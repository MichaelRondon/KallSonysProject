using ClientesEntities.Models;

namespace OrdenesEntities.Models
{
    public class Orden
    {
        public Cliente cliente { get; set; }
        public double valorTotal { get; set; }
        public int itemsTotal { get; set; }
        public int idOrden { get; set; }
        public string fecha_orden { get; set; }
        public string estado { get; set; }
        public string carrier { get; set; }
        public string fabricante { get; set; }
    }
}
