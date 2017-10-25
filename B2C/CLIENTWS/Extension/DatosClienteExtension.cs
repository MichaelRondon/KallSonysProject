using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using CLIENTWS.Models;
using ClientesEntities.Models;

namespace CLIENTWS.Extension
{
    public static class DatosClienteExtension
    {
        public static IEnumerable<DatosCliente> ToDatosClienteCollection(this IEnumerable<ClientesEntities.Models.Cliente> from)
        {
            List<DatosCliente> retorno = new List<DatosCliente>();
            DatosCliente response = null;

            foreach (var cliente in from)
            {
                response = new DatosCliente()
                {
                    apellidos = cliente.apellidos,
                    correo_e = cliente.correo_e,
                    documento = cliente.documento,
                    estatus = cliente.estatus,
                    nombres = cliente.nombres,
                    telefono = cliente.telefono
                };

                if (!string.IsNullOrEmpty(cliente.numero))
                {
                    response.datos_tarjeta = new DatosTarjeta()
                    {
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
                            response.direcciones = new List<Models.Direccion>();
                        }
                        ((List<Models.Direccion>)response.direcciones).Add(new Models.Direccion()
                        {
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

                retorno.Add(response);
            }

            return retorno;
        }
    }
}