using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientesEntities.Models
{
    public class LogonStatus
    {
        public bool result { get; set; }
        public string message { get; set; }
        public string token { get; set; }
    }
}
