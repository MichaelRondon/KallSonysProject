using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Common.DTO;
using OrdenesEntities.Models;

namespace OrdenesBC.Contratos
{
    public interface IOrdenesBC
    {
        Task<QueryProductos>  HistoricoVendidos(Parametros parametros);
        Task<QueryProductos> BuscarProductos(Parametros parametros);
        Task<QueryCampanias> BuscarCampanias(Parametros parametros);
        Task<Producto> ConsultarProducto(long id);
        Task<IEnumerable<Categoria>> BuscarCategorias(Parametros parametros);
        ResponseCarrito AgregarProducto(string idCliente, ProductoCarrito producto);
        Task<IEnumerable<ItemProductoCarrito>> ConsultarCarrito(string idCliente);
        Task<TotalOrden> Checkout(string idCliente, IEnumerable<ProductoCarrito> productos);
        ResponseCarrito ProcesarPago(string idCliente, DatosPago datosPago);
        TotalOrden ConsultarTotalOrden(int idOrden);
        IEnumerable<ItemProductoCarrito> DetalleOrden(int idOrden);
    }
}
