﻿#region Directivas using
using System.Collections.Generic;
using System.Web.Http;
using System.Web.Http.Description;
using System.Threading.Tasks;
using OrdenesEntities.Models;
using OrdenesBC.Contratos;
using System.Web.Http.Cors;
#endregion

namespace B2CWS.Controllers
{
    [EnableCors("*", "*", "*")]
    public class ShoppingCartController : ApiController
    {

        #region Atributos

        private IOrdenesBC _dac;

        #endregion
        
        #region Propiedades

        private IOrdenesBC DAC
        {
            get
            {
                if (_dac == null)
                {
                    _dac = new OrdenesBC.Implementaciones.OrdenesBC();
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
        [ResponseType(typeof(IEnumerable<ItemProductoCarrito>))]
        public async Task<IHttpActionResult> get(string idCliente)
        {
            IEnumerable<ItemProductoCarrito> carrito = await DAC.ConsultarCarrito(idCliente);

            if (carrito == null)
            {
                return NotFound();
            }
            else
            {
                foreach (var prod in carrito)
                {
                    prod.producto.urlImage = Url.Route("DefaultApi", new { controller = "ImageThumb", id = prod.producto.id });
                }
                return Ok(carrito);
            }
        }

        [Route("api/shoppingCart/{idCliente}/checkout")]
        [ResponseType(typeof(QueryOrden))]
        [HttpPost]
        public async Task<IHttpActionResult> checkout(string idCliente, [FromBody]IEnumerable<ProductoCarrito> producto)
        {
            if (producto == null || !ModelState.IsValid)
            {
                return BadRequest();
            }

            QueryOrden response = await DAC.Checkout(idCliente, producto);

            if (response == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(response); 
            }
        }

        [Route("api/shoppingCart/{idCliente}/procesarpago")]
        [ResponseType(typeof(ResponseCarrito))]
        [HttpPost]
        public async Task<IHttpActionResult> procesarPago(string idCliente, [FromBody]DatosPago datosPago)
        {
            if (datosPago == null || !ModelState.IsValid)
            {
                return BadRequest();
            }

            ResponseCarrito response = await DAC.ProcesarPago(idCliente, datosPago);

            if (response == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(response);
            }
        }

        [Route("api/shoppingCart/{idCliente}/subtotal")]
        [ResponseType(typeof(QueryOrden))]
        [HttpPost]
        public async Task<IHttpActionResult> subtotal(string idCliente, [FromBody]IEnumerable<ProductoCarrito> producto)
        {
            if (producto == null || !ModelState.IsValid)
            {
                return BadRequest();
            }

            QueryOrden response = await DAC.Subtotal(idCliente, producto);

            if (response == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(response);
            }
        }
    }
}