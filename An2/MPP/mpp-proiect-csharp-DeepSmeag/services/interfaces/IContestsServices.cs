using mpp_proiect_csharp_DeepSmeag.models;
using mpp_proiect_csharp_DeepSmeag.services.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.services.interfaces
{
    public interface IContestsServices
    {
        void login(PersoanaOficiu user, IContestsObserver client);

        void logout(PersoanaOficiu user, IContestsObserver client) ;

        List<InscrieriProba> getContests();
        List<Participant> getParticipants();
        List<Participant> getParticipants(int idProba) ;

        void attemptRegisterParticipant(String participantName, int age, InscrieriProba selectedItem);
    }
}
