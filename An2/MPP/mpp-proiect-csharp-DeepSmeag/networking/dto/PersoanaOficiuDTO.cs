using mpp_proiect_csharp_DeepSmeag.models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace networking.dto
{
    [Serializable]
    public class PersoanaOficiuDTO
    {
        PersoanaOficiu persoanaOficiu;
        public PersoanaOficiuDTO()
        {
        }
        public PersoanaOficiuDTO(PersoanaOficiu persoanaOficiu)
        {
            this.persoanaOficiu = persoanaOficiu;
        }

        public PersoanaOficiu getPersoanaOficiu()
        {
            return persoanaOficiu;
        }
    }
}
