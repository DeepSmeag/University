using mpp_proiect_csharp_DeepSmeag.models.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.models
{
    [Serializable]
    public class Inscriere : Entitate<string>
    {
        public int IdParticipant { get; }
        public int IdProba { get; }
        public Inscriere(int idParticipant, int idProba) : base(idParticipant.ToString() + "" + idProba.ToString())
        {

            this.IdParticipant = idParticipant;
            this.IdProba = idProba;
        }
    }
}
