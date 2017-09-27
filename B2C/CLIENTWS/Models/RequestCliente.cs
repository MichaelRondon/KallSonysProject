using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CLIENTWS.Models
{
    public class RequestCliente
    {
        public string documento { get; set; }
        public string nombres { get; set; }
        public string apellidos { get; set; }
        public string telefono { get; set; }
        public string correo_e { get; set; }
        public string password { get; set; }
        public string estatus { get; set; }
        public DatosTarjeta datos_tarjeta { get; set; }
    }
}
