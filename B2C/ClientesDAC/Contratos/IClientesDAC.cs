using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ClientesDomain;

namespace ClientesDAC.Contratos
{
    public interface IClientesDAC
    {
        string CrearCliente(ClientesDomain.Cliente cliente);
        string ActualizarCliente(ClientesDomain.Cliente cliente);
        ClientesDomain.Cliente ConsultarCliente(string ID);
        IEnumerable<ClientesDomain.Cliente> ConsultarCliente();
        ClientesDomain.LogonStatus ValidarCredencialesCliente(string ID, string e_mail, string passwd);
        Task<ClientesDomain.LogonStatus> SolicitarCambioClave(string ID, string e_mail);
        Task<ClientesDomain.LogonStatus> ProcesarCambioClave(string ID, string e_mail, string token, string passwd);
    }
}
