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
    }
}
