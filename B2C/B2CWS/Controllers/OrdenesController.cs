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
using B2CWS.Util;
using System.Web.Http.Cors;

namespace B2CWS.Controllers
{
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class OrdenesController : ApiController
    {
        [Route("api/ordenes/rankingClientes")]
        [ResponseType(typeof(QueryRankingClientes))]
        public async Task<IHttpActionResult> GetRankingClientes(long idProducto = 0L, int page = 0, int items_per_page = 0, string sort = "", string sort_type = "", string custom = "")
        {
            QueryRankingClientes ranking = null;

            if (true)
            {
                ranking = new QueryRankingClientes(); // Consultar ranking desde la BD
            }

            if (ranking == null)
            {
                return NotFound();
            }

            return Ok(ranking);
        }

    }
}
