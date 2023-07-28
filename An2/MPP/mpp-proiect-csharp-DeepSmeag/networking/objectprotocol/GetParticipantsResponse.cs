using mpp_proiect_csharp_DeepSmeag.models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.networking.objectprotocol
{
    [Serializable]
    public class GetParticipantsResponse : Response
    {
        private List<Participant> participants;

        public GetParticipantsResponse(List<Participant> participants)
        {
            this.participants = participants;
        }

        public List<Participant> getParticipants()
        {
            return participants;
        }
    }
}
