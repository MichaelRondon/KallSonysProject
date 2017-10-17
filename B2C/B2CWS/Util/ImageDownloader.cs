using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using B2CWS.Util;
using System.IO;
using System.Configuration;

namespace B2CWS.Util
{
    public class ImageDownloader
    {
        public static Byte[] GetImage(string ID, ImageType type)
        {
            string filename = string.Empty;
            string basePath = string.Empty;

            switch (type)
            {
                case ImageType.Product:
                    basePath = ConfigurationManager.AppSettings["RutaArchivos"];
                    break;
                case ImageType.Campania:
                    basePath = ConfigurationManager.AppSettings["RutaImgCampanias"];
                    break;
                case ImageType.Slider:
                    basePath = ConfigurationManager.AppSettings["RutaImgSlider"];
                    break;
                default:
                    break;
            }

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

        public static IEnumerable<string> GetSliderImages()
        {
            List<string> slider = new List<string>();
            FileInfo info = null;

            try
            {
                var imgsDir = Directory.GetFiles(ConfigurationManager.AppSettings["RutaImgSlider"]);
                foreach (var item in imgsDir)
                {
                    info = new FileInfo(item);
                    slider.Add(info.Name.Substring(0, info.Name.LastIndexOf(info.Extension)));
                }
            }
            catch (Exception)
            {

                slider = null;
            }

            return slider;
        }
    }
}