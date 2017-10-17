using ClientesDomain;
using CLIENTWS.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using System.Web.Http.Cors;

namespace CLIENTWS.Controllers
{
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class authController : ApiController
    {

        #region Atributos privados

        private ClientesDAC.Contratos.IClientesDAC _dac;

        private ClientesDAC.Contratos.IClientesDAC DAC
        {
            get
            {
                if (_dac == null)
                {
                    _dac = new ClientesDAC.Implementaciones.ClientesDAC();
                }
                return _dac;
            }
        }

        #endregion

        [HttpPost]
        [Route("api/auth/logon")]
        [ResponseType(typeof(LogonResult))]
        public IHttpActionResult Logon(LogonRequest logonRequest)
        {
            LogonResult result = new LogonResult() {
                result = true,
                message = string.Empty,
                token = string.Empty
            };

            LogonStatus status = DAC.ValidarCredencialesCliente(logonRequest.ID, logonRequest.e_mail, logonRequest.passwd);

            result.result = status.result;
            result.message = status.message;
            result.token = status.token;

            return Ok(result);
        }


        [HttpPost]
        [Route("api/auth/solicitarCambioPasswd")]
        [ResponseType(typeof(LogonResult))]
        public async Task<IHttpActionResult> solicitarCambioPasswd(LogonRequest logonRequest)
        {
            LogonResult result = new LogonResult() { result = true };

            LogonStatus status = await DAC.SolicitarCambioClave(logonRequest.ID, logonRequest.e_mail);

            result.result = status.result;
            result.message = status.message;
            result.token = status.token;

            return Ok(result);
        }

        [HttpPost]
        [Route("api/auth/procesarCambioPasswd")]
        [ResponseType(typeof(LogonResult))]
        public async Task<IHttpActionResult> procesarCambioPasswd(LogonRequest logonRequest)
        {
            LogonResult result = new LogonResult() { result = true };

            LogonStatus status = await DAC.ProcesarCambioClave(logonRequest.ID, logonRequest.e_mail, logonRequest.token, logonRequest.passwd);

            result.result = status.result;
            result.message = status.message;
            result.token = status.token;

            return Ok(result);
        }
    }
}
