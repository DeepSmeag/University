using mpp_proiect_csharp_DeepSmeag.models;
using mpp_proiect_csharp_DeepSmeag.persistence;
using mpp_proiect_csharp_DeepSmeag.persistence.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.services
{
    public class ServiceLogin
    {
        IPersoanaOficiuRepoInterface persoanaOficiuRepo;

        public ServiceLogin(IPersoanaOficiuRepoInterface repo)
        {
            persoanaOficiuRepo = repo;
        }

        public bool attemptLogin(String username, String password)
        {
            PersoanaOficiu? persoanaOficiu = persoanaOficiuRepo.getOneByUsername(username);
            if (persoanaOficiu == null)
            {
                return false;
            }
            return persoanaOficiu.password.Equals(password);
        }

    }
}
