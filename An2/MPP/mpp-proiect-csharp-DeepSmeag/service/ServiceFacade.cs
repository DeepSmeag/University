using mpp_proiect_csharp_DeepSmeag.domain;
using mpp_proiect_csharp_DeepSmeag.repo;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.service
{
    internal class ServiceFacade
    {
        private static ServiceLogin serviceLogin;
        private static ServiceParticipanti serviceParticipanti;
        private static ServiceMergeReg serviceMergeReg;
        private static ServiceInscrieri serviceInscrieri;

        //    signleton
        private static ServiceFacade singleton;
        public static ServiceFacade getInstance()
        {
            if (singleton == null)
            {
                //            !!!!!!!!!!! Fiecare service va primi ca parametri repo-urile care sunt folosite in cadrul lui
                singleton = new ServiceFacade();
                serviceLogin = new ServiceLogin(new PersoanaOficiuRepo());
                serviceParticipanti = new ServiceParticipanti(new ParticipantRepo());
                serviceMergeReg = new ServiceMergeReg(new ProbaRepo(), new InscriereRepo());
                serviceInscrieri = new ServiceInscrieri(new InscriereRepo());
            }
            return singleton;
        }
        ServiceFacade()
        {
        }

        public ServiceLogin getServiceLogin()
        {
            return serviceLogin;
        }

        public ServiceParticipanti getServiceParticipanti()
        {
            return serviceParticipanti;
        }

        public ServiceMergeReg getServiceMergeReg()
        {
            return serviceMergeReg;
        }


        //    !!!!!! VOR aparea toate functiile posibile care pot fi apelate, toata lumea va avea acelasi service si va apela prin aceasta
        //    fatada. De acolo se ramifica unde trebuie executata functia
        public bool attemptLogin(String user, String pass)
        {
            return serviceLogin.attemptLogin(user, pass);
        }

        public List<InscrieriProba> getInscrieriMerged()
        {
            return serviceMergeReg.getInscrieriMerged();
        }

        public void addParticipant(String participantName, int age)
        {
            serviceParticipanti.addParticipant(participantName, age);
        }

        public Participant getByName(String participantName)
        {
            return serviceParticipanti.getByName(participantName);
        }


        public List<Participant> getAllParticipants()
        {
            return serviceParticipanti.getAll();
        }

        public List<Participant> getByProba(int id)
        {
            return serviceParticipanti.getByProba(id);
        }

        public List<Inscriere> getByParticipantId(int id)
        {
            return serviceInscrieri.getByParticipantId(id);
        }

        public void addInscriere(int id, int id1)
        {
            serviceInscrieri.addInscriere(id, id1);
        }
    }
}
