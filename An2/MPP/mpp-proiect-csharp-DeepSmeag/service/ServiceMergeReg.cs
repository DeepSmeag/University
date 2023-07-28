using mpp_proiect_csharp_DeepSmeag.domain;
using mpp_proiect_csharp_DeepSmeag.repo;
using mpp_proiect_csharp_DeepSmeag.repo.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.service
{
    internal class ServiceMergeReg
    {
        IProbaRepoInterface probaRepo;
        IInscriereRepoInterface inscriereRepo;

        public ServiceMergeReg(IProbaRepoInterface repo1, IInscriereRepoInterface repo2)
        {
            probaRepo = repo1;
            inscriereRepo = repo2;
        }

        public List<InscrieriProba> getInscrieriMerged()
        {
            List<InscrieriProba> inscrieriProbaList = new List<InscrieriProba>();

            //probaRepo.getAll().ToArray()
            foreach (Proba proba in probaRepo.getAll())
            {
                int numRegistrations = inscriereRepo.getNumRegistrations(proba.Id);
                InscrieriProba inscrieriProba = new InscrieriProba(proba.Id, numRegistrations, proba.numeProba.ToString(), proba.categorieVarsta.ToString());
                inscrieriProbaList.Add(inscrieriProba);
                Console.WriteLine(proba.numeProba + " " + proba.categorieVarsta + " " + numRegistrations);
            }

            return inscrieriProbaList;
        }

    }
}
