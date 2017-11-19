namespace OrdenesEntities.Models
{
    public class DatosColaFacturacion
    {
        public int orderNumber { get; set; } // ":50000,
        public int orderFinishedYear { get; set; } // ": 2017,
        public int orderFinishedMonth { get; set; } // ": 11,
        public int orderFinishedDay { get; set; } // ": 18,
        public string orderManufacter { get; set; } // ": "SONY",
        public double orderTotalValue { get; set; } // ": 250000
    }
}
