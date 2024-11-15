// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: rpcWorker.proto

package contests.network.rpcprotocol;

public interface ResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:rpcprotocol.Response)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.rpcprotocol.OkResponse okResponse = 1;</code>
   * @return Whether the okResponse field is set.
   */
  boolean hasOkResponse();
  /**
   * <code>.rpcprotocol.OkResponse okResponse = 1;</code>
   * @return The okResponse.
   */
  contests.network.rpcprotocol.OkResponse getOkResponse();
  /**
   * <code>.rpcprotocol.OkResponse okResponse = 1;</code>
   */
  contests.network.rpcprotocol.OkResponseOrBuilder getOkResponseOrBuilder();

  /**
   * <code>.rpcprotocol.ErrorResponse errorResponse = 2;</code>
   * @return Whether the errorResponse field is set.
   */
  boolean hasErrorResponse();
  /**
   * <code>.rpcprotocol.ErrorResponse errorResponse = 2;</code>
   * @return The errorResponse.
   */
  contests.network.rpcprotocol.ErrorResponse getErrorResponse();
  /**
   * <code>.rpcprotocol.ErrorResponse errorResponse = 2;</code>
   */
  contests.network.rpcprotocol.ErrorResponseOrBuilder getErrorResponseOrBuilder();

  /**
   * <code>.rpcprotocol.GetContestsResponse getContestsResponse = 3;</code>
   * @return Whether the getContestsResponse field is set.
   */
  boolean hasGetContestsResponse();
  /**
   * <code>.rpcprotocol.GetContestsResponse getContestsResponse = 3;</code>
   * @return The getContestsResponse.
   */
  contests.network.rpcprotocol.GetContestsResponse getGetContestsResponse();
  /**
   * <code>.rpcprotocol.GetContestsResponse getContestsResponse = 3;</code>
   */
  contests.network.rpcprotocol.GetContestsResponseOrBuilder getGetContestsResponseOrBuilder();

  /**
   * <code>.rpcprotocol.GetParticipantsResponse getParticipantsResponse = 4;</code>
   * @return Whether the getParticipantsResponse field is set.
   */
  boolean hasGetParticipantsResponse();
  /**
   * <code>.rpcprotocol.GetParticipantsResponse getParticipantsResponse = 4;</code>
   * @return The getParticipantsResponse.
   */
  contests.network.rpcprotocol.GetParticipantsResponse getGetParticipantsResponse();
  /**
   * <code>.rpcprotocol.GetParticipantsResponse getParticipantsResponse = 4;</code>
   */
  contests.network.rpcprotocol.GetParticipantsResponseOrBuilder getGetParticipantsResponseOrBuilder();

  /**
   * <code>.rpcprotocol.UpdateResponse updateResponse = 5;</code>
   * @return Whether the updateResponse field is set.
   */
  boolean hasUpdateResponse();
  /**
   * <code>.rpcprotocol.UpdateResponse updateResponse = 5;</code>
   * @return The updateResponse.
   */
  contests.network.rpcprotocol.UpdateResponse getUpdateResponse();
  /**
   * <code>.rpcprotocol.UpdateResponse updateResponse = 5;</code>
   */
  contests.network.rpcprotocol.UpdateResponseOrBuilder getUpdateResponseOrBuilder();

  contests.network.rpcprotocol.Response.ResponseCase getResponseCase();
}
