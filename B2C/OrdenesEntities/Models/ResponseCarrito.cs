using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace OrdenesEntities.Models
{
    public class ResponseCarrito
    {
        public string estatus { get; set; }
        public bool exitoso { get; set; }
        public string mensaje { get; set; }
    }
}