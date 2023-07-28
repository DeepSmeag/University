using mpp_proiect_csharp_DeepSmeag.models.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.models
{
    [Serializable]
    public class NumeProba : Entitate<int>
    {
        public string numeProba { get; set; }
        public NumeProba() : base() { }
        public NumeProba(int id, String numeProba) : base(id)
        {
            this.numeProba = numeProba;
        }
        override
        public string ToString()
        {
            return numeProba;
        }
    }
}
