using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace B2CWS.Models
{
    public class Parametros
    {
        public long id { get; set; }
        public string nombre { get; set; }
        public string descripcion { get; set; }
        public string categoria { get; set; }
        public string marca { get; set; }
        public double precio_min { get; set; }
        public double precio_max { get; set; }
        public long proveedor { get; set; }
        public string ip { get; set; }
        public long cliente_id { get; set; }
        public int page { get; set; }
        public int items_per_page { get; set; }
        public string sort { get; set; }
        public string sort_type { get; set; }
        public string custom { get; set; }
        public string fecha_min { get; set; }
        public string fecha_max { get; set; }
        public int tamanio { get; set; }
        public string estado { get; set; }
    }
}