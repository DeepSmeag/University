
using log4net.Config;
using mpp_proiect_csharp_DeepSmeag.models;
using mpp_proiect_csharp_DeepSmeag.persistence;
using mpp_proiect_csharp_DeepSmeag.persistence.interfaces;
using mpp_proiect_csharp_DeepSmeag.services.interfaces;
using server;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.NetworkInformation;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.services
{
    public class ServiceFacade : IContestsServices
    {
        private static ServiceLogin serviceLogin;
        private static ServiceParticipanti serviceParticipanti;
        private static ServiceMergeReg serviceMergeReg;
        private static ServiceInscrieri serviceInscrieri;

        private static IPersoanaOficiuRepoInterface persoanaOficiuRepoI;
        private static IParticipantRepoInterface participantRepoI;
        private static IProbaRepoInterface probaRepoI;
        private static IInscriereRepoInterface inscriereRepoI;

        private static IDictionary<string, IContestsObserver> loggedUsers;

        //    signleton
        private static ServiceFacade singleton;
        public static void setRepos(IPersoanaOficiuRepoInterface persoanaOficiuRepo, IParticipantRepoInterface participantRepo, IProbaRepoInterface probaRepo, IInscriereRepoInterface inscriereRepo)
        {
            persoanaOficiuRepoI = persoanaOficiuRepo;
            participantRepoI = participantRepo;
            probaRepoI = probaRepo;
            inscriereRepoI = inscriereRepo;
            loggedUsers = new Dictionary<string, IContestsObserver>();
        }
        public static ServiceFacade getInstance()
        {
            if (singleton == null)
            {
                FileInfo configFile = new FileInfo("log4net.config");
                XmlConfigurator.Configure(configFile);
                //            !!!!!!!!!!! Fiecare service va primi ca parametri repo-urile care sunt folosite in cadrul lui
                singleton = new ServiceFacade();
                serviceLogin = new ServiceLogin(persoanaOficiuRepoI);
                serviceParticipanti = new ServiceParticipanti(participantRepoI);
                serviceMergeReg = new ServiceMergeReg(probaRepoI, inscriereRepoI);
                serviceInscrieri = new ServiceInscrieri(inscriereRepoI);
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

        public void login(PersoanaOficiu user, IContestsObserver client)
        {
            //Console.WriteLine("Attempting login");
            LogManager.info("Attempting login");
            if (!attemptLogin(user.username, user.password))
            {
                //LogManager.getLogger().error("Username or password is incorrect");
                Console.WriteLine("Username or password is incorrect");
                throw new ContestsException("Username or password is incorrect");
            }
            if (loggedUsers.ContainsKey(user.username) == true)
            {
                throw new ContestsException("User already logged in");
            }
            loggedUsers.Add(user.username, client);

            //LogManager.getLogger().info("User " + user.getUsername() + " logged in");
        }
        public void logout(PersoanaOficiu user, IContestsObserver client)
        {
            Console.WriteLine(user.username + " " + user.password);
            Console.WriteLine(user.GetHashCode());
            if (!loggedUsers.Remove(user.username))
            {
                throw new ContestsException("User " + user.username + " is not logged in");
            }
            //LogManager.getLogger().info("User " + user.getUsername() + " logged out");

        }
        public List<InscrieriProba> getContests()
        {
            return getInscrieriMerged();
        }
        public List<Participant> getParticipants()
        {
            return getAllParticipants();
        }
        public List<Participant> getParticipants(int idProba)
        {
            return getByProba(idProba);
        }


        public void attemptRegisterParticipant(String participantName, int age, InscrieriProba selectedItem)
        {

            Participant participant = getByName(participantName);

            List<Inscriere> inscrieri = new List<Inscriere>();
            int numInscrieri = 0;
            bool needsRegistering = false;
            int criteriuVarsta;
            Console.WriteLine("Got here: participant is " + participant);
            
            if (participant == null)
            {
                Console.WriteLine("Got here: participant is null so we will be registering");
                needsRegistering = true;
                criteriuVarsta = age;
            }
            else
            {
                criteriuVarsta = participant.Varsta;
                inscrieri = getByParticipantId(participant.Id);
                numInscrieri += inscrieri.Count;
            }

            if (numInscrieri > 1)
            {

                throw new ContestsException("Participantul " + participantName + " are deja 2 inscrieri");
            }

            Console.WriteLine("Got here: no more than 2 registers will be on");


            Console.WriteLine("Got here: one selected Item");
            bool isClashing = false;
            if (inscrieri.Count > 0)
            {
                foreach (Inscriere inscriere in inscrieri)
                {
                    if (selectedItem.Id == inscriere.IdProba)
                    {
                        isClashing = true;
                    }
                }
            }
            string[] criterii = selectedItem.categorieVarsta.Trim().Split('-');
            if (criteriuVarsta < int.Parse(criterii[0]) || criteriuVarsta > int.Parse(criterii[1]))
            {
                isClashing = true;
                Console.WriteLine("Got here: age group clashes");
            }
            if (!isClashing)
            {
                if (needsRegistering)
                {
                    Console.WriteLine("Registering participant");
                    addParticipant(participantName, age);
                    participant = getByName(participantName);
                }
                //add inscriere for participantID at probaID
                addInscriere(participant.Id, selectedItem.Id);

                updateUsers();
            }
            else
            {
                throw new ContestsException("Participantul " + participantName + "nu se incadreaza in categoria de varsta a probei " + selectedItem.numeProba);
            }

        }

        private void updateUsers()
        {

            foreach (IContestsObserver client in loggedUsers.Values)
            {
                Task.Run(() => client.updateParticipantsModel());
                Task.Run(() => client.updateContestsModel());
            }
        }

    }
}
