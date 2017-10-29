#region Directivas using
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using B2CWS.Models;
using System.Net.Http.Headers;
using System.Text;
using Common.DTO;
using Common.Util;
using B2CWS.Util;
using System.Web.Http.Cors;
using OrdenesEntities.Models;
using OrdenesBC.Contratos;
using OrdenesBC.Implementaciones;
#endregion

namespace B2CWS.Controllers
{
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class ProductosController : ApiController
    {
        #region Atributos

        private IOrdenesBC _ordenesDAC;

        #endregion

        #region Propiedades

        public IOrdenesBC OrdenesDAC
        {
            get
            {
                if (_ordenesDAC == null)
                {
                    _ordenesDAC = new OrdenesBC.Implementaciones.OrdenesBC();
                }
                return _ordenesDAC;
            }
        }

        #endregion

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

            QueryProductos productos = await OrdenesDAC.HistoricoVendidos(_params);

            if (productos == null)
            {
                return NotFound();
            }
            else
            {
                if (productos.productos != null)
                {
                    foreach (var prod in productos.productos)
                    {
                        prod.urlImage = Url.Route("DefaultApi", new { controller = "ImageThumb", id = prod.id });
                    }
                }
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

            QueryProductos productos = await OrdenesDAC.BuscarProductos(_params);

            if (productos == null)
            {
                return NotFound();
            }
            else
            {
                if (productos.productos != null)
                {
                    foreach (var prod in productos.productos)
                    {
                        prod.urlImage = Url.Route("DefaultApi", new { controller = "ImageThumb", id = prod.id });
                    }
                }
            }

            return Ok(productos);
        }

        // Get
        [Route("api/producto/campanias")]
        [ResponseType(typeof(QueryCampanias))]
        public async Task<IHttpActionResult> GetCampanias(string estado = "", int page = 0, int items_per_page = 0, string sort = "", string sort_type = "", string custom = "", bool detalle = true, string categoria = "")
        {
            Parametros _params = new Parametros()
            {
                estado = estado,
                page = page,
                items_per_page = items_per_page,
                sort = sort,
                sort_type = sort_type,
                custom = custom,
                categoria = categoria
            };

            QueryCampanias campanias = await OrdenesDAC.BuscarCampanias(_params);

            if (campanias == null)
            {
                return NotFound();
            }
            else
            {
                if (campanias.campanias != null)
                {
                    foreach (var item in campanias.campanias)
                    {
                        if (detalle == false)
                        {
                            item.productos = null;
                        }
                        else
                        {
                            if (item.productos != null)
                            {
                                foreach (var prod in item.productos)
                                {
                                    prod.urlImage = Url.Route("DefaultApi", new { controller = "ImageThumb", id = prod.id });
                                }
                            }
                        }
                        item.urlImage = Url.Route("DefaultApi", new { controller = "ImageCampania", id = item.id });
                    }
                }
            }

            return Ok(campanias);
        }

        // Get
        [Route("api/producto/slider")]
        [ResponseType(typeof(IEnumerable<string>))]
        public IHttpActionResult GetSlider()
        {
            List<string> slider = new List<string>();
            IEnumerable<string> imgsSlider = null;

            imgsSlider = ImageDownloader.GetSliderImages();

            if (imgsSlider == null)
            {
                return NotFound();
            }
            else
            {
                foreach (var item in imgsSlider)
                {
                    slider.Add(Url.Route("DefaultApi", new { controller = "ImageSlider", id = item }));
                }
            }

            return Ok(slider);
        }

        //// Get
        [Route("api/producto/{id}")]
        [ResponseType(typeof(Producto))]
        public async Task<IHttpActionResult> GetProducto(long id)
        {
            Producto producto = await OrdenesDAC.ConsultarProducto(id);

            if (producto == null)
            {
                return NotFound();
            }
            else
            {
                if (producto.id.HasValue)
                {
                    producto.urlImage = Url.Route("DefaultApi", new { controller = "ImageThumb", id = producto.id });
                }
            }

            return Ok(producto);
        }

        //// Get
        [Route("api/categorias")]
        [ResponseType(typeof(IEnumerable<Categoria>))]
        public async Task<IHttpActionResult> GetCategorias(int page = 0, int size = 0)
        {
            Parametros _params = new Parametros()
            {
                page = page,
                size = size
            };

            IEnumerable<Categoria> categorias = await OrdenesDAC.BuscarCategorias(_params);

            if (categorias == null)
            {
                return NotFound();
            }

            return Ok(categorias);
        }

    }
}