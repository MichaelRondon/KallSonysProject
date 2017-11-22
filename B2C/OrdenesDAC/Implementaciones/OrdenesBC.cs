#region Directivas using
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Common.Util;
using Common.DTO;
using Common.Resources;
using OrdenesBC.Contratos;
using OrdenesEntities.Models;
using ClientesEntities.Models;
using ClientesEF;
using System.Net.Http;
using System.Data;
using Oracle.DataAccess.Client;
using System.Configuration;
using OrdenesBC.ValidateCreditCardSVC;
#endregion

namespace OrdenesBC.Implementaciones
{
    public class OrdenesBC : IOrdenesBC
    {
        #region Constantes privadas

        private const string ESTADO_ORDEN_NUEVA = "NUEVA";
        private const string ESTADO_ORDEN_EN_PROCESO = "EN PROCESO";
        private const string ESTADO_ORDEN_ENVIADA = "ENVIADA";
        private const string ESTADO_ORDEN_RECHAZADA = "RECHAZADA";
        private const string ESTADO_ORDEN_EN_TRANSITO = "TRANSITO";
        private const string ESTADO_ORDEN_CANCELADA = "CANCELADA";
        private const string ESTADO_ORDEN_ENTREGADA = "ENTREGADA"; 

        #endregion

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
                producto.urlImage = string.Format("api/ImageThumb/{0}", id);
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

        public async Task<QueryOrden> Checkout(string idCliente, IEnumerable<ProductoCarrito> productos)
        {
            // Se combinan los datos actuales con los existentes
            ClientesEF.ORDER ordenActual = null;
            ClientesEF.ITEM itemActual = null;
            QueryOrden queryRespuesta = null;
            Orden ordenRespuesta = null;
            bool carritoPersistido = false;

            // Tratar de eliminar los items actuales
            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    ordenActual = context.ORDERS.SingleOrDefault(p => p.CUSTID.Equals(idCliente) && p.STATUS.Equals(ESTADO_ORDEN_NUEVA));

                    if (ordenActual != null)
                    {
                        foreach (var item in ordenActual.ITEMS)
                        {
                            context.Database.ExecuteSqlCommand(string.Format("DELETE FROM B2C.ITEMS WHERE ORDID = {0} AND PRODID = {1}", ordenActual.ORDID, item.PRODID));
                        }
                    }

                    context.SaveChanges();
                }
            }
            catch (Exception)
            {

                throw;
            }

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
                        // Se adicionan los productos que se recibieron desde el cliente
                        foreach (var productoCheckout in productos)
                        { // Actualizar los productos actuales con los enviados
                            if (ordenActual.ITEMS.Any(p => p.PRODID.Equals(productoCheckout.idProducto)))
                            {
                                ITEM item = ordenActual.ITEMS.Single(p => p.PRODID.Equals(productoCheckout.idProducto));
                                item.QUANTITY = productoCheckout.cantidad;
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
            catch (Exception ex)
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
                            if (ordenRespuesta == null)
                            {
                                ordenRespuesta = new Orden()
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

                            ordenRespuesta.itemsTotal += itemActual.QUANTITY.Value;
                            ordenRespuesta.valorTotal += (item.producto.precio.Value * itemActual.QUANTITY.Value);
                        }

                        ordenActual.PRICE = Convert.ToDecimal(ordenRespuesta.valorTotal);

                        context.SaveChanges();
                    }
                }
                catch (Exception)
                {

                    throw;
                }

                ordenRespuesta.cliente = new ClientesBC.Implementaciones.ClientesBC().ConsultarCliente(idCliente);

                queryRespuesta = new QueryOrden()
                {
                    orden = ordenRespuesta,
                    items = consultaCarrito
                };
            }

            return queryRespuesta;
        }

        public async Task<ResponseCarrito> ProcesarPago(string idCliente, DatosPago datosPago)
        {
            ResponseCarrito respuesta = new ResponseCarrito()
            {
                estatus = "Ok",
                exitoso = true,
                mensaje = "Pago procesado exitosamente. ID orden: {0}"
            };

            ORDER ordenActual = null;
            CUSTOMER clienteActual = null;
            string numTarjeta = null, tipoTarjeta = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    ordenActual = context.ORDERS.SingleOrDefault(p => p.CUSTID.Equals(idCliente) && p.STATUS.Equals(ESTADO_ORDEN_NUEVA));
                    clienteActual = ordenActual.CUSTOMER;

                    if (string.IsNullOrEmpty(clienteActual.CREDITCARDNUMBER))
                    {
                        numTarjeta = datosPago.numeroTarjeta;
                        tipoTarjeta = datosPago.tipoTarjeta;
                    }
                    else
                    {
                        numTarjeta = ClientesBC.Util.StringHelper.Decrypt(clienteActual.CREDITCARDNUMBER, clienteActual.SALT, true);
                        tipoTarjeta = clienteActual.CREDITCARDTYPE;
                    }

                    // Procesar pago de tarjeta de crédito
                    bool resultadoPago = ValidarTarjetaCredito(numTarjeta, tipoTarjeta, Convert.ToDouble(ordenActual.PRICE));

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
                ResponseBPM responseBPM = await InvocarProcesoBPM(ordenActual);
                if (responseBPM != null)
                {
                    try
                    {
                        using (var context = new ClientesEF.Entities())
                        {
                            ordenActual = context.ORDERS.SingleOrDefault(p => p.ORDID.Equals(ordenActual.ORDID));

                            ordenActual.COMMENTS = Newtonsoft.Json.JsonConvert.SerializeObject(responseBPM);
                         
                            context.SaveChanges();
                        }
                    }
                    catch (Exception ex)
                    {
                        respuesta.mensaje = ex.Message;
                        respuesta.exitoso = false;
                        respuesta.estatus = "Error";
                    }
                }
                else
                {
                    respuesta.mensaje = "Error al iniciar proceso de orden.";
                    respuesta.exitoso = false;
                    respuesta.estatus = "Error";
                }
            }

            if (respuesta.exitoso == true && ordenActual != null)
            {
                await GenerarMailNotificacion(ordenActual, clienteActual);
            }

            return respuesta;
        }

        public Orden ConsultarTotalOrden(int idOrden)
        {
            Orden total = null;
            ORDER ordenActual = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    ordenActual = context.ORDERS.Single(p => p.ORDID.Equals(idOrden));

                    total = new Orden()
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
                        valorTotal = Convert.ToDouble(ordenActual.PRICE),
                        idOrden = idOrden,
                        fecha_orden = ordenActual.ORDERDATE.HasValue ? ordenActual.ORDERDATE.Value.ToString("yyyy-MM-dd") : null,
                        estado = ordenActual.STATUS,
                        carrier = ordenActual.CARRIER,
                        fabricante = ordenActual.VENDOR
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
                            descripcion = item.CATEGORY,
                            urlImage = string.Format("api/ImageThumb/{0}", item.PRODID)
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

        public QueryRankingClientes ConsultarRankingRangoFechas(DateTime fechaInicio, DateTime fechaFin)
        {
            QueryRankingClientes ranking = null;

            OracleConnection conex = null;
            OracleDataReader reader = null;

            conex = new OracleConnection(ConfigurationManager.ConnectionStrings["B2CConnection"].ConnectionString);

            OracleCommand cmd = new OracleCommand() {
                CommandText = "B2C.PKG_ORDENES.ranking_fact_clientes",
                CommandType = CommandType.StoredProcedure,
                Connection = conex
            };

            OracleParameter paramFechaIni = new OracleParameter() {
                DbType = DbType.Date,
                Direction = ParameterDirection.Input,
                ParameterName = "p_fecha_ini",
                Value = fechaInicio
            };

            OracleParameter paramFechaFin = new OracleParameter()
            {
                DbType = DbType.Date,
                Direction = ParameterDirection.Input,
                ParameterName = "p_fecha_fin",
                Value = fechaFin
            };

            OracleParameter paramOutputCursor = new OracleParameter("IO_CURSOR", OracleDbType.RefCursor);
            paramOutputCursor.Direction = ParameterDirection.Output;

            cmd.Parameters.Add(paramFechaIni);
            cmd.Parameters.Add(paramFechaFin);
            cmd.Parameters.Add(paramOutputCursor);

            try
            {
                conex.Open();
                reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    if (ranking == null)
                    {
                        ranking = new QueryRankingClientes();
                        ranking.ranking = new List<ClienteRanking>();
                    }

                    ((List<ClienteRanking>)ranking.ranking).Add(new ClienteRanking() {
                        documento = reader.GetString(0),
                        facturado = Convert.ToDecimal(reader.GetValue(1))
                    });
                }
            }
            catch (Exception)
            {
                ranking = null;
            }
            finally
            {
                if (reader != null)
                {
                    reader.Close();
                }
                if (conex != null)
                {
                    conex.Close();
                }
                reader = null;
                conex = null;
            }

            return ranking;
        }

        public IEnumerable<Orden> ConsultarOrdenesFiltros(Parametros parametros)
        {
            List<Orden> ordenes = null;

            OracleConnection conex = null;
            OracleDataReader reader = null;

            conex = new OracleConnection(ConfigurationManager.ConnectionStrings["B2CConnection"].ConnectionString);

            OracleCommand cmd = new OracleCommand()
            {
                CommandText = "B2C.PKG_ORDENES.ordenes_x_producto",
                CommandType = CommandType.StoredProcedure,
                Connection = conex
            };

            OracleParameter paramIdProducto = new OracleParameter()
            {
                DbType = DbType.Int32,
                Direction = ParameterDirection.Input,
                ParameterName = "p_id_producto",
                Value = parametros.idProducto
            };

            OracleParameter paramOutputCursor = new OracleParameter("IO_CURSOR", OracleDbType.RefCursor);
            paramOutputCursor.Direction = ParameterDirection.Output;

            cmd.Parameters.Add(paramIdProducto);
            cmd.Parameters.Add(paramOutputCursor);

            try
            {
                conex.Open();
                reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    if (ordenes == null)
                    {
                        ordenes = new List<Orden>();
                    }

                    ordenes.Add(new Orden() {
                        idOrden = Convert.ToInt32(reader.GetValue(1)),
                        cliente = new Cliente() { documento = reader.GetValue(0).ToString() },
                        valorTotal = Convert.ToDouble(reader.GetValue(2)),
                        fecha_orden = reader.GetDateTime(3).ToString("yyyy-MM-dd"),
                        estado = reader.GetValue(4).ToString(),
                        carrier = reader.GetValue(5).ToString(),
                        fabricante = reader.GetValue(6).ToString(),
                    });
                }
            }
            catch (Exception)
            {
                ordenes = null;
            }
            finally
            {
                if (reader != null)
                {
                    reader.Close();
                }
                if (conex != null)
                {
                    conex.Close();
                }
                reader = null;
                conex = null;
            }

            return ordenes;
        }

        private bool ValidarTarjetaCredito(string numero, string tipo, double monto)
        {
            bool resultado = true;
            ValidateCreditCardClient client = null;

            try
            {
                client = new ValidateCreditCardClient();

                resultado = client.VerifyCC(new CreditCard() { ccNum = numero, ccType = tipo });

                client.Close();
            }
            catch (Exception)
            {
                resultado = false;
            }

            return resultado;
        }

        private async Task<ResponseBPM> InvocarProcesoBPM(ORDER orden)
        {
            string path = StringResources.ServicioBonitaBPM;
            OrdenBPM ordenBPM = new OrdenBPM() { fechaOrden = orden.ORDERDATE.Value.ToString("yyyy-MM-dd"), orderId = orden.ORDID };
            ResponseBPM responseBPM = null;

            HttpResponseMessage response = await WebClientHelper.ClientBPM.PostAsJsonAsync(path, ordenBPM);
            if (response.IsSuccessStatusCode)
            {
                responseBPM = await response.Content.ReadAsAsync<ResponseBPM>();
            }

            return responseBPM;
        }

        public async Task<ResponseOrdenes> ActualizarOrden(Orden orden)
        {
            ResponseOrdenes response = new ResponseOrdenes() {
                estatus = "Ok",
                exitoso = true,
                mensaje = "Orden actualizada exitosamente"
            };

            ORDER ordenActual = null;
            CUSTOMER clienteActual = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    ordenActual = context.ORDERS.Single(p => p.ORDID.Equals(orden.idOrden));
                    clienteActual = ordenActual.CUSTOMER;

                    // Se actualiza el estado de la orden
                    if (!string.IsNullOrEmpty(orden.estado))
                    {
                        ordenActual.STATUS = orden.estado; 
                    }

                    if (!string.IsNullOrEmpty(orden.carrier))
                    {
                        ordenActual.CARRIER = orden.carrier; 
                    }

                    if (!string.IsNullOrEmpty(orden.fabricante))
                    {
                        ordenActual.VENDOR = orden.fabricante; 
                    }

                    ordenActual.LASTTRACKINGDATE = DateTime.Now;

                    if (ordenActual.STATUS.Equals(ESTADO_ORDEN_ENTREGADA))
                    {
                        var resultadoCola = await EnviarDatosCola(ordenActual);

                        if (resultadoCola != null)
                        {
                            string res = Newtonsoft.Json.JsonConvert.SerializeObject(resultadoCola);
                            ordenActual.COMMENTS = string.IsNullOrEmpty(ordenActual.COMMENTS) ? res : string.Concat(ordenActual.COMMENTS, "\r\n", res);
                        }
                    }

                    context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                response.estatus = "Error";
                response.exitoso = false;
                response.mensaje = string.Format("Error al actualizar orden: {0}", ex.Message);
            }

            if (ordenActual != null)
            {
                await GenerarMailNotificacion(ordenActual, clienteActual);
            }

            return response;
        }

        public ResumenOrdenesMes OrdenesMes(int anio, int mes)
        {
            ResumenOrdenesMes ordenesMes = null;

            OracleConnection conex = null;
            OracleDataReader reader = null;

            conex = new OracleConnection(ConfigurationManager.ConnectionStrings["B2CConnection"].ConnectionString);

            OracleCommand cmd = new OracleCommand()
            {
                CommandText = "B2C.PKG_ORDENES.resumen_ordenes_mes",
                CommandType = CommandType.StoredProcedure,
                Connection = conex
            };

            OracleParameter paramAnio = new OracleParameter()
            {
                DbType = DbType.Int32,
                Direction = ParameterDirection.Input,
                ParameterName = "p_anio",
                Value = anio
            };

            OracleParameter paramMes = new OracleParameter()
            {
                DbType = DbType.Int32,
                Direction = ParameterDirection.Input,
                ParameterName = "p_mes",
                Value = mes
            };

            OracleParameter paramOutputCursor = new OracleParameter("IO_CURSOR", OracleDbType.RefCursor);
            paramOutputCursor.Direction = ParameterDirection.Output;

            cmd.Parameters.Add(paramAnio);
            cmd.Parameters.Add(paramMes);
            cmd.Parameters.Add(paramOutputCursor);

            try
            {
                conex.Open();
                reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    if (ordenesMes == null)
                    {
                        ordenesMes = new ResumenOrdenesMes();
                    }

                    ordenesMes.numeroOrdenes = Convert.ToInt32(reader.GetValue(1));
                    ordenesMes.totalFacturado = Convert.ToInt32(reader.GetValue(0));
                }
            }
            catch (Exception)
            {
                ordenesMes = null;
            }
            finally
            {
                if (reader != null)
                {
                    reader.Close();
                }
                if (conex != null)
                {
                    conex.Close();
                }
                reader = null;
                conex = null;
            }

            return ordenesMes;
        }

        public IEnumerable<Orden> ConsultarOrdenesAbiertas()
        {
            List<Orden> ordenes = null;
            Orden ordenActual = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    var ordenesConsulta = context.ORDERS.Where(p => p.STATUS.Equals(ESTADO_ORDEN_EN_PROCESO) || p.STATUS.Equals(ESTADO_ORDEN_EN_TRANSITO)).OrderBy(q => q.ORDERDATE);

                    foreach (var item in ordenesConsulta)
                    {
                        if (ordenes == null)
                        {
                            ordenes = new List<Orden>();
                        }

                        ordenActual = CreateOrden(item);

                        ordenes.Add(ordenActual);
                    }
                }
            }
            catch (Exception ex)
            {
                ordenes = null;
            }

            return ordenes;
        }

        public IEnumerable<Orden> ConsultarRankingFacturacionOrdenes(DateTime fechaInicio, DateTime fechaFin)
        {
            List<Orden> ordenes = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    var ordenesRanking = context.ORDERS.Where(p => p.STATUS.Equals(ESTADO_ORDEN_ENTREGADA) && p.ORDERDATE >= fechaInicio && p.ORDERDATE <= fechaFin).OrderByDescending(q => q.PRICE);

                    foreach (var item in ordenesRanking)
                    {
                        if (ordenes == null)
                        {
                            ordenes = new List<Orden>();
                        }

                        ordenes.Add(CreateOrden(item));
                    }
                }
            }
            catch (Exception)
            {
                ordenes = null;
            }

            return ordenes;
        }

        private Orden CreateOrden(ORDER ordendb)
        {
            Orden ordenActual = new Orden()
            {
                estado = ordendb.STATUS,
                fecha_orden = ordendb.ORDERDATE.HasValue ? ordendb.ORDERDATE.Value.ToString("yyyy-MM-dd") : null,
                idOrden = ordendb.ORDID,
                valorTotal = Convert.ToDouble(ordendb.PRICE.Value),
                fabricante = ordendb.VENDOR,
                carrier = ordendb.CARRIER,
                cliente = new Cliente()
                {
                    apellidos = ordendb.CUSTOMER.LNAME,
                    correo_e = ordendb.CUSTOMER.EMAIL,
                    documento = ordendb.CUSTID,
                    estatus = ordendb.CUSTOMER.STATUS,
                    nombres = ordendb.CUSTOMER.FNAME,
                    numero = string.IsNullOrEmpty(ordendb.CUSTOMER.CREDITCARDNUMBER) ? null : ClientesBC.Util.StringHelper.MaskText(ClientesBC.Util.StringHelper.Decrypt(ordendb.CUSTOMER.CREDITCARDNUMBER, ordendb.CUSTOMER.SALT, true), 4),
                    telefono = ordendb.CUSTOMER.PHONENUMBER,
                    tipo = ordendb.CUSTOMER.CREDITCARDTYPE,
                }
            };

            if (ordendb.CUSTOMER.ADDRESSes.Any())
            {
                ADDRESS direccionActual = ordendb.CUSTOMER.ADDRESSes.First();
                ordenActual.cliente.direcciones = new List<Direccion>() {
                                new Direccion()
                                {
                                    calle = direccionActual.STREET,
                                    ciudad = direccionActual.CITY,
                                    direccion = direccionActual.ADDRID,
                                    estado = direccionActual.STATE,
                                    pais = direccionActual.COUNTRY,
                                    tipo = direccionActual.ADDRESSTYPE,
                                    zipcode = direccionActual.ZIP
                                }
                            };
            }

            return ordenActual;
        }

        public async Task<QueryOrden> Subtotal(string idCliente, IEnumerable<ProductoCarrito> productos)
        {
            // Se combinan los datos actuales con los existentes
            QueryOrden queryRespuesta = null;
            Orden ordenRespuesta = null;
            List<ItemProductoCarrito> consultaCarrito = null;
            Producto producto = null;
            
            try
            {
                foreach (var productoSubtotal in productos)
                {
                    if (ordenRespuesta == null)
                    {
                        ordenRespuesta = new Orden()
                        {
                            itemsTotal = 0,
                            valorTotal = 0d
                        };

                        consultaCarrito = new List<ItemProductoCarrito>();
                    }

                    producto = await ConsultarProducto(productoSubtotal.idProducto);

                    consultaCarrito.Add(new ItemProductoCarrito() {
                        itemCarrito = new ProductoCarrito() { cantidad = productoSubtotal.cantidad, idProducto = productoSubtotal.idProducto},
                        producto = producto
                    });

                    ordenRespuesta.itemsTotal += productoSubtotal.cantidad;
                    ordenRespuesta.valorTotal += (producto.precio.Value * productoSubtotal.cantidad);
                }
            }
            catch (Exception)
            {

                throw;
            }

            ordenRespuesta.cliente = new ClientesBC.Implementaciones.ClientesBC().ConsultarCliente(idCliente);

            queryRespuesta = new QueryOrden()
            {
                orden = ordenRespuesta,
                items = consultaCarrito
            };
            //} // fin if

            return queryRespuesta;
        }

        public QueryRankingClientes ConsultaRankingClientesProductos(Parametros parametros)
        {
            List<ClienteRanking> rankingConsulta = null;
            QueryRankingClientes respuesta = null;

            OracleConnection conex = null;
            OracleDataReader reader = null;

            conex = new OracleConnection(ConfigurationManager.ConnectionStrings["B2CConnection"].ConnectionString);

            OracleCommand cmd = new OracleCommand()
            {
                CommandText = "B2C.PKG_ORDENES.ranking_x_producto",
                CommandType = CommandType.StoredProcedure,
                Connection = conex
            };

            OracleParameter paramIdProducto = new OracleParameter()
            {
                DbType = DbType.Int32,
                Direction = ParameterDirection.Input,
                ParameterName = "p_id_producto",
                Value = parametros.idProducto
            };

            OracleParameter paramOutputCursor = new OracleParameter("IO_CURSOR", OracleDbType.RefCursor);
            paramOutputCursor.Direction = ParameterDirection.Output;

            cmd.Parameters.Add(paramIdProducto);
            cmd.Parameters.Add(paramOutputCursor);

            try
            {
                conex.Open();
                reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    if (rankingConsulta == null)
                    {
                        rankingConsulta = new List<ClienteRanking>();
                    }

                    rankingConsulta.Add(new ClienteRanking() {
                        documento = reader.GetString(0),
                        facturado = Convert.ToDecimal(reader.GetValue(1))
                    });
                }
                respuesta = new QueryRankingClientes() { ranking = rankingConsulta };
            }
            catch (Exception)
            {
                respuesta = null;
            }
            finally
            {
                if (reader != null)
                {
                    reader.Close();
                }
                if (conex != null)
                {
                    conex.Close();
                }
                reader = null;
                conex = null;
            }

            return respuesta;
        }

        private async Task<int> GenerarMailNotificacion(ORDER ordenActual, CUSTOMER clienteActual)
        {
            string subject = StringResources.TemplateSubjectCambioEstado;
            string body = StringResources.TemplateMailCambioEstado;
            string carrier = StringResources.TemplateProveedorMensajeria;
            int resultado = 0;


            subject = string.Format(subject, ordenActual.ORDID);

            if (!string.IsNullOrEmpty(ordenActual.CARRIER) 
                && !(ordenActual.STATUS.Equals(ESTADO_ORDEN_CANCELADA) 
                || ordenActual.STATUS.Equals(ESTADO_ORDEN_ENTREGADA)
                || ordenActual.STATUS.Equals(ESTADO_ORDEN_RECHAZADA)))
            {
                carrier = string.Format(carrier, ordenActual.CARRIER);
            }
            else
            {
                carrier = string.Empty;
            }

            body = string.Format(body, string.Format("{0} {1}", clienteActual.FNAME, clienteActual.LNAME), ordenActual.ORDID, ordenActual.STATUS, carrier);

            try
            {
                await MailHelper.SendEmail(clienteActual.EMAIL, subject, body);
            }
            catch (Exception ex)
            {
                resultado = -1;
                // throw;
            }

            return resultado;
        }

        public IEnumerable<OrdenTransito> ConsultarOrdenesEnTransito()
        {
            List<OrdenTransito> ordenes = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    var abiertas = context.ORDERS.Where(p => p.STATUS.Equals(ESTADO_ORDEN_EN_TRANSITO));

                    if (abiertas.Any())
                    {
                        ordenes = new List<OrdenTransito>();
                    }

                    foreach (var item in abiertas)
                    {
                        ordenes.Add(new OrdenTransito() { carrier = item.CARRIER, idOrden = item.ORDID });
                    }
                }
            }
            catch (Exception)
            {
                ordenes = null;
            }

            return ordenes; 
        }

        public IEnumerable<Orden> ConsultarOrdenesCliente(string idCliente)
        {
            List<Orden> ordenes = null;
            Orden ordenActual = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    var ordenesConsulta = context.ORDERS.Where(p => p.CUSTID.Equals(idCliente) && !p.STATUS.Equals(ESTADO_ORDEN_NUEVA)).OrderByDescending(q => q.ORDERDATE);

                    foreach (var item in ordenesConsulta)
                    {
                        if (ordenes == null)
                        {
                            ordenes = new List<Orden>();
                        }

                        ordenActual = CreateOrden(item);

                        ordenes.Add(ordenActual);
                    }
                }
            }
            catch (Exception)
            {
                ordenes = null;
            }

            return ordenes;
        }

        public IEnumerable<ProductoRanking> ConsultarRankingFacturacionProductos(DateTime fechaInicio, DateTime fechaFin)
        {
            List<ProductoRanking> productos = null;

            OracleConnection conex = null;
            OracleDataReader reader = null;

            conex = new OracleConnection(ConfigurationManager.ConnectionStrings["B2CConnection"].ConnectionString);

            OracleCommand cmd = new OracleCommand()
            {
                CommandText = "B2C.PKG_ORDENES.ranking_fact_productos",
                CommandType = CommandType.StoredProcedure,
                Connection = conex
            };

            OracleParameter paramFechaIni = new OracleParameter()
            {
                DbType = DbType.Date,
                Direction = ParameterDirection.Input,
                ParameterName = "p_fecha_ini",
                Value = fechaInicio
            };

            OracleParameter paramFechaFin = new OracleParameter()
            {
                DbType = DbType.Date,
                Direction = ParameterDirection.Input,
                ParameterName = "p_fecha_fin",
                Value = fechaFin
            };

            OracleParameter paramOutputCursor = new OracleParameter("IO_CURSOR", OracleDbType.RefCursor);
            paramOutputCursor.Direction = ParameterDirection.Output;

            cmd.Parameters.Add(paramFechaIni);
            cmd.Parameters.Add(paramFechaFin);
            cmd.Parameters.Add(paramOutputCursor);

            try
            {
                conex.Open();
                reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    if (productos == null)
                    {
                        productos = new List<ProductoRanking>();
                    }

                    productos.Add(new ProductoRanking()
                    {
                        idProducto = Convert.ToInt32(reader.GetValue(0)),
                        facturado = Convert.ToDecimal(reader.GetValue(1)),
                    });
                }
            }
            catch (Exception)
            {
                productos = null;
            }
            finally
            {
                if (reader != null)
                {
                    reader.Close();
                }
                if (conex != null)
                {
                    conex.Close();
                }
                reader = null;
                conex = null;
            }

            return productos;
        }

        private async Task<RespuestaColaFacturacion> EnviarDatosCola(ORDER ordenActual)
        {
            string path = StringResources.ServicioColaFacturacion;
            DatosColaFacturacion dataColaFacturacion = new DatosColaFacturacion()
            {
                orderFinishedDay = ordenActual.LASTTRACKINGDATE.Value.Day,
                orderFinishedMonth = ordenActual.LASTTRACKINGDATE.Value.Month,
                orderFinishedYear = ordenActual.LASTTRACKINGDATE.Value.Year,
                orderManufacter = ordenActual.VENDOR,
                orderNumber = ordenActual.ORDID,
                orderTotalValue = Convert.ToDouble(ordenActual.PRICE.Value)
            };
            RespuestaColaFacturacion responseColaFacturacion = null;

            try
            {
                HttpResponseMessage response = await WebClientHelper.ClientColaFactur.PostAsJsonAsync(path, dataColaFacturacion);
                if (response.IsSuccessStatusCode)
                {
                    responseColaFacturacion = await response.Content.ReadAsAsync<RespuestaColaFacturacion>();
                }
            }
            catch (Exception)
            {
                responseColaFacturacion = null;
            }

            return responseColaFacturacion;
        }

        public async Task<QueryProductos> BuscarProductosScroll(Parametros parametros)
        {
            string parameters = WebClientHelper.ParametrosSearch(parametros);

            string path = string.Format("{0}?{1}", StringResources.ServicioBuscarProductosScroll, parameters); // Path base de los productos

            QueryProductos productos = null;
            HttpResponseMessage response = await WebClientHelper.Client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                productos = await response.Content.ReadAsAsync<QueryProductos>();
            }

            return productos;
        }
    }
}
