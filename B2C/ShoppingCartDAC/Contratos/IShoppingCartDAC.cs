using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ShoppingCartEntities.Models;

namespace ShoppingCartDAC.Contratos
{
    public interface IShoppingCartDAC
    {
        ResponseCarrito AgregarProducto(string idCliente, ProductoCarrito producto);
        IEnumerable<ProductoCarrito> ConsultarCarrito(string idCliente);
    }
}
