using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrdenesEntities.Models
{
    public class Producto
    {
        public long? id { get; set; }
        public string nombre { get; set; }
        public string descripcion { get; set; }
        public double? precio { get; set; }
        public string estado { get; set; }
        public string categoria { get; set; }
        public string[] key_words { get; set; }
        public object[] proveedores { get; set; }
        public int? disponibilidad { get; set; }
        public string marca { get; set; }
        public string fecha_rev_disponibilidad { get; set; }
        public string urlImage { get; set; }

    }
}
