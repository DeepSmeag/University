using mpp_proiect_csharp_DeepSmeag.domain.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.domain
{
    internal class NumeProba : Entitate<int>
    {
        public string Nume { get; }
        public NumeProba(int id, String numeProba) : base(id)
        {
            this.Nume = numeProba;
        }
        public string ToString()
        {
            return Nume;
        }
    }
}
