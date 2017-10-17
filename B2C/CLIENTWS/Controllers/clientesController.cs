using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using CLIENTWS.Models;
using CLIENTWS.Extension;
using System.Web.Http.Description;
using System.Web.Http.Cors;

namespace CLIENTWS.Controllers
{
    //[EnableCors(origins: "http://mywebclient.azurewebsites.net", headers: "*", methods: "*")]
    [EnableCors(origins: "*", headers: "*", methods: "*")]
    public class clientesController : ApiController
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

        [ResponseType(typeof(ResponseCliente))]
        public IHttpActionResult post(RequestCliente request)
        {
            ResponseCliente response = new ResponseCliente() {
                mensaje = "Cliente creado exitosamente",
                status = 200,
                success = true
            };

            ClientesDomain.Cliente clienteCrear = new ClientesDomain.Cliente() {
                apellidos = request.apellidos,
                correo_e = request.correo_e,
                documento = request.documento,
                nombres = request.nombres,
                password = request.password,
                telefono = request.telefono
            };

            if (request.datos_tarjeta != null)
            {
                clienteCrear.numero = request.datos_tarjeta.numero;
                clienteCrear.tipo = request.datos_tarjeta.tipo;
            }

            string resultado = DAC.CrearCliente(clienteCrear);
            response.success = string.IsNullOrEmpty(resultado);

            if (!response.success)
            {
                response.mensaje = resultado;
            }

            return Ok(response);
        }

        [ResponseType(typeof(ResponseCliente))]
        public IHttpActionResult put(RequestCliente request)
        {
            ResponseCliente response = new ResponseCliente()
            {
                mensaje = "Cliente actualizado exitosamente",
                status = 200,
                success = true
            };

            ClientesDomain.Cliente clienteActualizar = new ClientesDomain.Cliente()
            {
                apellidos = request.apellidos,
                correo_e = request.correo_e,
                documento = request.documento,
                nombres = request.nombres,
                password = request.password,
                telefono = request.telefono,
                estatus = request.estatus
            };

            if (request.datos_tarjeta != null)
            {
                clienteActualizar.numero = request.datos_tarjeta.numero;
                clienteActualizar.tipo = request.datos_tarjeta.tipo;
            }

            response.mensaje = DAC.ActualizarCliente(clienteActualizar);
            response.success = string.IsNullOrEmpty(response.mensaje);

            return Ok(response);
        }

        [ResponseType(typeof(DatosCliente))]
        public IHttpActionResult get(string ID)
        {
            DatosCliente response = new DatosCliente();

            ClientesDomain.Cliente cliente = DAC.ConsultarCliente(ID);

            if (cliente != null)
            {
                response = new DatosCliente() {
                    apellidos = cliente.apellidos,
                    correo_e = cliente.correo_e,
                    documento = cliente.documento,
                    estatus = cliente.estatus,
                    nombres = cliente.nombres,
                    telefono = cliente.telefono
                };

                if (!string.IsNullOrEmpty(cliente.numero))
                {
                    response.datos_tarjeta = new DatosTarjeta() {
                        numero = cliente.numero,
                        tipo = cliente.tipo
                    };
                }

                if (cliente.direcciones != null)
                {
                    foreach (var dir in cliente.direcciones)
                    {
                        if (response.direcciones == null)
                        {
                            response.direcciones = new List<Direccion>();
                        }
                        ((List<Direccion>)response.direcciones).Add(new Direccion() {
                            calle = dir.calle,
                            ciudad = dir.ciudad,
                            direccion = dir.direccion,
                            estado = dir.estado,
                            pais = dir.pais,
                            tipo = dir.tipo,
                            zipcode = dir.zipcode
                        });
                    }
                }
            }

            return Ok(response);
        }

        [ResponseType(typeof(IEnumerable<DatosCliente>))]
        public IHttpActionResult get()
        {
            IEnumerable<DatosCliente> response = new List<DatosCliente>();

            IEnumerable<ClientesDomain.Cliente> cliente = DAC.ConsultarCliente();

            response = cliente.ToDatosClienteCollection();

            return Ok(response);
        }
    }
}
