using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using B2CWS.Models;
using System.Net.Http.Headers;
using System.Configuration;
using System.Text;

namespace B2CWS.Util
{
    public static class WebClientHelper
    {
        private static HttpClient _client;

        public static HttpClient Client
        {
            get
            {
                if (_client == null)
                {
                     _client = new HttpClient();
                    _client.BaseAddress = new Uri(ConfigurationManager.AppSettings["RutaBaseServicios"]);
                    _client.DefaultRequestHeaders.Accept.Clear();
                    _client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                }
                return _client;
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
            if (parametros.items_per_page != -1)
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

            if (sb.Length > 0)
            {
                sb.Remove(0, 1);
            }

            return sb.ToString();
        }
    }
}