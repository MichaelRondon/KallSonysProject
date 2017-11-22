using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mail;
using System.Text;
using System.Threading.Tasks;

namespace Common.Util
{
    public static class MailHelper
    {
        public static async Task SendEmail(string toEmailAddress, string emailSubject, string emailMessage)
        {
            var message = new MailMessage();
            message.To.Add(toEmailAddress);

            message.Subject = emailSubject;
            message.Body = emailMessage;
            message.IsBodyHtml = true;

            using (var smtpClient = new SmtpClient())
            {
                //await smtpClient.SendMailAsync(message);
                Task.Run(() => smtpClient.Send(message));
            }
        }

    }
}
