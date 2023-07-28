using mpp_proiect_csharp_DeepSmeag.models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.networking.objectprotocol
{
    [Serializable]
    public class GetContestsResponse : Response
    {
        private List<InscrieriProba> contests;

        public GetContestsResponse(List<InscrieriProba> contests)
        {
            this.contests = contests;
        }

        public List<InscrieriProba> getContests()
        {
            return contests;
        }
    }

}
