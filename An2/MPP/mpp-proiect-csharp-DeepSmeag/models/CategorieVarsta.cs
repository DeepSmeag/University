using mpp_proiect_csharp_DeepSmeag.models.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.models
{
    [Serializable]
    public class CategorieVarsta : Entitate<int>
    {
        public int varstaMinima { get; set; }
        public int varstaMaxima { get; set; }
        public CategorieVarsta() : base() { }
        public CategorieVarsta(int id, int varstaMinima, int varstaMaxima) : base(id)
        {
            this.varstaMinima = varstaMinima;
            this.varstaMaxima = varstaMaxima;
        }
        override
        public string ToString()
        {
            return string.Format("{0:d}-{1:d}",varstaMinima,varstaMaxima);
        }
    }
}
