using mpp_proiect_csharp_DeepSmeag.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.repo.interfaces
{
    internal interface ICategorieVarstaRepoInterface : IRepoInterface<CategorieVarsta, int>
    {
        CategorieVarsta? getCategorieVarstaByAgeGroup(int minAge, int maxAge);
    }
}
