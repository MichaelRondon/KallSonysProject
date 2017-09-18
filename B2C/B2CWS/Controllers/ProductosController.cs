using System;
using System.Collections.Generic;
using System.Data;
//using System.Data.Entity;
//using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using B2CWS.Models;
using System.Net.Http.Headers;
using System.Text;
using B2CWS.Util;

namespace B2CWS.Controllers
{
    public class ProductosController : ApiController
    {
        //protected override void Dispose(bool disposing)
        //{
        //    if (disposing)
        //    {
        //        //db.Dispose();
        //    }
        //    base.Dispose(disposing);
        //}

        // Get
        [Route("api/producto/historico/vendidos")]
        [ResponseType(typeof(QueryProductos))]
        public async Task<IHttpActionResult> GetHistoricoVendidos(string fecha_min, string fecha_max, string categoria = "", int tamanio = 0, int page = 0, int items_per_page = 0, string sort = "", string sort_type = "", string custom = "")
        {
            Parametros _params = new Parametros() {
                fecha_min = fecha_min,
                fecha_max = fecha_max,
                categoria = categoria,
                tamanio = tamanio,
                page = page,
                items_per_page = items_per_page,
                sort = sort,
                sort_type = sort_type,
                custom = custom
            };
            string parameters = WebClientHelper.ParametrosSearch(_params);
            string path = string.Format("{0}?{1}", StringResources.ServicioHistoricoProductos, parameters);

            QueryProductos productos = null;
            HttpResponseMessage response = await WebClientHelper.Client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                productos = await response.Content.ReadAsAsync<QueryProductos>();
            }

            if (productos == null)
            {
                return NotFound();
            }

            return Ok(productos);
        }

        // Get
        [Route("api/producto/buscar")]
        [ResponseType(typeof(QueryProductos))]
        public async Task<IHttpActionResult> GetProductos (long id = 0, string nombre = "", string descripcion = "", string categoria = "", string marca = "", double precio_min = 0, double precio_max = 0, long proveedor = 0, string ip = "", long cliente_id = 0, int page = 0, int items_per_page = -1, string sort = "", string sort_type = "", string custom = "")
        {
            Parametros _params = new Parametros()
            {
                id = id,
                nombre = nombre,
                descripcion = descripcion,
                marca = marca,
                precio_min = precio_min,
                precio_max = precio_max,
                proveedor = proveedor,
                ip = ip,
                cliente_id = cliente_id,
                categoria = categoria,
                page = page,
                items_per_page = items_per_page,
                sort = sort,
                sort_type = sort_type,
                custom = custom
            };
            string parameters = WebClientHelper.ParametrosSearch(_params);
            
            string path = string.Format("{0}?{1}", StringResources.ServicioBuscarProductos, parameters); // Path base de los productos

            QueryProductos productos = null;
            HttpResponseMessage response = await WebClientHelper.Client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                productos = await response.Content.ReadAsAsync<QueryProductos>();
            }

            if (productos == null)
            {
                return NotFound();
            }

            return Ok(productos);
        }

        // Get
        [Route("api/producto/campanias")]
        [ResponseType(typeof(QueryCampanias))]
        public async Task<IHttpActionResult> GetCampanias(string estado = "", int page = 0, int items_per_page = 0, string sort = "", string sort_type = "", string custom = "", bool detalle = true)
        {
            Parametros _params = new Parametros()
            {
                estado = estado,
                page = page,
                items_per_page = items_per_page,
                sort = sort,
                sort_type = sort_type,
                custom = custom
            };
            string parameters = WebClientHelper.ParametrosSearch(_params);
            string path = string.Format("{0}?{1}", StringResources.ServicioCampanias, parameters);

            QueryCampanias campanias = null;
            HttpResponseMessage response = await WebClientHelper.Client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                campanias = await response.Content.ReadAsAsync<QueryCampanias>();
            }

            if (campanias == null)
            {
                return NotFound();
            }

            if (detalle == false)
            {
                foreach (var item in campanias.campanias)
                {
                    item.productos = null;
                }
            }

            return Ok(campanias);
        }
    }
}