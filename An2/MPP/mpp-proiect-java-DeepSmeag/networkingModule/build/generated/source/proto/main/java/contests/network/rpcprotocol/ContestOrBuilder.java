// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: rpcWorker.proto

package contests.network.rpcprotocol;

public interface ContestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:rpcprotocol.Contest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 id = 1;</code>
   * @return The id.
   */
  int getId();

  /**
   * <code>int32 nrParticipanti = 2;</code>
   * @return The nrParticipanti.
   */
  int getNrParticipanti();

  /**
   * <code>string numeProba = 3;</code>
   * @return The numeProba.
   */
  java.lang.String getNumeProba();
  /**
   * <code>string numeProba = 3;</code>
   * @return The bytes for numeProba.
   */
  com.google.protobuf.ByteString
      getNumeProbaBytes();

  /**
   * <code>string categorieVarsta = 4;</code>
   * @return The categorieVarsta.
   */
  java.lang.String getCategorieVarsta();
  /**
   * <code>string categorieVarsta = 4;</code>
   * @return The bytes for categorieVarsta.
   */
  com.google.protobuf.ByteString
      getCategorieVarstaBytes();
}
