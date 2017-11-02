﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ShoppingCartEntities.Models;

namespace ShoppingCartBC.Contratos
{
    public interface IShoppingCartBC
    {
        ResponseCarrito AgregarProducto(string idCliente, ProductoCarrito producto);
        IEnumerable<ProductoCarrito> ConsultarCarrito(string idCliente);
    }
}