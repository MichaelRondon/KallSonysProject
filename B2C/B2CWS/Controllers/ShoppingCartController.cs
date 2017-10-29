using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using System.Web.Http.Cors;
using ShoppingCartEntities.Models;
using ShoppingCartBC.Contratos;
using ShoppingCartBC.Implementaciones;

namespace B2CWS.Controllers
{
    public class ShoppingCartController : ApiController
    {

        #region Atributos

        private IShoppingCartBC _dac;

        #endregion
        
        #region Propiedades

        private IShoppingCartBC DAC
        {
            get
            {
                if (_dac == null)
                {
                    _dac = new ShoppingCartBC.Implementaciones.ShoppingCartBC();
                }
                return _dac;
            }
        } 

        #endregion

        [Route("api/shoppingCart/{idCliente}")]
        [ResponseType(typeof(ResponseCarrito))]
        public IHttpActionResult post(string idCliente, [FromBody]ProductoCarrito producto)
        {
            if (producto == null || !ModelState.IsValid)
            {
                return BadRequest();
            }

            ResponseCarrito response = DAC.AgregarProducto(idCliente, producto);

            return Ok(response);
        }

        [Route("api/shoppingCart/{idCliente}")]
        [ResponseType(typeof(IEnumerable<ProductoCarrito>))]
        public IHttpActionResult get(string idCliente)
        {
            IEnumerable<ProductoCarrito> carrito = DAC.ConsultarCarrito(idCliente);

            if (carrito == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(carrito);
            }
        }
    }
}
