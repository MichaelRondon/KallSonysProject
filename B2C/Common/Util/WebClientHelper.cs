using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using Common.DTO;
using System.Net.Http.Headers;
using System.Configuration;
using System.Text;

namespace Common.Util
{
    public static class WebClientHelper
    {
        private static HttpClient _clientProductos;
        private static HttpClient _clientBUS;
        private static HttpClient _clientColaFactur;

        public static HttpClient Client
        {
            get
            {
                if (_clientProductos == null)
                {
                    _clientProductos = new HttpClient();
                    _clientProductos.BaseAddress = new Uri(ConfigurationManager.AppSettings["RutaBaseServicios"]);
                    _clientProductos.DefaultRequestHeaders.Accept.Clear();
                    _clientProductos.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                }
                return _clientProductos;
            }
        }

        public static HttpClient ClientBPM
        {
            get
            {
                if (_clientBUS == null)
                {
                    _clientBUS = new HttpClient();
                    _clientBUS.BaseAddress = new Uri(ConfigurationManager.AppSettings["RutaBaseBPMS"]);
                    _clientBUS.DefaultRequestHeaders.Accept.Clear();
                    _clientBUS.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                }
                return _clientBUS;
            }
        }

        public static HttpClient ClientColaFactur
        {
            get
            {
                if (_clientColaFactur == null)
                {
                    _clientColaFactur = new HttpClient();
                    _clientColaFactur.BaseAddress = new Uri(ConfigurationManager.AppSettings["RutaBaseColaFactur"]);
                    _clientColaFactur.DefaultRequestHeaders.Accept.Clear();
                    _clientColaFactur.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                }
                return _clientColaFactur;
            }
        }

        public static string ParametrosSearch(Parametros parametros)
        {
            StringBuilder sb = new StringBuilder();

            if (parametros.id != 0)
            {
                sb.Append(string.Format("&id={0}", parametros.id));
            }
            if (!string.IsNullOrEmpty(parametros.nombre))
            {
                sb.Append(string.Format("&nombre={0}", parametros.nombre));
            }
            if (!string.IsNullOrEmpty(parametros.descripcion))
            {
                sb.Append(string.Format("&descripcion={0}", parametros.descripcion));
            }
            if (!string.IsNullOrEmpty(parametros.categoria))
            {
                sb.Append(string.Format("&categoria={0}", parametros.categoria));
            }
            if (!string.IsNullOrEmpty(parametros.marca))
            {
                sb.Append(string.Format("&marca={0}", parametros.marca));
            }
            if (parametros.precio_min != 0)
            {
                sb.Append(string.Format("&precio_min={0}", parametros.precio_min));
            }
            if (parametros.precio_max != 0)
            {
                sb.Append(string.Format("&precio_max={0}", parametros.precio_max));
            }
            if (parametros.proveedor != 0)
            {
                sb.Append(string.Format("&proveedor={0}", parametros.proveedor));
            }
            if (!string.IsNullOrEmpty(parametros.ip))
            {
                sb.Append(string.Format("&ip={0}", parametros.ip));
            }
            if (parametros.cliente_id != 0)
            {
                sb.Append(string.Format("&cliente_id={0}", parametros.cliente_id));
            }
            if (parametros.page != 0)
            {
                sb.Append(string.Format("&page={0}", parametros.page));
            }
            if (parametros.items_per_page.HasValue && parametros.items_per_page.Value != -1)
            {
                sb.Append(string.Format("&items_per_page={0}", parametros.items_per_page));
            }
            if (!string.IsNullOrEmpty(parametros.sort))
            {
                sb.Append(string.Format("&sort={0}", parametros.sort));
            }
            if (!string.IsNullOrEmpty(parametros.sort_type))
            {
                sb.Append(string.Format("&sort_type={0}", parametros.sort_type));
            }
            if (!string.IsNullOrEmpty(parametros.custom))
            {
                sb.Append(string.Format("&custom={0}", parametros.custom));
            }
            if (!string.IsNullOrEmpty(parametros.fecha_min))
            {
                sb.Append(string.Format("&fecha_min={0}", parametros.fecha_min));
            }
            if (!string.IsNullOrEmpty(parametros.fecha_max))
            {
                sb.Append(string.Format("&fecha_max={0}", parametros.fecha_max));
            }
            if (parametros.tamanio != 0)
            {
                sb.Append(string.Format("&tamanio={0}", parametros.tamanio));
            }
            if (!string.IsNullOrEmpty(parametros.estado))
            {
                sb.Append(string.Format("&estado={0}", parametros.estado));
            }
            if (parametros.size != 0)
            {
                sb.Append(string.Format("&size={0}", parametros.size));
            }

            if (sb.Length > 0)
            {
                sb.Remove(0, 1);
            }
            if (!string.IsNullOrEmpty(parametros.scrollId))
            {
                sb.Append(string.Format("&scrollId={0}", parametros.scrollId));
            }

            return sb.ToString();
        }
    }
}