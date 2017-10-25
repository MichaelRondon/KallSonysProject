using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Common.Util;
using Common.DTO;
using Common.Resources;
using OrdenesDAC.Contratos;
using OrdenesEntities.Models;
using System.Net.Http;

namespace OrdenesDAC.Implementaciones
{
    public class OrdenesDAC : IOrdenesDAC
    {
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
    }
}
