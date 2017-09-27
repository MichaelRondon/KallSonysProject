using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CLIENTWS.Models
{
    public class LogonRequest
    {
        public string ID { get; set; }
        public string e_mail { get; set; }
        public string passwd { get; set; }
    }
}