using mpp_proiect_csharp_DeepSmeag.models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace networking.dto
{
    [Serializable]
    public class ListInscrieriProbaDTO
    {
        private List<InscrieriProba> inscrieriProbe;

        public ListInscrieriProbaDTO(List<InscrieriProba> inscrieriProbe)
        {
            this.inscrieriProbe = inscrieriProbe;
        }

        public List<InscrieriProba> getInscrieriProbe()
        {
            return inscrieriProbe;
        }
    }
}
