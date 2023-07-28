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
    public class ServiceInscrieri
    {
        IInscriereRepoInterface inscriereRepo;

        public ServiceInscrieri(IInscriereRepoInterface repo)
        {
            inscriereRepo = repo;
        }

        public List<Inscriere> getByParticipantId(int idParticipant)
        {
            return inscriereRepo.getInscrieriByParticipantId(idParticipant);
        }

        public void addInscriere(int idParticipant, int idProba)
        {
            Inscriere inscriere = new Inscriere(idParticipant, idProba);
            inscriereRepo.saveEntity(inscriere);
        }
    }
}
