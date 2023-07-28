using mpp_proiect_csharp_DeepSmeag.domain.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.domain
{
    internal class CategorieVarsta : Entitate<int>
    {
        public int VarstaMinima { get; }
        public int VarstaMaxima { get; }

        public CategorieVarsta(int id, int varstaMinima, int varstaMaxima) : base(id)
        {
            this.VarstaMinima = varstaMinima;
            this.VarstaMaxima = varstaMaxima;
        }
        public string ToString()
        {
            return string.Format("{0:d}-{1:d}",VarstaMinima,VarstaMaxima);
        }
    }
}
