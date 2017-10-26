#region Directivas using
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ShoppingCartDAC.Contratos;
using ShoppingCartEntities.Models;
using ClientesEF;
using Common;
using Common.Enums;
#endregion

namespace ShoppingCartDAC.Implementaciones
{
    public class ShoppingCartDAC : IShoppingCartDAC
    {
        private const string ESTADO_ORDEN_NUEVA = "NUEVA";

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

        public IEnumerable<ProductoCarrito> ConsultarCarrito(string idCliente)
        {
            List<ProductoCarrito> carrito = new List<ProductoCarrito>();

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
                        carrito = new List<ProductoCarrito>();

                        if (ordenActual.ITEMS != null)
                        {
                            foreach (var item in ordenActual.ITEMS)
                            {
                                carrito.Add(new ProductoCarrito() {
                                    cantidad = item.QUANTITY.Value,
                                    idProducto = (long)item.PRODID
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

            return carrito;
        }
    }
}
