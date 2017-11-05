using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace OrdenesEntities.Models
{
    public class DatosPago
    {
        public string numeroTarjeta { get; set; }
        public string tipoTarjeta { get; set; }
        [Required]
        [MaxLength(40)]
        public string direccionEntrega { get; set; }
        [Required]
        public string paisEntrega { get; set; }
        [Required]
        public string ciudadEntrega { get; set; }
    }
}
