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
        Task<QueryOrden> Checkout(string idCliente, IEnumerable<ProductoCarrito> productos);
        Task<ResponseCarrito> ProcesarPago(string idCliente, DatosPago datosPago);
        Orden ConsultarTotalOrden(int idOrden);
        IEnumerable<ItemProductoCarrito> DetalleOrden(int idOrden);
        QueryRankingClientes ConsultarRankingRangoFechas(DateTime fechaInicio, DateTime fechaFin);
        IEnumerable<Orden> ConsultarOrdenesFiltros(Parametros parametros);
        ResponseOrdenes ActualizarOrden(Orden orden);
        ResumenOrdenesMes OrdenesMes(int anio, int mes);
        IEnumerable<Orden> ConsultarOrdenesAbiertas();
        IEnumerable<Orden> ConsultarRankingFacturacionOrdenes(DateTime fechaInicio, DateTime fechaFin);
        Task<QueryOrden> Subtotal(string idCliente, IEnumerable<ProductoCarrito> productos);
    }
}
