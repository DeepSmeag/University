﻿using mpp_proiect_csharp_DeepSmeag.models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.persistence.interfaces
{
    public interface ICategorieVarstaRepoInterface : IRepoInterface<CategorieVarsta, int>
    {
        CategorieVarsta? getCategorieVarstaByAgeGroup(int minAge, int maxAge);
    }
}
