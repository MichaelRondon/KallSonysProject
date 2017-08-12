using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ImagenesWS.Util;
using System.IO;
using System.Configuration;

namespace ImagenesWS.Util
{
    public class ImageDownloader
    {
        public static Byte[] GetImage(long ID, ImageSizeEnum size)
        {
            string filename = string.Empty;
            string basePath = ConfigurationManager.AppSettings["RutaArchivos"];
            string noFoundFile = ConfigurationManager.AppSettings["ImgNotFound"];
            string noFound = string.Format(@"{0}\{1}", basePath, noFoundFile);

            try
            {
                var archivos = Directory.EnumerateFiles(basePath, string.Format("{0}.*", ID));
                if (archivos.Count() != 1)
                {
                    filename = noFound;
                }
                else
                {
                    filename = archivos.First();
                }
            }
            catch (Exception)
            {

                throw;
            }

            Byte[] b = System.IO.File.ReadAllBytes(filename);
            return b;
        }
    }
}