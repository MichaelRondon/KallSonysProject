using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CLIENTWS.Models
{
    public class DatosCliente : RequestCliente
    {
        public IEnumerable<Direccion> direcciones { get; set; }
    }
}