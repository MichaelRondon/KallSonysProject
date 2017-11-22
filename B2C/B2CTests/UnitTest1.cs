#region Directivas using
using Microsoft.VisualStudio.TestTools.UnitTesting;
using ClientesBC.Contratos;
using OrdenesBC.Contratos;
using System.Threading.Tasks;
using ClientesEntities.Models;
using OrdenesEntities.Models;
using System.Linq;
using System.Collections.Generic;
#endregion

namespace B2CTests
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void ConsultaClienteEmailTest()
        {
            IClientesBC clientesBC = new ClientesBC.Implementaciones.ClientesBC();
            IEnumerable<Cliente> resultado = clientesBC.BuscarClientes(new Common.DTO.Parametros() { e_mail = "marcevaquiro@gmail.com" });
            Assert.IsTrue(resultado.Count() == 1);
        }

        [TestMethod]
        public void ConsultaClienteTest()
        {
            IClientesBC clientesBC = new ClientesBC.Implementaciones.ClientesBC();
            Cliente resultado = clientesBC.ConsultarCliente("CC79797979");
            Assert.IsTrue(resultado.correo_e.Equals("dmsan.chezr@gmail.com"));
        }

        [TestMethod]
        public async Task ConsultaProductosTest()
        {
            IOrdenesBC ordenesBC = new OrdenesBC.Implementaciones.OrdenesBC();
            Producto producto = await ordenesBC.ConsultarProducto(344076);
            Assert.IsNotNull(producto);
            Assert.IsNotNull(producto.nombre);
        }

        [TestMethod]
        public async Task ConsultaCampaniasTest()
        {
            IOrdenesBC ordenesBC = new OrdenesBC.Implementaciones.OrdenesBC();
            QueryCampanias campanias = await ordenesBC.BuscarCampanias(new Common.DTO.Parametros() { items_per_page = 20, categoria = "PRINCIPAL", estado = "ACTIVO" });
            Assert.IsTrue(campanias.campanias.Count == 4);
        }

        [TestMethod]
        public void LogonTest()
        {
            IClientesBC clientesBC = new ClientesBC.Implementaciones.ClientesBC();
            var resultado = clientesBC.ValidarCredencialesCliente("", "marcevaquiro@gmail.com", "1234");
            Assert.IsTrue(resultado.result);
        }

        [TestMethod]
        public void OrdenesAddTest()
        {
            IOrdenesBC OrdenesBC = new OrdenesBC.Implementaciones.OrdenesBC();
            var resultado = OrdenesBC.AgregarProducto("CC79797979", new ProductoCarrito() { idProducto = 344076, cantidad = 2 });
            Assert.IsTrue(resultado.exitoso);
        }

        [TestMethod]
        public async Task ConsultaOrdenesTest()
        {
            IOrdenesBC OrdenesBC = new OrdenesBC.Implementaciones.OrdenesBC();
            var resultado = await OrdenesBC.ConsultarCarrito("CC79797979");
            Assert.IsTrue(resultado.Any());
        }

        [TestMethod]
        public async Task ConsultaCategoriasTest()
        {
            IOrdenesBC ordenesBC = new OrdenesBC.Implementaciones.OrdenesBC();
            IEnumerable<Categoria> categorias = await ordenesBC.BuscarCategorias(new Common.DTO.Parametros() { page = 0, size = 20 });
            Assert.IsTrue(categorias.Count() == 12);
        }
    }
}
