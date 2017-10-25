using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ClientesEntities.Models;

namespace ClientesDAC.Contratos
{
    public interface IClientesDAC
    {
        string CrearCliente(Cliente cliente);
        string ActualizarCliente(Cliente cliente);
        Cliente ConsultarCliente(string ID);
        IEnumerable<Cliente> ConsultarCliente();
        LogonStatus ValidarCredencialesCliente(string ID, string e_mail, string passwd);
        Task<LogonStatus> SolicitarCambioClave(string ID, string e_mail);
        Task<LogonStatus> ProcesarCambioClave(string ID, string e_mail, string token, string passwd);
    }
}
