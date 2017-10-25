using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using OrdenesEntities.Models;

namespace OrdenesEntities.Models
{
    public class QueryProductos
    {
        public List<Producto> productos { get; set; }
        public Page page { get; set; }
    }
}