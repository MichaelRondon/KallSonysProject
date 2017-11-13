using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrdenesEntities.Models
{
    public class QueryOrden
    {
        public Orden orden { get; set; }
        public IEnumerable<ItemProductoCarrito> items { get; set; }
    }
}
