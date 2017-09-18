using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace B2CWS.Models
{
    public class QueryProductos
    {
        public List<Producto> productos { get; set; }
        public Page page { get; set; }
    }
}