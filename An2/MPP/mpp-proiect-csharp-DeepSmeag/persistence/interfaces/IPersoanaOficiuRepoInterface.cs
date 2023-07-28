using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using mpp_proiect_csharp_DeepSmeag.models;

namespace mpp_proiect_csharp_DeepSmeag.persistence.interfaces
{
    public interface IPersoanaOficiuRepoInterface : IRepoInterface<PersoanaOficiu, int>
    {
        PersoanaOficiu? getOneByUsername(string username);
    }
}
