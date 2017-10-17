using System.Configuration;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;

public class UploadController : ApiController
{
    public async Task<HttpResponseMessage> PostFile()
    {
        // Check if the request contains multipart/form-data.
        if (!Request.Content.IsMimeMultipartContent())
        {
            System.Diagnostics.Trace.WriteLine("Lanzando excepcion: UnsupportedMediaType");
            throw new HttpResponseException(HttpStatusCode.UnsupportedMediaType);
        }

        //string root = HttpContext.Current.Server.MapPath("~/App_Data");
        string root = ConfigurationManager.AppSettings["RutaArchivos"];
        string fileid = null;
        int intFileId;
        long fileLength;
        string currentFileName = null, newFileName = null, currentFileFullName = null;
        var provider = new MultipartFormDataStreamProvider(root);

        try
        {
            StringBuilder sb = new StringBuilder(); // Holds the response body

            // Read the form data and return an async task.
            await Request.Content.ReadAsMultipartAsync(provider);

            // This illustrates how to get the form data.
            foreach (var key in provider.FormData.AllKeys)
            {
                foreach (var val in provider.FormData.GetValues(key))
                {
                    //sb.Append(string.Format("{0}: {1}\n", key, val));
                    if (key.Equals("id"))
                    {
                        fileid = val;
                        break;
                    }
                }
            }

            if (!int.TryParse(fileid, out intFileId))
            {
                throw new System.Exception("Error al obtener ID de imagen");
            }

            currentFileName = string.Format("{0}.jpg", fileid);
            
            // This illustrates how to get the file names for uploaded files.
            foreach (var file in provider.FileData)
            {
                FileInfo fileInfo = new FileInfo(file.LocalFileName);
                newFileName = fileInfo.FullName;
                fileLength = fileInfo.Length;

                // Nombre esperado del archivo a recibir
                currentFileFullName = string.Format("{0}\\{1}", fileInfo.DirectoryName, currentFileName);

                // Validar si el archivo existe
                if (File.Exists(currentFileFullName))
                {
                    File.Delete(currentFileFullName);
                }

                // Renombrar
                File.Move(newFileName, currentFileFullName);

                sb.Append(string.Format("Uploaded file: {0} ({1} bytes)\n", currentFileName, fileLength));
            }
            return new HttpResponseMessage()
            {
                Content = new StringContent(sb.ToString())
            };
        }
        catch (System.Exception e)
        {
            System.Diagnostics.Trace.WriteLine(e);
            return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, e);
        }
    }

}