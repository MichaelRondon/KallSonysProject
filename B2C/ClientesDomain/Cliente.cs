using System.Text;
using System.Collections.Generic;

namespace ClientesDomain
{
    public class Cliente : IEntidad
    {
        public string documento { get; set; }
        public string nombres { get; set; }
        public string apellidos { get; set; }
        public string telefono { get; set; }
        public string correo_e { get; set; }
        public string password { get; set; }
        public string estatus { get; set; }
        public string tipo { get; set; }
        public string numero { get; set; }
        public IEnumerable<Direccion> direcciones { get; set; }

        public string Validar()
        {
            StringBuilder sbErrores = new StringBuilder();

            // Validar campos obligatorios
            if (string.IsNullOrEmpty(documento))
            {
                sbErrores.Append("Documento es requerido");
            }
            if (string.IsNullOrEmpty(nombres))
            {
                sbErrores.AppendFormat("{0}{1}", sbErrores.Length == 0 ? string.Empty : ";", "Nombres es requerido");
            }
            if (string.IsNullOrEmpty(apellidos))
            {
                sbErrores.AppendFormat("{0}{1}", sbErrores.Length == 0 ? string.Empty : ";", "Apellidos es requerido");
            }
            if (string.IsNullOrEmpty(correo_e))
            {
                sbErrores.AppendFormat("{0}{1}", sbErrores.Length == 0 ? string.Empty : ";", "Correo electrónico es requerido");
            }
            //if (string.IsNullOrEmpty(password))
            //{
            //    sbErrores.AppendFormat("{0}{1}", sbErrores.Length == 0 ? string.Empty : ";", "Password es requerido");
            //}
            return sbErrores.ToString();
        }
    }
}
