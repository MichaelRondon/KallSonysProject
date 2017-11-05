using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Common.Util;
using Common.DTO;
using Common.Resources;
using OrdenesBC.Contratos;
using OrdenesEntities.Models;
using ClientesEntities.Models;
using ClientesEF;
using System.Net.Http;

namespace OrdenesBC.Implementaciones
{
    public class OrdenesBC : IOrdenesBC
    {
        private const string ESTADO_ORDEN_NUEVA = "NUEVA";
        private const string ESTADO_ORDEN_EN_PROCESO = "EN PROCESO";
        private const string ESTADO_ORDEN_ENVIADA = "ENVIADA";
        private const string ESTADO_ORDEN_RECHAZADA = "RECHAZADA";

        public async Task<QueryCampanias> BuscarCampanias(Parametros parametros)
        {
            string parameters = WebClientHelper.ParametrosSearch(parametros);
            string path = string.Format("{0}?{1}", StringResources.ServicioCampanias, parameters);

            QueryCampanias campanias = null;
            HttpResponseMessage response = await WebClientHelper.Client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                campanias = await response.Content.ReadAsAsync<QueryCampanias>();
            }
            
            return campanias;
        }

        public async Task<IEnumerable<Categoria>> BuscarCategorias(Parametros parametros)
        {
            string parameters = WebClientHelper.ParametrosSearch(parametros);
            string path = string.Format("{0}?{1}", StringResources.ServicioCategorias, parameters);

            List<Categoria> categorias = null;
            HttpResponseMessage response = await WebClientHelper.Client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                categorias = await response.Content.ReadAsAsync<List<Categoria>>();
            }

            return categorias;
        }

        public async Task<QueryProductos> BuscarProductos(Parametros parametros)
        {
            string parameters = WebClientHelper.ParametrosSearch(parametros);

            string path = string.Format("{0}?{1}", StringResources.ServicioBuscarProductos, parameters); // Path base de los productos

            QueryProductos productos = null;
            HttpResponseMessage response = await WebClientHelper.Client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                productos = await response.Content.ReadAsAsync<QueryProductos>();
            }

            return productos;

        }

        public async Task<Producto> ConsultarProducto(long id)
        {
            string path = string.Format("{0}/{1}", StringResources.ServicioProductos, id);

            Producto producto = null;
            HttpResponseMessage response = await WebClientHelper.Client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                producto = await response.Content.ReadAsAsync<Producto>();
            }

            return producto;
        }

        public async Task<QueryProductos> HistoricoVendidos(Parametros parametros)
        {
            string parameters = WebClientHelper.ParametrosSearch(parametros);
            string path = string.Format("{0}?{1}", StringResources.ServicioHistoricoProductos, parameters);

            QueryProductos productos = null;
            HttpResponseMessage response = await WebClientHelper.Client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                productos = await response.Content.ReadAsAsync<QueryProductos>();
            }

            return productos;
        }

        public ResponseCarrito AgregarProducto(string idCliente, ProductoCarrito producto)
        {
            ResponseCarrito response = new ResponseCarrito()
            {
                estatus = "OK",
                exitoso = true,
                mensaje = "Producto agregado al carrito exitosamente"
            };

            ClientesEF.ORDER ordenActual = null;

            // Tratar de extraer una orden actual en estado "NUEVA"
            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    ordenActual = context.ORDERS.SingleOrDefault(p => p.CUSTID.Equals(idCliente) && p.STATUS.Equals(ESTADO_ORDEN_NUEVA));

                    if (ordenActual == null)
                    {
                        ordenActual = context.ORDERS.Create();
                        ordenActual.CUSTID = idCliente;
                        ordenActual.STATUS = ESTADO_ORDEN_NUEVA;
                        context.ORDERS.Add(ordenActual);
                    }

                    // Verificar si la orden es nueva
                    if (context.Entry(ordenActual).State == System.Data.EntityState.Unchanged)
                    { // Orden existente
                        if (producto.cantidad == 0)
                        { // Eliminar
                            if (ordenActual.ITEMS.Any(p => p.PRODID.Equals(producto.idProducto)))
                            {
                                ITEM item = ordenActual.ITEMS.Single(p => p.PRODID.Equals(producto.idProducto));
                                context.ITEMS.Remove(item);
                                response.mensaje = "Item eliminado exitosamente";
                            }
                        } // Fin eliminar
                        else
                        { // Adicionar
                            if (ordenActual.ITEMS.Any(p => p.PRODID.Equals(producto.idProducto)))
                            {
                                ordenActual.ITEMS.Single(p => p.PRODID.Equals(producto.idProducto)).QUANTITY = producto.cantidad;
                            }
                            else
                            {
                                ordenActual.ITEMS.Add(new ITEM()
                                {
                                    PRODID = producto.idProducto,
                                    QUANTITY = producto.cantidad
                                });
                            }
                        } // Adicionar
                    } // Fin orden existente
                    else
                    { // Orden nueva
                        if (producto.cantidad > 0)
                        {
                            ordenActual.ITEMS.Add(new ITEM()
                            {
                                PRODID = producto.idProducto,
                                QUANTITY = producto.cantidad
                            });
                        }
                        else
                        {
                            response.mensaje = "Item eliminado exitosamente";
                        }
                    } // Fin orden nueva

                    // Enviar a la base de datos
                    context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                response.estatus = "Error";
                response.exitoso = false;
                response.mensaje = ex.Message;
            }

            return response;
        }

        public async Task<IEnumerable<ItemProductoCarrito>> ConsultarCarrito(string idCliente)
        {
            List<ItemProductoCarrito> carrito = new List<ItemProductoCarrito>();

            ClientesEF.ORDER ordenActual = null;

            // Tratar de extraer una orden actual en estado "NUEVA"
            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    ordenActual = context.ORDERS.SingleOrDefault(p => p.CUSTID.Equals(idCliente) && p.STATUS.Equals(ESTADO_ORDEN_NUEVA));

                    if (ordenActual == null)
                    {
                        return carrito;
                    }
                    else
                    {
                        carrito = new List<ItemProductoCarrito>();

                        if (ordenActual.ITEMS != null)
                        {
                            foreach (var item in ordenActual.ITEMS)
                            {
                                carrito.Add(new ItemProductoCarrito() {
                                    itemCarrito = new ProductoCarrito() {
                                        cantidad = item.QUANTITY.Value,
                                        idProducto = (long)item.PRODID
                                    }
                                });
                            }
                        }
                    }
                }
            }
            catch (Exception)
            {
                carrito = null;
            }

            // Si la consulta en base de datos fue exitosa, consultar API de productos
            try
            {
                if (carrito != null)
                {
                    foreach (var item in carrito)
                    {
                        item.producto = await ConsultarProducto(item.itemCarrito.idProducto);
                    }
                }
            }
            catch (Exception)
            {
                carrito = null;
            }

            return carrito;
        }

        public async Task<TotalOrden> Checkout(string idCliente, IEnumerable<ProductoCarrito> productos)
        {
            // Se combinan los datos actuales con los existentes
            ClientesEF.ORDER ordenActual = null;
            ClientesEF.ITEM itemActual = null;
            TotalOrden respuesta = null;
            bool carritoPersistido = false;

            // Tratar de extraer una orden actual en estado "NUEVA"
            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    ordenActual = context.ORDERS.SingleOrDefault(p => p.CUSTID.Equals(idCliente) && p.STATUS.Equals(ESTADO_ORDEN_NUEVA));

                    if (ordenActual == null)
                    {
                        ordenActual = context.ORDERS.Create();
                        ordenActual.CUSTID = idCliente;
                        ordenActual.STATUS = ESTADO_ORDEN_NUEVA;
                        context.ORDERS.Add(ordenActual);
                    }

                    // Verificar si la orden es nueva
                    if (context.Entry(ordenActual).State == System.Data.EntityState.Unchanged)
                    { // Orden existente
                        foreach (var productoCheckout in productos)
                        { // Sumar los productos enviados a los actuales
                            if (ordenActual.ITEMS.Any(p => p.PRODID.Equals(productoCheckout.idProducto)))
                            {
                                ITEM item = ordenActual.ITEMS.Single(p => p.PRODID.Equals(productoCheckout.idProducto));
                                item.QUANTITY += productoCheckout.cantidad;
                            }
                            else
                            {
                                ordenActual.ITEMS.Add(new ITEM() {
                                    PRODID = productoCheckout.idProducto,
                                    QUANTITY = productoCheckout.cantidad
                                });
                            }
                        } // Fin sumar los productos enviados a los actuales
                    } // Fin orden existente
                    else
                    { // Orden nueva
                        foreach (var productoCheckout in productos)
                        {
                            ordenActual.ITEMS.Add(new ITEM()
                            {
                                PRODID = productoCheckout.idProducto,
                                QUANTITY = productoCheckout.cantidad
                            }); 
                        }
                    } // Fin orden nueva

                    // Enviar a la base de datos
                    context.SaveChanges();
                    carritoPersistido = true;
                }
            }
            catch (Exception)
            {
                carritoPersistido = false;
            }

            // Obtener la consulta del carrito para retornar la información
            if (carritoPersistido == true)
            {
                // Consultar el carrito actual junto con sus detalles
                var consultaCarrito = await ConsultarCarrito(idCliente);

                try
                {
                    using (var context = new ClientesEF.Entities())
                    {
                        ordenActual = context.ORDERS.SingleOrDefault(p => p.CUSTID.Equals(idCliente) && p.STATUS.Equals(ESTADO_ORDEN_NUEVA));

                        foreach (var item in consultaCarrito)
                        {
                            if (respuesta == null)
                            {
                                respuesta = new TotalOrden()
                                {
                                    itemsTotal = 0,
                                    valorTotal = 0d
                                };
                            }

                            // Persistir la consulta en la base de datos
                            itemActual = ordenActual.ITEMS.Single(p => p.PRODID.Equals(item.itemCarrito.idProducto));
                            itemActual.CATEGORY = item.producto.descripcion;
                            itemActual.PRICE = Convert.ToDecimal(item.producto.precio.Value);
                            itemActual.PRODUCTNAME = item.producto.nombre;

                            respuesta.itemsTotal += itemActual.QUANTITY.Value;
                            respuesta.valorTotal += (item.producto.precio.Value * itemActual.QUANTITY.Value);
                        }

                        ordenActual.PRICE = Convert.ToDecimal(respuesta.valorTotal);

                        context.SaveChanges();
                    }
                }
                catch (Exception)
                {

                    throw;
                }

                respuesta.cliente = new ClientesBC.Implementaciones.ClientesBC().ConsultarCliente(idCliente); 
            }

            return respuesta;
        }

        public ResponseCarrito ProcesarPago(string idCliente, DatosPago datosPago)
        {
            ResponseCarrito respuesta = new ResponseCarrito()
            {
                estatus = "Ok",
                exitoso = true,
                mensaje = "Pago procesado exitosamente. ID orden: {0}"
            };

            ORDER ordenActual = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    ordenActual = context.ORDERS.SingleOrDefault(p => p.CUSTID.Equals(idCliente) && p.STATUS.Equals(ESTADO_ORDEN_NUEVA));

                    // Procesar pago de tarjeta de crédito
                    bool resultadoPago = true; // Procesar pago de la orden

                    // Actualizar el estado de la orden
                    if (resultadoPago == true)
                    {
                        ordenActual.STATUS = ESTADO_ORDEN_EN_PROCESO;
                        ordenActual.ORDERDATE = DateTime.Now;
                        ADDRESS address = ordenActual.CUSTOMER.ADDRESSes.FirstOrDefault();

                        if (address == null)
                        {
                            address = context.ADDRESSes.Create();
                            ordenActual.CUSTOMER.ADDRESSes.Add(address);
                        }

                        address.ADDRID = String.Format("DIRECCION {0}", ordenActual.CUSTOMER.CUSTID);
                        address.STREET = datosPago.direccionEntrega;
                        address.CITY = datosPago.ciudadEntrega;
                        address.COUNTRY = datosPago.paisEntrega;
                    }
                    else
                    {
                        respuesta.mensaje = "Error al procesar pago con tarjeta de crédito.";
                        respuesta.exitoso = false;
                        respuesta.estatus = "Error";
                    }

                    respuesta.mensaje = string.Format(respuesta.mensaje, ordenActual.ORDID);

                    context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                respuesta.mensaje = ex.Message;
                respuesta.exitoso = false;
                respuesta.estatus = "Error";
            }

            // Llamar servicio backend de órdenes
            if (respuesta.exitoso == true)
            {
                // TODO: LLAMAR EL SERVICIO
            }
            //else
            //{
            //    respuesta.mensaje = "Error al iniciar proceso de orden.";
            //    respuesta.exitoso = false;
            //    respuesta.estatus = "Error";
            //}

            return respuesta;
        }

        public TotalOrden ConsultarTotalOrden(int idOrden)
        {
            TotalOrden total = null;
            ORDER ordenActual = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    ordenActual = context.ORDERS.Single(p => p.ORDID.Equals(idOrden));

                    total = new TotalOrden()
                    {
                        cliente = new ClientesEntities.Models.Cliente()
                        {
                            apellidos = ordenActual.CUSTOMER.LNAME,
                            correo_e = ordenActual.CUSTOMER.EMAIL,
                            documento = ordenActual.CUSTOMER.CUSTID,
                            estatus = ordenActual.CUSTOMER.STATUS,
                            nombres = ordenActual.CUSTOMER.FNAME,
                            numero = ordenActual.CUSTOMER.CREDITCARDNUMBER,
                            telefono = ordenActual.CUSTOMER.PHONENUMBER,
                            tipo = ordenActual.CUSTOMER.CREDITCARDTYPE
                        },
                        valorTotal = Convert.ToDouble(ordenActual.PRICE)
                    };

                    ADDRESS address = ordenActual.CUSTOMER.ADDRESSes.FirstOrDefault();

                    if (address != null)
                    {
                        Direccion direccion = new Direccion()
                        {
                            calle = address.STREET,
                            ciudad = address.CITY,
                            direccion = address.ADDRID,
                            estado = address.STATE,
                            pais = address.COUNTRY,
                            tipo = address.ADDRESSTYPE,
                            zipcode = address.ZIP
                        };

                        total.cliente.direcciones = new List<Direccion>() { direccion };
                    }
                }
            }
            catch (Exception)
            {
                total = null;
            }

            return total;
        }

        public IEnumerable<ItemProductoCarrito> DetalleOrden(int idOrden)
        {
            List<ItemProductoCarrito> resultado = null;
            ORDER ordenActual = null;
            ItemProductoCarrito itemProductoCarrito = null;
            Producto productoOrden = null;
            ProductoCarrito itemProductoOrden = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    ordenActual = context.ORDERS.Single(p => p.ORDID.Equals(idOrden));

                    foreach (var item in ordenActual.ITEMS)
                    {
                        if (resultado == null)
                        {
                            resultado = new List<ItemProductoCarrito>();
                        }

                        productoOrden = new Producto() {
                            id = Convert.ToInt64(item.PRODID),
                            nombre = item.PRODUCTNAME,
                            precio = Convert.ToDouble(item.PRICE),
                            descripcion = item.CATEGORY
                        };

                        itemProductoOrden = new ProductoCarrito() {
                            cantidad = item.QUANTITY.Value,
                            idProducto = Convert.ToInt64(item.PRODID)
                        };

                        itemProductoCarrito = new ItemProductoCarrito() {
                            itemCarrito = itemProductoOrden,
                            producto = productoOrden
                        };

                        resultado.Add(itemProductoCarrito);
                    }
                }
            }
            catch (Exception)
            {
                resultado = null;
            }

            return resultado;
        }
    }
}
