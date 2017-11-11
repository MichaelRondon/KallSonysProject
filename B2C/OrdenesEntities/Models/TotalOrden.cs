using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ClientesEntities.Models;

namespace OrdenesEntities.Models
{
    public class TotalOrden
    {
        public Cliente cliente { get; set; }
        public double valorTotal { get; set; }
        public int itemsTotal { get; set; }
        public int idOrden { get; set; }
        public string fecha_orden { get; set; }
    }
}
