package com.example.helloworld;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class HelloWorldClient {
    private final ManagedChannel channel;
    private final HelloWorldServiceGrpc.HelloWorldServiceBlockingStub blockingStub;

    public HelloWorldClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = HelloWorldServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() {
        channel.shutdown();
    }

    public String sayHello(String message) {
        HelloRequest request = HelloRequest.newBuilder().setMessage(message).build();
        HelloResponse response = blockingStub.sayHello(request);
        return response.getMessage();
    }

    public static void main(String[] args) {
        HelloWorldClient client = new HelloWorldClient("localhost", 50051);
        String response = client.sayHello("World");
        System.out.println("Received response: " + response);
        client.shutdown();
    }
}
