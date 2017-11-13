#region Directivas using
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using OrdenesEntities.Models;
using OrdenesBC.Contratos;
using System.Web.Http.Cors;
using System.Collections.Generic;
using System;
using System.Globalization;
#endregion

namespace B2CWS.Controllers
{
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class OrdenesController : ApiController
    {

        #region Atributos

        private IOrdenesBC _dac;

        #endregion

        #region Propiedades

        private IOrdenesBC DAC
        {
            get
            {
                if (_dac == null)
                {
                    _dac = new OrdenesBC.Implementaciones.OrdenesBC();
                }
                return _dac;
            }
        }

        #endregion

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

        [Route("api/ordenes/rankingClientesFechas")]
        [ResponseType(typeof(QueryRankingClientes))]
        public IHttpActionResult GetRankingClientesFechas(string fechaInicio, string fechaFin)
        {
            DateTime fechaIniConsultar, fechaFinConsultar;
            fechaIniConsultar = DateTime.ParseExact(fechaInicio, "yyyy-MM-dd", CultureInfo.CurrentCulture);
            fechaFinConsultar = DateTime.ParseExact(fechaFin, "yyyy-MM-dd", CultureInfo.CurrentCulture);
            QueryRankingClientes ranking = DAC.ConsultarRankingRangoFechas(fechaIniConsultar, fechaFinConsultar);

            if (ranking == null)
            {
                return NotFound();
            }

            return Ok(ranking);
        }

        [Route("api/ordenes/{idOrden}/total")]
        [ResponseType(typeof(Orden))]
        [HttpGet]
        public IHttpActionResult TotalOrden(int idOrden)
        {
            Orden total = DAC.ConsultarTotalOrden(idOrden);
            
            if (total != null)
            {
                return Ok(total);
            }
            else
            {
                return NotFound();
            }
        }

        [Route("api/ordenes/{idOrden}/detalle")]
        [ResponseType(typeof(IEnumerable<ItemProductoCarrito>))]
        [HttpGet]
        public IHttpActionResult DetalleOrden(int idOrden)
        {
            IEnumerable<ItemProductoCarrito> detalle = DAC.DetalleOrden(idOrden);

            if (detalle != null)
            {
                return Ok(detalle);
            }
            else
            {
                return NotFound();
            }
        }

        [Route("api/ordenes")]
        [ResponseType(typeof(IEnumerable<Orden>))]
        [HttpGet]
        public IHttpActionResult ConsultarOrdenes(int idProducto)
        {
            IEnumerable<Orden> ordenes = DAC.ConsultarOrdenesFiltros(new Common.DTO.Parametros() { idProducto = idProducto });

            if (ordenes != null)
            {
                return Ok(ordenes);
            }
            else
            {
                return NotFound();
            }
        }


        [Route("api/ordenes")]
        [ResponseType(typeof(ResponseOrdenes))]
        [HttpPut]
        public IHttpActionResult put(Orden request)
        {
            if (request == null)
            {
                return BadRequest();
            }

            ResponseOrdenes response = DAC.ActualizarOrden(request);
            
            return Ok(response);
        }

        [Route("api/ordenes/ordenesMes")]
        [ResponseType(typeof(ResumenOrdenesMes))]
        [HttpGet]
        public IHttpActionResult ResumenMes(int anio, int mes)
        {
            ResumenOrdenesMes resumen = DAC.OrdenesMes(anio, mes);

            if (resumen == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(resumen);
            }
        }

        [Route("api/ordenes/abiertas")]
        [ResponseType(typeof(IEnumerable<Orden>))]
        [HttpGet]
        public IHttpActionResult OrdenesAbiertas()
        {
            IEnumerable<Orden> abiertas = DAC.ConsultarOrdenesAbiertas();

            if (abiertas == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(abiertas);
            }
        }

        [Route("api/ordenes/rankingOrdenesFechas")]
        [ResponseType(typeof(IEnumerable<Orden>))]
        public IHttpActionResult GetRankingFacturacionFechas(string fechaInicio, string fechaFin)
        {
            DateTime fechaIniConsultar, fechaFinConsultar;
            fechaIniConsultar = DateTime.ParseExact(fechaInicio, "yyyy-MM-dd", CultureInfo.CurrentCulture);
            fechaFinConsultar = DateTime.ParseExact(fechaFin, "yyyy-MM-dd", CultureInfo.CurrentCulture);
            IEnumerable<Orden> ranking = DAC.ConsultarRankingFacturacionOrdenes(fechaIniConsultar, fechaFinConsultar);

            if (ranking == null)
            {
                return NotFound();
            }

            return Ok(ranking);
        }
    }
}
