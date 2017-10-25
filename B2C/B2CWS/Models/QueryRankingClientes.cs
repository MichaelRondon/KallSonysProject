using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace B2CWS.Models
{
    public class QueryRankingClientes
    {
        public IEnumerable<ClienteRanking> ranking { get; set; }
        //public Page page { get; set; }
    }
}