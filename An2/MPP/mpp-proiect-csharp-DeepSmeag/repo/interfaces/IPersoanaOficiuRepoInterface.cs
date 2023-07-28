using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mpp_proiect_csharp_DeepSmeag.domain;

namespace mpp_proiect_csharp_DeepSmeag.repo.interfaces
{
    internal interface IPersoanaOficiuRepoInterface : IRepoInterface<PersoanaOficiu, int>
    {
        PersoanaOficiu? getOneByUsername(string username);
    }
}
