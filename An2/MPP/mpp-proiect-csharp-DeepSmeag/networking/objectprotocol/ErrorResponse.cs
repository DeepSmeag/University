
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.networking.objectprotocol
{
    [Serializable]
    public class ErrorResponse : Response
    {
        public string message;
        public ErrorResponse(string message)
        {
            this.message = message;
        }
        public string getMessage() { return message; }
    }
}
