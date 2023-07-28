using mpp_proiect_csharp_DeepSmeag.models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.networking.objectprotocol
{
    [Serializable]
    public class GetParticipantsByProbaRequest : Request
    {
        public int idProba;
        public GetParticipantsByProbaRequest()
        {
        }
        public GetParticipantsByProbaRequest(int idProba)
        {
            this.idProba = idProba;
        }
        public int getIdProba()
        {
            return idProba;
        }
    }

}
