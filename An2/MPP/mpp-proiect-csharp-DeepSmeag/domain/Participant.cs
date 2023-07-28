using mpp_proiect_csharp_DeepSmeag.domain.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.domain
{
    internal class Participant : Entitate<int>
    {
        public string Nume { get; }
        public int Varsta { get; }
        public Participant(int id, string nume, int varsta) : base(id)
        {
            this.Nume = nume;
            this.Varsta = varsta;
        }
    }
}
