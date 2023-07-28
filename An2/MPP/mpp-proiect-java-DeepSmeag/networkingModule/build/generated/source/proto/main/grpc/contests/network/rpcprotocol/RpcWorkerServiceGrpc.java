package contests.network.rpcprotocol;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.54.1)",
    comments = "Source: rpcWorker.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class RpcWorkerServiceGrpc {

  private RpcWorkerServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcprotocol.RpcWorkerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<contests.network.rpcprotocol.LoginRequest,
      contests.network.rpcprotocol.LoginResponse> getLoginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Login",
      requestType = contests.network.rpcprotocol.LoginRequest.class,
      responseType = contests.network.rpcprotocol.LoginResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<contests.network.rpcprotocol.LoginRequest,
      contests.network.rpcprotocol.LoginResponse> getLoginMethod() {
    io.grpc.MethodDescriptor<contests.network.rpcprotocol.LoginRequest, contests.network.rpcprotocol.LoginResponse> getLoginMethod;
    if ((getLoginMethod = RpcWorkerServiceGrpc.getLoginMethod) == null) {
      synchronized (RpcWorkerServiceGrpc.class) {
        if ((getLoginMethod = RpcWorkerServiceGrpc.getLoginMethod) == null) {
          RpcWorkerServiceGrpc.getLoginMethod = getLoginMethod =
              io.grpc.MethodDescriptor.<contests.network.rpcprotocol.LoginRequest, contests.network.rpcprotocol.LoginResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Login"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.LoginRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.LoginResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RpcWorkerServiceMethodDescriptorSupplier("Login"))
              .build();
        }
      }
    }
    return getLoginMethod;
  }

  private static volatile io.grpc.MethodDescriptor<contests.network.rpcprotocol.LogoutRequest,
      contests.network.rpcprotocol.LogoutResponse> getLogoutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Logout",
      requestType = contests.network.rpcprotocol.LogoutRequest.class,
      responseType = contests.network.rpcprotocol.LogoutResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<contests.network.rpcprotocol.LogoutRequest,
      contests.network.rpcprotocol.LogoutResponse> getLogoutMethod() {
    io.grpc.MethodDescriptor<contests.network.rpcprotocol.LogoutRequest, contests.network.rpcprotocol.LogoutResponse> getLogoutMethod;
    if ((getLogoutMethod = RpcWorkerServiceGrpc.getLogoutMethod) == null) {
      synchronized (RpcWorkerServiceGrpc.class) {
        if ((getLogoutMethod = RpcWorkerServiceGrpc.getLogoutMethod) == null) {
          RpcWorkerServiceGrpc.getLogoutMethod = getLogoutMethod =
              io.grpc.MethodDescriptor.<contests.network.rpcprotocol.LogoutRequest, contests.network.rpcprotocol.LogoutResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Logout"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.LogoutRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.LogoutResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RpcWorkerServiceMethodDescriptorSupplier("Logout"))
              .build();
        }
      }
    }
    return getLogoutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<contests.network.rpcprotocol.GetContestsRequest,
      contests.network.rpcprotocol.GetContestsResponse> getGetContestsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetContests",
      requestType = contests.network.rpcprotocol.GetContestsRequest.class,
      responseType = contests.network.rpcprotocol.GetContestsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<contests.network.rpcprotocol.GetContestsRequest,
      contests.network.rpcprotocol.GetContestsResponse> getGetContestsMethod() {
    io.grpc.MethodDescriptor<contests.network.rpcprotocol.GetContestsRequest, contests.network.rpcprotocol.GetContestsResponse> getGetContestsMethod;
    if ((getGetContestsMethod = RpcWorkerServiceGrpc.getGetContestsMethod) == null) {
      synchronized (RpcWorkerServiceGrpc.class) {
        if ((getGetContestsMethod = RpcWorkerServiceGrpc.getGetContestsMethod) == null) {
          RpcWorkerServiceGrpc.getGetContestsMethod = getGetContestsMethod =
              io.grpc.MethodDescriptor.<contests.network.rpcprotocol.GetContestsRequest, contests.network.rpcprotocol.GetContestsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetContests"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.GetContestsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.GetContestsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RpcWorkerServiceMethodDescriptorSupplier("GetContests"))
              .build();
        }
      }
    }
    return getGetContestsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<contests.network.rpcprotocol.GetParticipantsRequest,
      contests.network.rpcprotocol.GetParticipantsResponse> getGetParticipantsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetParticipants",
      requestType = contests.network.rpcprotocol.GetParticipantsRequest.class,
      responseType = contests.network.rpcprotocol.GetParticipantsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<contests.network.rpcprotocol.GetParticipantsRequest,
      contests.network.rpcprotocol.GetParticipantsResponse> getGetParticipantsMethod() {
    io.grpc.MethodDescriptor<contests.network.rpcprotocol.GetParticipantsRequest, contests.network.rpcprotocol.GetParticipantsResponse> getGetParticipantsMethod;
    if ((getGetParticipantsMethod = RpcWorkerServiceGrpc.getGetParticipantsMethod) == null) {
      synchronized (RpcWorkerServiceGrpc.class) {
        if ((getGetParticipantsMethod = RpcWorkerServiceGrpc.getGetParticipantsMethod) == null) {
          RpcWorkerServiceGrpc.getGetParticipantsMethod = getGetParticipantsMethod =
              io.grpc.MethodDescriptor.<contests.network.rpcprotocol.GetParticipantsRequest, contests.network.rpcprotocol.GetParticipantsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetParticipants"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.GetParticipantsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.GetParticipantsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RpcWorkerServiceMethodDescriptorSupplier("GetParticipants"))
              .build();
        }
      }
    }
    return getGetParticipantsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<contests.network.rpcprotocol.GetParticipantsByProbaRequest,
      contests.network.rpcprotocol.GetParticipantsResponse> getGetParticipantsByProbaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetParticipantsByProba",
      requestType = contests.network.rpcprotocol.GetParticipantsByProbaRequest.class,
      responseType = contests.network.rpcprotocol.GetParticipantsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<contests.network.rpcprotocol.GetParticipantsByProbaRequest,
      contests.network.rpcprotocol.GetParticipantsResponse> getGetParticipantsByProbaMethod() {
    io.grpc.MethodDescriptor<contests.network.rpcprotocol.GetParticipantsByProbaRequest, contests.network.rpcprotocol.GetParticipantsResponse> getGetParticipantsByProbaMethod;
    if ((getGetParticipantsByProbaMethod = RpcWorkerServiceGrpc.getGetParticipantsByProbaMethod) == null) {
      synchronized (RpcWorkerServiceGrpc.class) {
        if ((getGetParticipantsByProbaMethod = RpcWorkerServiceGrpc.getGetParticipantsByProbaMethod) == null) {
          RpcWorkerServiceGrpc.getGetParticipantsByProbaMethod = getGetParticipantsByProbaMethod =
              io.grpc.MethodDescriptor.<contests.network.rpcprotocol.GetParticipantsByProbaRequest, contests.network.rpcprotocol.GetParticipantsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetParticipantsByProba"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.GetParticipantsByProbaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.GetParticipantsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RpcWorkerServiceMethodDescriptorSupplier("GetParticipantsByProba"))
              .build();
        }
      }
    }
    return getGetParticipantsByProbaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<contests.network.rpcprotocol.RegisterParticipantRequest,
      contests.network.rpcprotocol.Response> getAttemptRegisterParticipantMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AttemptRegisterParticipant",
      requestType = contests.network.rpcprotocol.RegisterParticipantRequest.class,
      responseType = contests.network.rpcprotocol.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<contests.network.rpcprotocol.RegisterParticipantRequest,
      contests.network.rpcprotocol.Response> getAttemptRegisterParticipantMethod() {
    io.grpc.MethodDescriptor<contests.network.rpcprotocol.RegisterParticipantRequest, contests.network.rpcprotocol.Response> getAttemptRegisterParticipantMethod;
    if ((getAttemptRegisterParticipantMethod = RpcWorkerServiceGrpc.getAttemptRegisterParticipantMethod) == null) {
      synchronized (RpcWorkerServiceGrpc.class) {
        if ((getAttemptRegisterParticipantMethod = RpcWorkerServiceGrpc.getAttemptRegisterParticipantMethod) == null) {
          RpcWorkerServiceGrpc.getAttemptRegisterParticipantMethod = getAttemptRegisterParticipantMethod =
              io.grpc.MethodDescriptor.<contests.network.rpcprotocol.RegisterParticipantRequest, contests.network.rpcprotocol.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AttemptRegisterParticipant"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.RegisterParticipantRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.Response.getDefaultInstance()))
              .setSchemaDescriptor(new RpcWorkerServiceMethodDescriptorSupplier("AttemptRegisterParticipant"))
              .build();
        }
      }
    }
    return getAttemptRegisterParticipantMethod;
  }

  private static volatile io.grpc.MethodDescriptor<contests.network.rpcprotocol.LoginRequest,
      contests.network.rpcprotocol.UpdateResponse> getListenForUpdatesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListenForUpdates",
      requestType = contests.network.rpcprotocol.LoginRequest.class,
      responseType = contests.network.rpcprotocol.UpdateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<contests.network.rpcprotocol.LoginRequest,
      contests.network.rpcprotocol.UpdateResponse> getListenForUpdatesMethod() {
    io.grpc.MethodDescriptor<contests.network.rpcprotocol.LoginRequest, contests.network.rpcprotocol.UpdateResponse> getListenForUpdatesMethod;
    if ((getListenForUpdatesMethod = RpcWorkerServiceGrpc.getListenForUpdatesMethod) == null) {
      synchronized (RpcWorkerServiceGrpc.class) {
        if ((getListenForUpdatesMethod = RpcWorkerServiceGrpc.getListenForUpdatesMethod) == null) {
          RpcWorkerServiceGrpc.getListenForUpdatesMethod = getListenForUpdatesMethod =
              io.grpc.MethodDescriptor.<contests.network.rpcprotocol.LoginRequest, contests.network.rpcprotocol.UpdateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListenForUpdates"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.LoginRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  contests.network.rpcprotocol.UpdateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RpcWorkerServiceMethodDescriptorSupplier("ListenForUpdates"))
              .build();
        }
      }
    }
    return getListenForUpdatesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RpcWorkerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RpcWorkerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RpcWorkerServiceStub>() {
        @java.lang.Override
        public RpcWorkerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RpcWorkerServiceStub(channel, callOptions);
        }
      };
    return RpcWorkerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RpcWorkerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RpcWorkerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RpcWorkerServiceBlockingStub>() {
        @java.lang.Override
        public RpcWorkerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RpcWorkerServiceBlockingStub(channel, callOptions);
        }
      };
    return RpcWorkerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RpcWorkerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RpcWorkerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RpcWorkerServiceFutureStub>() {
        @java.lang.Override
        public RpcWorkerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RpcWorkerServiceFutureStub(channel, callOptions);
        }
      };
    return RpcWorkerServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void login(contests.network.rpcprotocol.LoginRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.LoginResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoginMethod(), responseObserver);
    }

    /**
     */
    default void logout(contests.network.rpcprotocol.LogoutRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.LogoutResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLogoutMethod(), responseObserver);
    }

    /**
     */
    default void getContests(contests.network.rpcprotocol.GetContestsRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.GetContestsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetContestsMethod(), responseObserver);
    }

    /**
     */
    default void getParticipants(contests.network.rpcprotocol.GetParticipantsRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.GetParticipantsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetParticipantsMethod(), responseObserver);
    }

    /**
     */
    default void getParticipantsByProba(contests.network.rpcprotocol.GetParticipantsByProbaRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.GetParticipantsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetParticipantsByProbaMethod(), responseObserver);
    }

    /**
     */
    default void attemptRegisterParticipant(contests.network.rpcprotocol.RegisterParticipantRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAttemptRegisterParticipantMethod(), responseObserver);
    }

    /**
     */
    default void listenForUpdates(contests.network.rpcprotocol.LoginRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.UpdateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListenForUpdatesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service RpcWorkerService.
   */
  public static abstract class RpcWorkerServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return RpcWorkerServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service RpcWorkerService.
   */
  public static final class RpcWorkerServiceStub
      extends io.grpc.stub.AbstractAsyncStub<RpcWorkerServiceStub> {
    private RpcWorkerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RpcWorkerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RpcWorkerServiceStub(channel, callOptions);
    }

    /**
     */
    public void login(contests.network.rpcprotocol.LoginRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.LoginResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void logout(contests.network.rpcprotocol.LogoutRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.LogoutResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getContests(contests.network.rpcprotocol.GetContestsRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.GetContestsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetContestsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getParticipants(contests.network.rpcprotocol.GetParticipantsRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.GetParticipantsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetParticipantsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getParticipantsByProba(contests.network.rpcprotocol.GetParticipantsByProbaRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.GetParticipantsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetParticipantsByProbaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void attemptRegisterParticipant(contests.network.rpcprotocol.RegisterParticipantRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAttemptRegisterParticipantMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listenForUpdates(contests.network.rpcprotocol.LoginRequest request,
        io.grpc.stub.StreamObserver<contests.network.rpcprotocol.UpdateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getListenForUpdatesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service RpcWorkerService.
   */
  public static final class RpcWorkerServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<RpcWorkerServiceBlockingStub> {
    private RpcWorkerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RpcWorkerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RpcWorkerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public contests.network.rpcprotocol.LoginResponse login(contests.network.rpcprotocol.LoginRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoginMethod(), getCallOptions(), request);
    }

    /**
     */
    public contests.network.rpcprotocol.LogoutResponse logout(contests.network.rpcprotocol.LogoutRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLogoutMethod(), getCallOptions(), request);
    }

    /**
     */
    public contests.network.rpcprotocol.GetContestsResponse getContests(contests.network.rpcprotocol.GetContestsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetContestsMethod(), getCallOptions(), request);
    }

    /**
     */
    public contests.network.rpcprotocol.GetParticipantsResponse getParticipants(contests.network.rpcprotocol.GetParticipantsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetParticipantsMethod(), getCallOptions(), request);
    }

    /**
     */
    public contests.network.rpcprotocol.GetParticipantsResponse getParticipantsByProba(contests.network.rpcprotocol.GetParticipantsByProbaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetParticipantsByProbaMethod(), getCallOptions(), request);
    }

    /**
     */
    public contests.network.rpcprotocol.Response attemptRegisterParticipant(contests.network.rpcprotocol.RegisterParticipantRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAttemptRegisterParticipantMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<contests.network.rpcprotocol.UpdateResponse> listenForUpdates(
        contests.network.rpcprotocol.LoginRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getListenForUpdatesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service RpcWorkerService.
   */
  public static final class RpcWorkerServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<RpcWorkerServiceFutureStub> {
    private RpcWorkerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RpcWorkerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RpcWorkerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<contests.network.rpcprotocol.LoginResponse> login(
        contests.network.rpcprotocol.LoginRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<contests.network.rpcprotocol.LogoutResponse> logout(
        contests.network.rpcprotocol.LogoutRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<contests.network.rpcprotocol.GetContestsResponse> getContests(
        contests.network.rpcprotocol.GetContestsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetContestsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<contests.network.rpcprotocol.GetParticipantsResponse> getParticipants(
        contests.network.rpcprotocol.GetParticipantsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetParticipantsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<contests.network.rpcprotocol.GetParticipantsResponse> getParticipantsByProba(
        contests.network.rpcprotocol.GetParticipantsByProbaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetParticipantsByProbaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<contests.network.rpcprotocol.Response> attemptRegisterParticipant(
        contests.network.rpcprotocol.RegisterParticipantRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAttemptRegisterParticipantMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOGIN = 0;
  private static final int METHODID_LOGOUT = 1;
  private static final int METHODID_GET_CONTESTS = 2;
  private static final int METHODID_GET_PARTICIPANTS = 3;
  private static final int METHODID_GET_PARTICIPANTS_BY_PROBA = 4;
  private static final int METHODID_ATTEMPT_REGISTER_PARTICIPANT = 5;
  private static final int METHODID_LISTEN_FOR_UPDATES = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOGIN:
          serviceImpl.login((contests.network.rpcprotocol.LoginRequest) request,
              (io.grpc.stub.StreamObserver<contests.network.rpcprotocol.LoginResponse>) responseObserver);
          break;
        case METHODID_LOGOUT:
          serviceImpl.logout((contests.network.rpcprotocol.LogoutRequest) request,
              (io.grpc.stub.StreamObserver<contests.network.rpcprotocol.LogoutResponse>) responseObserver);
          break;
        case METHODID_GET_CONTESTS:
          serviceImpl.getContests((contests.network.rpcprotocol.GetContestsRequest) request,
              (io.grpc.stub.StreamObserver<contests.network.rpcprotocol.GetContestsResponse>) responseObserver);
          break;
        case METHODID_GET_PARTICIPANTS:
          serviceImpl.getParticipants((contests.network.rpcprotocol.GetParticipantsRequest) request,
              (io.grpc.stub.StreamObserver<contests.network.rpcprotocol.GetParticipantsResponse>) responseObserver);
          break;
        case METHODID_GET_PARTICIPANTS_BY_PROBA:
          serviceImpl.getParticipantsByProba((contests.network.rpcprotocol.GetParticipantsByProbaRequest) request,
              (io.grpc.stub.StreamObserver<contests.network.rpcprotocol.GetParticipantsResponse>) responseObserver);
          break;
        case METHODID_ATTEMPT_REGISTER_PARTICIPANT:
          serviceImpl.attemptRegisterParticipant((contests.network.rpcprotocol.RegisterParticipantRequest) request,
              (io.grpc.stub.StreamObserver<contests.network.rpcprotocol.Response>) responseObserver);
          break;
        case METHODID_LISTEN_FOR_UPDATES:
          serviceImpl.listenForUpdates((contests.network.rpcprotocol.LoginRequest) request,
              (io.grpc.stub.StreamObserver<contests.network.rpcprotocol.UpdateResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getLoginMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              contests.network.rpcprotocol.LoginRequest,
              contests.network.rpcprotocol.LoginResponse>(
                service, METHODID_LOGIN)))
        .addMethod(
          getLogoutMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              contests.network.rpcprotocol.LogoutRequest,
              contests.network.rpcprotocol.LogoutResponse>(
                service, METHODID_LOGOUT)))
        .addMethod(
          getGetContestsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              contests.network.rpcprotocol.GetContestsRequest,
              contests.network.rpcprotocol.GetContestsResponse>(
                service, METHODID_GET_CONTESTS)))
        .addMethod(
          getGetParticipantsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              contests.network.rpcprotocol.GetParticipantsRequest,
              contests.network.rpcprotocol.GetParticipantsResponse>(
                service, METHODID_GET_PARTICIPANTS)))
        .addMethod(
          getGetParticipantsByProbaMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              contests.network.rpcprotocol.GetParticipantsByProbaRequest,
              contests.network.rpcprotocol.GetParticipantsResponse>(
                service, METHODID_GET_PARTICIPANTS_BY_PROBA)))
        .addMethod(
          getAttemptRegisterParticipantMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              contests.network.rpcprotocol.RegisterParticipantRequest,
              contests.network.rpcprotocol.Response>(
                service, METHODID_ATTEMPT_REGISTER_PARTICIPANT)))
        .addMethod(
          getListenForUpdatesMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              contests.network.rpcprotocol.LoginRequest,
              contests.network.rpcprotocol.UpdateResponse>(
                service, METHODID_LISTEN_FOR_UPDATES)))
        .build();
  }

  private static abstract class RpcWorkerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RpcWorkerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return contests.network.rpcprotocol.RpcWorkerProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RpcWorkerService");
    }
  }

  private static final class RpcWorkerServiceFileDescriptorSupplier
      extends RpcWorkerServiceBaseDescriptorSupplier {
    RpcWorkerServiceFileDescriptorSupplier() {}
  }

  private static final class RpcWorkerServiceMethodDescriptorSupplier
      extends RpcWorkerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    RpcWorkerServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (RpcWorkerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RpcWorkerServiceFileDescriptorSupplier())
              .addMethod(getLoginMethod())
              .addMethod(getLogoutMethod())
              .addMethod(getGetContestsMethod())
              .addMethod(getGetParticipantsMethod())
              .addMethod(getGetParticipantsByProbaMethod())
              .addMethod(getAttemptRegisterParticipantMethod())
              .addMethod(getListenForUpdatesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
