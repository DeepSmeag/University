package service;

import contests.model.Inscriere;
import contests.model.InscrieriProba;
import contests.model.Participant;
import contests.model.PersoanaOficiu;

import contests.persistence.InscriereRepo;
import contests.persistence.ParticipantRepo;
import contests.persistence.PersoanaOficiuRepo;
import contests.persistence.ProbaRepo;
import contests.persistence.interfaces.ParticipantRepoInterface;
import contests.services.ContestsException;
import contests.services.IContestsObserver;
import contests.services.IContestsServices;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceFacade {
    private static ServiceLogin serviceLogin;
    private static ServiceParticipanti serviceParticipanti;
    private static ServiceMergeReg serviceMergeReg;
    private static ServiceInscrieri serviceInscrieri;


    //    signleton
    private static ServiceFacade singleton;

    public static void setRepos(PersoanaOficiuRepo persoanaOficiuRepo, ParticipantRepo participantRepo, ProbaRepo probaRepo, InscriereRepo inscriereRepo) {
        serviceLogin = new ServiceLogin(persoanaOficiuRepo);
        serviceParticipanti = new ServiceParticipanti(participantRepo);
        serviceMergeReg = new ServiceMergeReg(probaRepo, inscriereRepo);
        serviceInscrieri = new ServiceInscrieri(inscriereRepo);
//        loggedUsers = new ConcurrentHashMap<>();
    }

    public static ServiceFacade getInstance() {
        if (singleton == null) {
//            !!!!!!!!!!! Fiecare service va primi ca parametri repo-urile care sunt folosite in cadrul lui
            singleton = new ServiceFacade();
            serviceLogin = new ServiceLogin(new PersoanaOficiuRepo());
            serviceParticipanti = new ServiceParticipanti(new ParticipantRepo());
            serviceMergeReg = new ServiceMergeReg(new ProbaRepo(), new InscriereRepo());
            serviceInscrieri = new ServiceInscrieri(new InscriereRepo());
        }
        return singleton;
    }

    ServiceFacade() {
    }

    public ServiceLogin getServiceLogin() {
        return serviceLogin;
    }

    public ServiceParticipanti getServiceParticipanti() {
        return serviceParticipanti;
    }

    public ServiceMergeReg getServiceMergeReg() {
        return serviceMergeReg;
    }


    //    !!!!!! VOR aparea toate functiile posibile care pot fi apelate, toata lumea va avea acelasi service si va apela prin aceasta
//    fatada. De acolo se ramifica unde trebuie executata functia
    public boolean attemptLogin(String user, String pass) {
        return serviceLogin.attemptLogin(user, pass);
    }

    private List<InscrieriProba> getInscrieriMerged() {
        return serviceMergeReg.getInscrieriMerged();
    }

    public void addParticipant(String participantName, int age) {
        serviceParticipanti.addParticipant(participantName, age);
    }

    public Participant getByName(String participantName) {
        return serviceParticipanti.getByName(participantName);
    }


    private List<Participant> getAllParticipants() {
        return serviceParticipanti.getAll();
    }

    private List<Participant> getByProba(Integer id) {
        return serviceParticipanti.getByProba(id);
    }

    public List<Inscriere> getByParticipantId(Integer id) {
        return serviceInscrieri.getByParticipantId(id);
    }

    public void addInscriere(Integer id, Integer id1) {
        serviceInscrieri.addInscriere(id, id1);
    }


    public List<InscrieriProba> getContests() {
        return getInscrieriMerged();
    }


    public List<Participant> getParticipants() {
        return getAllParticipants();
    }


    public List<Participant> getParticipants(int idProba) {
        return getByProba(idProba);
    }


    public void attemptRegisterParticipant(String participantName, int age, List<InscrieriProba> selectedItems) throws Exception {
        Participant participant = getByName(participantName);
//        if (participant == null) {
//            addParticipant(participantName, age);
//            participant = getByName(participantName);
//        }

//        Check if already registered for >=2 contests
        List<Inscriere> inscrieri = null;
        int numInscrieri = 0;
        int criteriuVarsta;
        boolean needsRegistering = false;

        if (participant == null) {
            needsRegistering = true;
            criteriuVarsta = age;
            inscrieri = new ArrayList<>();
        } else {
            criteriuVarsta = participant.getVarsta();
            inscrieri = getByParticipantId(participant.getId());
            numInscrieri += inscrieri.size();
        }

        if (numInscrieri > 1) {

            throw new Exception("Participantul " + participantName + " este deja inscris la destule probe");
        }

//        If fine up to this point, can register to 1-2 contests
//        Also register that participant
//        First to register the person if not existant

//        Register to the selected contests, if they're not already registered there; also validate age group
//        First get the contests
        var selectedContests = selectedItems;
        for (InscrieriProba selected :
                selectedContests) {
            boolean isClashing = false;
            for (Inscriere inscriere :
                    inscrieri) {
                if (selected.getId() == inscriere.getIdProba()) {
                    isClashing = true;
                }
            }
//                split categorie varsta dupa -, get min and max
            String[] criterii = selected.getCategorieVarsta().split("-");
            if (criteriuVarsta < Integer.parseInt(criterii[0]) ||
                    criteriuVarsta > Integer.parseInt(criterii[1])) {
                isClashing = true;
            }
            if (!isClashing) {
//                then I want to add this registration
                if (needsRegistering) {
                    addParticipant(participantName, age);
                    participant = getByName(participantName);
                }
                    addInscriere(participant.getId(), selected.getId());
//                updateUsers();
            } else {
                throw new Exception("Participantul " + participantName + " nu se incadreaza in categoria de varsta a probei " + selected.getNumeProba());
            }
        }
    }
//    private static int defaultThreadsNo=5;
//    private void updateUsers() {
//        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
//        for (Map.Entry<PersoanaOficiu, IContestsObserver> entry :
//                loggedUsers.entrySet()) {
//            entry.getValue().updateParticipantsModel();
//            entry.getValue().updateContestsModel();
//        }
//        executor.shutdown();
//    }
}
