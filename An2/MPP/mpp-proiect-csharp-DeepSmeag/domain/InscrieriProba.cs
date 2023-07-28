using mpp_proiect_csharp_DeepSmeag.domain.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.domain
{
    internal class InscrieriProba: Entitate<int>
    {
        public int nrParticipanti { get; }
        public string numeProba { get; }
        public string categorieVarsta { get; }

        public InscrieriProba(int id, int nrParticipanti, string numeProba, string categorieVarsta) : base(id)
        {
            this.nrParticipanti = nrParticipanti;
            this.numeProba = numeProba;
            this.categorieVarsta = categorieVarsta;
        }

        public String toString()
        {
            return "InscrieriProba{" +
                    "nrParticipanti=" + nrParticipanti +
                    ", numeProba='" + numeProba + '\'' +
                    ", categorieVarsta='" + categorieVarsta + '\'' +
                    '}';
        }

    }
}
