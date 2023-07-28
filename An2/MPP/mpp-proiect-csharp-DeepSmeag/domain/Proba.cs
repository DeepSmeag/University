using mpp_proiect_csharp_DeepSmeag.domain.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.domain
{
    internal class Proba : Entitate<int>
    {
        public NumeProba numeProba;
        public CategorieVarsta categorieVarsta;
        public Proba(int id, NumeProba numeProba, CategorieVarsta categorieVarsta) : base(id)
        {
            this.numeProba = numeProba;
            this.categorieVarsta = categorieVarsta;
        }
    }
}
