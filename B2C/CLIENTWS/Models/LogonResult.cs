using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CLIENTWS.Models
{
    public class LogonResult
    {
        public bool result { get; set; }
        public string token { get; set; }
        public string message { get; set; }
    }
}