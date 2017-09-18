using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace B2CWS.Models
{
    public class Campania
    {
        public long id { get; set; }
        public string nombre { get; set; }
        public string descripcion { get; set; }
        public Producto[] productos { get; set; }
        public string estado { get; set; }
        public string fecha_inicio { get; set; } // (yyyy-MM-dd)	
        public string fecha_fin { get; set; } // (yyyy-MM-dd)	
        public string urlImage { get; set; }
    }
}