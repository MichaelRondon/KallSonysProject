//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace ClientesEF
{
    using System;
    using System.Collections.Generic;
    
    public partial class ADDRESS
    {
        public ADDRESS()
        {
            this.CUSTOMERs = new HashSet<CUSTOMER>();
        }
    
        public string STREET { get; set; }
        public string STATE { get; set; }
        public string ZIP { get; set; }
        public string COUNTRY { get; set; }
        public string ADDRESSTYPE { get; set; }
        public string ADDRID { get; set; }
        public string CITY { get; set; }
    
        public virtual ICollection<CUSTOMER> CUSTOMERs { get; set; }
    }
}