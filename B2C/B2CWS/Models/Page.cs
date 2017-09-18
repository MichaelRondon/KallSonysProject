using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace B2CWS.Models
{
    public class Page
    {
        public int total_pages { get; set; } // : 9,
        public int total_elements { get; set; } // : 178,
        public int number { get; set; } // : 3,
        public string sort { get; set; } // : "precio",
        public string sort_type { get; set; } // : "ASC",
        public string custom { get; set; } // : "xxx"
    }
}
