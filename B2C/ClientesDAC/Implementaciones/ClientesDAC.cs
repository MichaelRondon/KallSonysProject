using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ClientesDAC.Contratos;
using ClientesDomain;
using ClientesDAC.Util;
using ClientesEF;
using Oracle.DataAccess.EntityFramework;

namespace ClientesDAC.Implementaciones
{
    public class ClientesDAC : IClientesDAC
    {
        public string ActualizarCliente(Cliente cliente)
        {
            string resultado = string.Empty;

            resultado = cliente.Validar();

            if (!string.IsNullOrEmpty(resultado))
            {
                return resultado;
            }

            // Validar existencia del cliente
            ClientesEF.CUSTOMER clienteActual = null;
            ClientesEF.CUSTOMER clienteValidar = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    clienteActual = context.CUSTOMERs.FirstOrDefault(p => p.EMAIL.Equals(cliente.correo_e) || p.CUSTID.Equals(cliente.documento));

                    if (clienteActual == null)
                    {
                        resultado = "Cliente no existe.";
                        return resultado;
                    }

                    // Validar que no se vaya a tomar una dirección de correo-e existente
                    clienteValidar = context.CUSTOMERs.FirstOrDefault(p => p.EMAIL.Equals(cliente.correo_e) && !p.CUSTID.Equals(cliente.documento));

                    if (clienteValidar != null)
                    {
                        resultado = "Dirección de correo electrónico ya registrada.";
                        return resultado;
                    }

                    // Actualizar datos de cliente
                    clienteActual.EMAIL = cliente.correo_e;
                    clienteActual.FNAME = cliente.nombres;
                    clienteActual.LNAME = cliente.apellidos;
                    clienteActual.CREDITCARDTYPE = cliente.tipo;
                    clienteActual.PHONENUMBER = cliente.telefono;
                    clienteActual.STATUS = cliente.estatus;

                    if (!string.IsNullOrEmpty(cliente.numero))
                    {
                        clienteActual.CREDITCARDNUMBER = StringHelper.Encrypt(cliente.numero, clienteActual.SALT, true);
                    }

                    context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                return ex.Message;
            }

            return resultado;
        }

        public string CrearCliente(Cliente cliente)
        {
            string resultado = string.Empty;

            resultado = cliente.Validar();

            if (!string.IsNullOrEmpty(resultado))
            {
                return resultado;
            }

            // Validar que no exista
            ClientesEF.CUSTOMER clienteActual = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    clienteActual = context.CUSTOMERs.FirstOrDefault(p => p.EMAIL.Equals(cliente.correo_e) || p.CUSTID.Equals(cliente.documento));
                }
            }
            catch (Exception ex)
            {
                return ex.Message;
            }

            if (clienteActual != null)
            {
                resultado = "Cliente ya registrado.";
                return resultado;
            }

            // Crear cliente
            ClientesEF.CUSTOMER clientePersistir = new CUSTOMER()
            {
                CUSTID = cliente.documento,
                EMAIL = cliente.correo_e,
                FNAME = cliente.nombres,
                LNAME = cliente.apellidos,
                CREDITCARDTYPE = cliente.tipo,
                PHONENUMBER = cliente.telefono
            };

            // Caracteristicas de seguridad
            clientePersistir.SALT = StringHelper.GenerateSalt();
            clientePersistir.PASSWORD = StringHelper.GeneratePassword(cliente.password, clientePersistir.SALT);

            if (!string.IsNullOrEmpty(cliente.numero))
            {
                clientePersistir.CREDITCARDNUMBER = StringHelper.Encrypt(cliente.numero, clientePersistir.SALT, true);
            }

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    context.CUSTOMERs.Add(clientePersistir);
                    context.SaveChanges();
                }
            }
            catch (Exception ex)
            {
                return ex.Message;
            }

            return resultado;
        }

        public ClientesDomain.Cliente ConsultarCliente(string ID)
        {
            ClientesDomain.Cliente cliente = null;
            CUSTOMER customer = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    customer = context.CUSTOMERs.FirstOrDefault(p => p.CUSTID.Equals(ID));

                    if (customer != null)
                    {
                        cliente = new Cliente() {
                            apellidos = customer.LNAME,
                            correo_e = customer.EMAIL,
                            documento = customer.CUSTID,
                            estatus = customer.STATUS,
                            nombres = customer.FNAME,
                            tipo = customer.CREDITCARDTYPE,
                            telefono = customer.PHONENUMBER
                        };

                        if (!string.IsNullOrEmpty(customer.CREDITCARDNUMBER))
                        {
                            cliente.numero = StringHelper.MaskText(StringHelper.Decrypt(customer.CREDITCARDNUMBER, customer.SALT, true), 4);
                        }
                        
                        foreach (var dir in customer.ADDRESSes)
                        {
                            if (cliente.direcciones == null)
                            {
                                cliente.direcciones = new List<Direccion>();
                            }
                            ((List<Direccion>)cliente.direcciones).Add(new Direccion() {
                                calle = dir.STREET,
                                ciudad = dir.CITY,
                                direccion = dir.ADDRID,
                                estado = dir.STATE,
                                pais = dir.COUNTRY,
                                tipo = dir.ADDRESSTYPE,
                                zipcode = dir.ZIP
                            });
                        }
                    }
                }
            }
            catch (Exception)
            {
                throw;
            }

            return cliente;
        }

        public LogonStatus ValidarCredencialesCliente(string ID, string e_mail, string passwd)
        {
            LogonStatus status = new LogonStatus() {
                message = "OK",
                result = true,
                token = string.Empty
            };

            CUSTOMER clienteActual = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    clienteActual = context.CUSTOMERs.FirstOrDefault(p => p.EMAIL.Equals(e_mail) || p.CUSTID.Equals(ID));
                }
            }
            catch (Exception ex)
            {
                status.message = ex.Message;
                status.result = false;
            }

            if (clienteActual != null)
            {
                if (!StringHelper.GeneratePassword(passwd, clienteActual.SALT).Equals(clienteActual.PASSWORD))
                {
                    status.message = "Nombre de usuario o contraseña errados.";
                    status.result = false;
                }
                else
                {
                    status.token = StringHelper.GeneratePassword(DateTime.Now.ToString(), clienteActual.SALT);
                }
            }
            else
            {
                status.message = "Nombre de usuario o contraseña errados.";
                status.result = false;
            }

            return status;
        }
    }
}
