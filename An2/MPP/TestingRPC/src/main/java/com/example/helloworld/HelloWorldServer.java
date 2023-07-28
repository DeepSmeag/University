package com.example.helloworld;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class HelloWorldServer {
    private final int port;
    private final Server server;

    public HelloWorldServer(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .addService(new HelloWorldServiceImpl())
                .build();
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

    public static void main(String[] args) throws IOException, InterruptedException {
        HelloWorldServer server = new HelloWorldServer(50051);
        server.start();
        server.blockUntilShutdown();
    }

    static class HelloWorldServiceImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {
        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
            String message = "Hello, " + request.getMessage();
            System.out.println(message);
            HelloResponse response = HelloResponse.newBuilder().setMessage(message).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        @Override
        public void getUsers(GetUsersRequest request, StreamObserver<GetUsersResponse> responseObserver) {
            System.out.println("getUsers");
            User user1 = User.newBuilder().setName("user1").setPassword("user1").build();
            User user2 = User.newBuilder().setName("user2").setPassword("user2").build();
            GetUsersResponse response = GetUsersResponse.newBuilder().addUsers(user1).addUsers(user2).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
