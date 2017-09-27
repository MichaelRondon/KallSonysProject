using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CLIENTWS.Models
{
    public class Direccion
    {
        public string direccion { get; set; }
        public string calle { get; set; }
        public string estado { get; set; }
        public string zipcode { get; set; }
        public string pais { get; set; }
        public string tipo { get; set; }
        public string ciudad { get; set; }
    }
}