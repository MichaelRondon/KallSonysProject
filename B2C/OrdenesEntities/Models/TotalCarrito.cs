using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ClientesEntities.Models;

namespace OrdenesEntities.Models
{
    public class TotalCarrito
    {
        public Cliente cliente { get; set; }
        public double? valorTotal { get; set; }
        public int itemsTotal { get; set; }
    }
}
