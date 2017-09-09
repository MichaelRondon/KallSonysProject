using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace B2CWS.Models
{
    public class Producto
    {
        public long id { get; set; }
        public string nombre { get; set; }
        public string descripcion { get; set; }
        public double precio { get; set; }
        public string estado { get; set; }
        public string categoria { get; set; }
        public string[] key_words { get; set; }
        public object[] proveedores { get; set; }
        public int disponibilidad { get; set; }
        // (yyyy-MM-dd HH:mm:ss)
        public string fecha_rev_disponibilidad { get; set; }

    }
}