
import contests.persistence.*;
import contests.services.IContestsServices;
import service.ServiceFacade;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort=55356;

    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps = new Properties();
        try {
            serverProps.load(StartObjectServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties " + e);
            return;
        }
        InscriereRepo inscriereRepo = new InscriereRepo();
        ParticipantRepo participantRepo = new ParticipantRepo();
        PersoanaOficiuRepo persoanaOficiuRepo = new PersoanaOficiuRepo();
        ProbaRepo probaRepo = new ProbaRepo();
        ServiceFacade.setRepos(persoanaOficiuRepo, participantRepo, probaRepo, inscriereRepo);
//        IContestsServices chatServerImpl = ServiceFacade.getInstance();
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException nef) {
            System.err.println("Wrong  Port Number" + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Starting server on port: " + serverPort);
//        AbstractServer server = new ContestsObjectConcurrentServer(serverPort, chatServerImpl);
//        RpcWorker server = new RpcWorker(serverPort);
//        try {
//            server.start();
//        } catch (ServerException e) {
//            System.err.println("Error starting the server" + e.getMessage());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
