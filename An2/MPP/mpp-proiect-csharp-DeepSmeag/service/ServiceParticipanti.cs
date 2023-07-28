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
    internal class ServiceParticipanti
    {
        IParticipantRepoInterface participantRepo;
        public ServiceParticipanti(IParticipantRepoInterface repo)
        {
            participantRepo = repo;
        }
        public List<Participant> getAll()
        {
            return participantRepo.getAll();
        }
        public List<Participant> getByProba(int idProba)
        {
            return participantRepo.getParticipantsByProba(idProba);
        }

        public Participant getByName(String participantName)
        {
            return participantRepo.getParticipantByName(participantName);
        }

        public void addParticipant(String participantName, int age)
        {
            Participant participant = new Participant(1, participantName, age);
            participantRepo.saveEntity(participant);
        }
    }
}
