using mpp_proiect_csharp_DeepSmeag.models.entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.models
{
    [Serializable]
    public class Proba : Entitate<int>
    {
        public NumeProba numeProba { get; set; }
        public CategorieVarsta categorieVarsta { get; set; }
        public Proba():base() { }
        public Proba(int id, NumeProba numeProba, CategorieVarsta categorieVarsta) : base(id)
        {
            this.numeProba = numeProba;
            this.categorieVarsta = categorieVarsta;
        }
    }
}
