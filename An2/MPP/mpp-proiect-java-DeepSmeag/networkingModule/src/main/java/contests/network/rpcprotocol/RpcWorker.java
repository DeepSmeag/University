package contests.network.rpcprotocol;

import contests.model.InscrieriProba;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import service.ServiceFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;

public class RpcWorker {
    private final int port;
    private final Server server;
    private static final ServiceFacade serverImpl = ServiceFacade.getInstance();

    public RpcWorker(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .addService(new RpcWorkerServiceGrpcImpl())
                .build();

    }
    public static void main(String[] args) throws IOException, InterruptedException {
        RpcWorker worker = new RpcWorker(50051);
        worker.start();
        worker.blockUntilShutdown();

//        System.out.println(serverImpl.attemptLogin("username2", "password2"));


    }
    public void start() throws IOException {

        server.start();
        System.out.println("Server started on port " + port);
    }
    public void stop() {
        server.shutdown();
    }
    public void blockUntilShutdown() throws InterruptedException {
        server.awaitTermination();
    }

    static class RpcWorkerServiceGrpcImpl extends RpcWorkerServiceGrpc.RpcWorkerServiceImplBase {
        private static Map<String, StreamObserver> loggedUsers;
        @Override
        public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
            System.out.println("login");
            if(loggedUsers == null) {
                loggedUsers = new HashMap<>();
            }
            boolean success = serverImpl.attemptLogin(request.getUsername(), request.getPassword());
            if(success) {


                loggedUsers.put(request.getUsername(), responseObserver);
                LoginResponse response = LoginResponse.newBuilder().setResponse("ok").build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }

            else {
                LoginResponse response = LoginResponse.newBuilder().setResponse("nok").build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        }
        @Override
        public void logout(LogoutRequest request, StreamObserver<LogoutResponse> responseObserver) {
            System.out.println("logout");
            var client = loggedUsers.remove(request.getUsername());
            if(client == null) {
                LogoutResponse response = LogoutResponse.newBuilder().setResponse("nok").build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            client.onCompleted();
            LogoutResponse response = LogoutResponse.newBuilder().setResponse("ok").build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        @Override
        public void getParticipants(GetParticipantsRequest request, StreamObserver<GetParticipantsResponse> responseObserver) {
            System.out.println("getParticipants");
            List<contests.model.Participant> participants= serverImpl.getParticipants();
            List<Participant> rpcParticipants = participants.stream().map(p -> Participant.newBuilder().setId(p.getId()).setName(p.getNume()).setAge(p.getVarsta()).build()).toList();
            GetParticipantsResponse response = GetParticipantsResponse.newBuilder().addAllParticipants(rpcParticipants).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        @Override
        public void getContests(GetContestsRequest request, StreamObserver<GetContestsResponse> responseObserver) {
            System.out.println("getContests");
            List<InscrieriProba> contests = serverImpl.getContests();
            List<Contest> rpcContests = contests.stream().map(c -> Contest.newBuilder().setId(c.getId()).setCategorieVarsta(c.getCategorieVarsta()).setNumeProba(c.getNumeProba()).setNrParticipanti(c.getNrParticipanti()).build()).toList();
            GetContestsResponse response = GetContestsResponse.newBuilder().addAllContests(rpcContests).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        @Override
        public void getParticipantsByProba(GetParticipantsByProbaRequest request, StreamObserver<GetParticipantsResponse> responseObserver) {
            System.out.println("GetParticipantsByProba");
            List<contests.model.Participant> participants= serverImpl.getParticipants(request.getProbaId());
            List<Participant> rpcParticipants = participants.stream().map(p -> Participant.newBuilder().setId(p.getId()).setName(p.getNume()).setAge(p.getVarsta()).build()).toList();
            GetParticipantsResponse response = GetParticipantsResponse.newBuilder().addAllParticipants(rpcParticipants).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        @Override
        public void attemptRegisterParticipant(RegisterParticipantRequest request, StreamObserver<Response> responseObserver) {
            System.out.println("RegisterParticipant");
            Contest c = request.getContest();
            InscrieriProba inscriere = new InscrieriProba(c.getId(), c.getNrParticipanti(), c.getNumeProba(), c.getCategorieVarsta());
            List<InscrieriProba> inscrieri = new ArrayList<>();
            inscrieri.add(inscriere);
            try {
                serverImpl.attemptRegisterParticipant(request.getName(), request.getAge(), inscrieri);
                Response response = Response.newBuilder().setOkResponse(OkResponse.newBuilder().getDefaultInstanceForType()).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();

                updateClients();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                Response response = Response.newBuilder().setErrorResponse(ErrorResponse.newBuilder().setMessage(e.getMessage()).build()).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        }
        @Override
        public void listenForUpdates(LoginRequest request, StreamObserver<UpdateResponse> responseObserver) {
            System.out.println("listenForUpdates");
            var client = loggedUsers.get(request.getUsername());
            if(client == null) {
                return;
            }
            loggedUsers.put(request.getUsername(), responseObserver);
            updateClients();
        }
        private void updateClients() {
            for (var client : loggedUsers.entrySet()) {
                var responseObserver = client.getValue();
                List<InscrieriProba> contests = serverImpl.getContests();
                List<Contest> rpcContests = contests.stream().map(c -> Contest.newBuilder().setId(c.getId()).setCategorieVarsta(c.getCategorieVarsta()).setNumeProba(c.getNumeProba()).setNrParticipanti(c.getNrParticipanti()).build()).toList();
                GetContestsResponse response = GetContestsResponse.newBuilder().addAllContests(rpcContests).build();
                responseObserver.onNext(response);
//                responseObserver.onCompleted();
            }
        }
    }
}
