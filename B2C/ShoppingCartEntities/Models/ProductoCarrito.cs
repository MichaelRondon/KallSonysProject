using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace ShoppingCartEntities.Models
{
    public class ProductoCarrito
    {
        [Required]
        public long idProducto { get; set; }
        [Required]
        [Range(0, int.MaxValue)]
        public int cantidad { get; set; }
    }
}