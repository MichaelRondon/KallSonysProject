using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CLIENTWS.Models
{
    public class ResponseCliente
    {
        public bool success { get; set; }
        public int status { get; set; }
        public string mensaje { get; set; }
    }
}