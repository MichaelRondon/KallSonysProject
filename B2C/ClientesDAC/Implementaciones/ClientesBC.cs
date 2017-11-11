using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ClientesBC.Contratos;
using ClientesEntities.Models;
using ClientesBC.Util;
using ClientesEF;
using Common;
using Common.Util;
using Common.DTO;
using Oracle.DataAccess.EntityFramework;
using System.Configuration;

namespace ClientesBC.Implementaciones
{
    public class ClientesBC : IClientesBC
    {
        private const string ESTATUS_CLIENTE_PLATEADO = "PLATEADO";

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
                    clienteActual = context.CUSTOMERs.SingleOrDefault(p => p.EMAIL.Equals(cliente.correo_e) || p.CUSTID.Equals(cliente.documento));

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
                PHONENUMBER = cliente.telefono,
                STATUS = ESTATUS_CLIENTE_PLATEADO
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

        public ClientesEntities.Models.Cliente ConsultarCliente(string ID)
        {
            ClientesEntities.Models.Cliente cliente = null;
            // CUSTOMER customer = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    var customers = context.CUSTOMERs.Where(p => p.CUSTID.Equals(ID, StringComparison.InvariantCultureIgnoreCase));
                    
                    foreach (var customer in customers)
                    {
                        cliente = new Cliente()
                        {
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
                            ((List<Direccion>)cliente.direcciones).Add(new Direccion()
                            {
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

        public async Task<ClientesEntities.Models.LogonStatus>  SolicitarCambioClave(string ID, string e_mail)
        {
            ClientesEntities.Models.LogonStatus status = new LogonStatus() { result = true };

            CUSTOMER clienteActual = null;
            string token = null, url = null;
            DateTime fechaHora = DateTime.Now;

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
                // Cliente existe. 
                // Generar token
                token = StringHelper.GeneratePassword(fechaHora.ToString(), clienteActual.SALT);
                url = string.Format(ConfigurationManager.AppSettings["urlCambioPasswd"], token);

                try
                {

                    // Persistir petición
                    using (var context = new ClientesEF.Entities())
                    {
                        PASSWD_CHANGE_REQUEST request = new PASSWD_CHANGE_REQUEST()
                        {
                            CUSTID = clienteActual.CUSTID,
                            EMAIL = clienteActual.EMAIL,
                            PROCESSED = "N",
                            REQUEST_DATE = fechaHora,
                            TOKEN = token
                        };
                        context.PASSWD_CHANGE_REQUEST.Add(request);
                        context.SaveChanges();
                    }

                    // Enviar mail
                    await MailHelper.SendEmail(clienteActual.EMAIL, "Solicitud de cambio de clave", StringHelper.MensajeCambioClave(url));

                    status.message = "Enviado mail con indicaciones.";
                    status.result = true;
                    status.token = token;
                }
                catch (Exception ex)
                {
                    status.message = ex.Message;
                    status.result = false;
                }
            }
            else
            {
                status.message = "Usuario o dirección de correo errados.";
                status.result = false;
            }

            return status;
        }

        public async Task<ClientesEntities.Models.LogonStatus> ProcesarCambioClave(string ID, string e_mail, string token, string passwd)
        {
            ClientesEntities.Models.LogonStatus status = new LogonStatus() { result = true };

            CUSTOMER clienteActual = null;
            PASSWD_CHANGE_REQUEST request = null;
            DateTime fechaHora = DateTime.Now;
            
            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    request = context.PASSWD_CHANGE_REQUEST.SingleOrDefault(p => p.TOKEN.Equals(token) && p.PROCESSED.Equals("N"));

                    //clienteActual = context.CUSTOMERs.FirstOrDefault(p => p.EMAIL.Equals(e_mail) || p.CUSTID.Equals(ID));
                    //request = clienteActual.PASSWD_CHANGE_REQUEST.FirstOrDefault(p => p.TOKEN.Equals(token) && p.PROCESSED.Equals("N"));

                    if (request != null)
                    {
                        clienteActual = request.CUSTOMER;

                        // Los datos son consistentes. Procesar el cambio
                        clienteActual.PASSWORD = StringHelper.GeneratePassword(passwd, clienteActual.SALT);
                        request.PROCESSED = "Y";
                        context.SaveChanges();
                        status.message = "Cambio de clave procesado con éxito.";
                        status.result = true;
                    }
                    else
                    {
                        status.message = "Datos errados.";
                        status.result = false;
                    }
                }

                if (status.result == true)
                {
                    await MailHelper.SendEmail(clienteActual.EMAIL, "Cambio de clave exitoso", StringHelper.MensajeCambioClaveExitoso());
                }
            }
            catch (Exception ex)
            {
                status.message = ex.Message;
                status.result = false;
            }
            
            return status;
        }

        public IEnumerable<ClientesEntities.Models.Cliente> ConsultarCliente()
        {
            IEnumerable<ClientesEntities.Models.Cliente> clientes = null;
            Cliente cliente = null;
            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    var customers = context.CUSTOMERs.AsEnumerable();

                    if (customers != null)
                    {
                        if (clientes == null)
                        {
                            clientes = new List<ClientesEntities.Models.Cliente>();
                        }

                        foreach (var customer in customers)
                        {
                            cliente = new Cliente()
                            {
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
                                ((List<Direccion>)cliente.direcciones).Add(new Direccion()
                                {
                                    calle = dir.STREET,
                                    ciudad = dir.CITY,
                                    direccion = dir.ADDRID,
                                    estado = dir.STATE,
                                    pais = dir.COUNTRY,
                                    tipo = dir.ADDRESSTYPE,
                                    zipcode = dir.ZIP
                                });
                            }

                            ((List<ClientesEntities.Models.Cliente>)clientes).Add(cliente);
                        }
                    }
                }
            }
            catch (Exception)
            {
                throw;
            }

            return clientes;
        }

        public IEnumerable<Cliente> BuscarClientes(Parametros parametros)
        {
            List<Cliente> clientes = null;
            Cliente cliente = null;

            try
            {
                using (var context = new ClientesEF.Entities())
                {
                    var busqueda = context.CUSTOMERs.Where(p => p.EMAIL.Equals(parametros.e_mail, StringComparison.InvariantCultureIgnoreCase));

                    foreach (var customer in busqueda)
                    {
                        if (clientes == null)
                        {
                            clientes = new List<Cliente>();
                        }

                        cliente = new Cliente()
                        {
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
                            ((List<Direccion>)cliente.direcciones).Add(new Direccion()
                            {
                                calle = dir.STREET,
                                ciudad = dir.CITY,
                                direccion = dir.ADDRID,
                                estado = dir.STATE,
                                pais = dir.COUNTRY,
                                tipo = dir.ADDRESSTYPE,
                                zipcode = dir.ZIP
                            });
                        }

                        clientes.Add(cliente);
                    }
                }
            }
            catch (Exception)
            {
                clientes = null;
            }

            return clientes;
        }
    }
}
