using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using System.Web.Http.Cors;
using ShoppingCartEntities.Models;
using ShoppingCartDAC.Contratos;
using ShoppingCartDAC.Implementaciones;

namespace B2CWS.Controllers
{
    public class ShoppingCartController : ApiController
    {

        #region Atributos

        private IShoppingCartDAC _dac;

        #endregion
        
        #region Propiedades

        private IShoppingCartDAC DAC
        {
            get
            {
                if (_dac == null)
                {
                    _dac = new ShoppingCartDAC.Implementaciones.ShoppingCartDAC();
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
