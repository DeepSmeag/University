using mpp_proiect_csharp_DeepSmeag.models;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.networking.objectprotocol
{
    [Serializable]
    public class LogoutRequest : Request
    {
        public PersoanaOficiu user;
        public LogoutRequest(PersoanaOficiu user) { this.user = user; }
        public PersoanaOficiu getUser()
        {
            return user;
        }
    }
    
}
