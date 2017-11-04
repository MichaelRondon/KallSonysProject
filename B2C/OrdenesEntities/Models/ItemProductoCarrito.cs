using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrdenesEntities.Models
{
    public class ItemProductoCarrito
    {
        public Producto producto { get; set; }
        public ProductoCarrito itemCarrito { get; set; }
    }
}
