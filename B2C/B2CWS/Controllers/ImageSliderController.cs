using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Web.Http;
using B2CWS.Util;

namespace B2CWS.Controllers
{
    public class ImageSliderController : ApiController
    {
        public HttpResponseMessage Get(string id)
        {
            HttpResponseMessage result = new HttpResponseMessage(); //HttpStatusCode.OK

            try
            {
                result.StatusCode = HttpStatusCode.OK;
                result.Content = new ByteArrayContent(ImageDownloader.GetImage(id, ImageType.Slider));
                result.Content.Headers.ContentType = new MediaTypeHeaderValue("image/jpeg");
                return result;
            }
            catch (Exception)
            {
                result.StatusCode = HttpStatusCode.NotFound;
                return result;
            }
        }
    }
}
