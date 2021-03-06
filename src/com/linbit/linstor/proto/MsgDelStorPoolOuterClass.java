// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: linstor/proto/MsgDelStorPool.proto

package com.linbit.linstor.proto;

public final class MsgDelStorPoolOuterClass {
  private MsgDelStorPoolOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MsgDelStorPoolOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.linbit.linstor.proto.MsgDelStorPool)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * UUID
     * </pre>
     *
     * <code>optional string uuid = 1;</code>
     */
    boolean hasUuid();
    /**
     * <pre>
     * UUID
     * </pre>
     *
     * <code>optional string uuid = 1;</code>
     */
    java.lang.String getUuid();
    /**
     * <pre>
     * UUID
     * </pre>
     *
     * <code>optional string uuid = 1;</code>
     */
    com.google.protobuf.ByteString
        getUuidBytes();

    /**
     * <pre>
     * Node name
     * </pre>
     *
     * <code>required string node_name = 2;</code>
     */
    boolean hasNodeName();
    /**
     * <pre>
     * Node name
     * </pre>
     *
     * <code>required string node_name = 2;</code>
     */
    java.lang.String getNodeName();
    /**
     * <pre>
     * Node name
     * </pre>
     *
     * <code>required string node_name = 2;</code>
     */
    com.google.protobuf.ByteString
        getNodeNameBytes();

    /**
     * <pre>
     * Storage pool name
     * </pre>
     *
     * <code>required string stor_pool_name = 3;</code>
     */
    boolean hasStorPoolName();
    /**
     * <pre>
     * Storage pool name
     * </pre>
     *
     * <code>required string stor_pool_name = 3;</code>
     */
    java.lang.String getStorPoolName();
    /**
     * <pre>
     * Storage pool name
     * </pre>
     *
     * <code>required string stor_pool_name = 3;</code>
     */
    com.google.protobuf.ByteString
        getStorPoolNameBytes();
  }
  /**
   * <pre>
   * linstor - Delete storage pool
   * </pre>
   *
   * Protobuf type {@code com.linbit.linstor.proto.MsgDelStorPool}
   */
  public  static final class MsgDelStorPool extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.linbit.linstor.proto.MsgDelStorPool)
      MsgDelStorPoolOrBuilder {
    // Use MsgDelStorPool.newBuilder() to construct.
    private MsgDelStorPool(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MsgDelStorPool() {
      uuid_ = "";
      nodeName_ = "";
      storPoolName_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private MsgDelStorPool(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000001;
              uuid_ = bs;
              break;
            }
            case 18: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000002;
              nodeName_ = bs;
              break;
            }
            case 26: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000004;
              storPoolName_ = bs;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.linbit.linstor.proto.MsgDelStorPoolOuterClass.internal_static_com_linbit_linstor_proto_MsgDelStorPool_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.linbit.linstor.proto.MsgDelStorPoolOuterClass.internal_static_com_linbit_linstor_proto_MsgDelStorPool_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool.class, com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool.Builder.class);
    }

    private int bitField0_;
    public static final int UUID_FIELD_NUMBER = 1;
    private volatile java.lang.Object uuid_;
    /**
     * <pre>
     * UUID
     * </pre>
     *
     * <code>optional string uuid = 1;</code>
     */
    public boolean hasUuid() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <pre>
     * UUID
     * </pre>
     *
     * <code>optional string uuid = 1;</code>
     */
    public java.lang.String getUuid() {
      java.lang.Object ref = uuid_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          uuid_ = s;
        }
        return s;
      }
    }
    /**
     * <pre>
     * UUID
     * </pre>
     *
     * <code>optional string uuid = 1;</code>
     */
    public com.google.protobuf.ByteString
        getUuidBytes() {
      java.lang.Object ref = uuid_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        uuid_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int NODE_NAME_FIELD_NUMBER = 2;
    private volatile java.lang.Object nodeName_;
    /**
     * <pre>
     * Node name
     * </pre>
     *
     * <code>required string node_name = 2;</code>
     */
    public boolean hasNodeName() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <pre>
     * Node name
     * </pre>
     *
     * <code>required string node_name = 2;</code>
     */
    public java.lang.String getNodeName() {
      java.lang.Object ref = nodeName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          nodeName_ = s;
        }
        return s;
      }
    }
    /**
     * <pre>
     * Node name
     * </pre>
     *
     * <code>required string node_name = 2;</code>
     */
    public com.google.protobuf.ByteString
        getNodeNameBytes() {
      java.lang.Object ref = nodeName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        nodeName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int STOR_POOL_NAME_FIELD_NUMBER = 3;
    private volatile java.lang.Object storPoolName_;
    /**
     * <pre>
     * Storage pool name
     * </pre>
     *
     * <code>required string stor_pool_name = 3;</code>
     */
    public boolean hasStorPoolName() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <pre>
     * Storage pool name
     * </pre>
     *
     * <code>required string stor_pool_name = 3;</code>
     */
    public java.lang.String getStorPoolName() {
      java.lang.Object ref = storPoolName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          storPoolName_ = s;
        }
        return s;
      }
    }
    /**
     * <pre>
     * Storage pool name
     * </pre>
     *
     * <code>required string stor_pool_name = 3;</code>
     */
    public com.google.protobuf.ByteString
        getStorPoolNameBytes() {
      java.lang.Object ref = storPoolName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        storPoolName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasNodeName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasStorPoolName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, uuid_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, nodeName_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, storPoolName_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, uuid_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, nodeName_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, storPoolName_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool)) {
        return super.equals(obj);
      }
      com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool other = (com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool) obj;

      boolean result = true;
      result = result && (hasUuid() == other.hasUuid());
      if (hasUuid()) {
        result = result && getUuid()
            .equals(other.getUuid());
      }
      result = result && (hasNodeName() == other.hasNodeName());
      if (hasNodeName()) {
        result = result && getNodeName()
            .equals(other.getNodeName());
      }
      result = result && (hasStorPoolName() == other.hasStorPoolName());
      if (hasStorPoolName()) {
        result = result && getStorPoolName()
            .equals(other.getStorPoolName());
      }
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasUuid()) {
        hash = (37 * hash) + UUID_FIELD_NUMBER;
        hash = (53 * hash) + getUuid().hashCode();
      }
      if (hasNodeName()) {
        hash = (37 * hash) + NODE_NAME_FIELD_NUMBER;
        hash = (53 * hash) + getNodeName().hashCode();
      }
      if (hasStorPoolName()) {
        hash = (37 * hash) + STOR_POOL_NAME_FIELD_NUMBER;
        hash = (53 * hash) + getStorPoolName().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * linstor - Delete storage pool
     * </pre>
     *
     * Protobuf type {@code com.linbit.linstor.proto.MsgDelStorPool}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.linbit.linstor.proto.MsgDelStorPool)
        com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPoolOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.linbit.linstor.proto.MsgDelStorPoolOuterClass.internal_static_com_linbit_linstor_proto_MsgDelStorPool_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.linbit.linstor.proto.MsgDelStorPoolOuterClass.internal_static_com_linbit_linstor_proto_MsgDelStorPool_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool.class, com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool.Builder.class);
      }

      // Construct using com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        uuid_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        nodeName_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        storPoolName_ = "";
        bitField0_ = (bitField0_ & ~0x00000004);
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.linbit.linstor.proto.MsgDelStorPoolOuterClass.internal_static_com_linbit_linstor_proto_MsgDelStorPool_descriptor;
      }

      public com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool getDefaultInstanceForType() {
        return com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool.getDefaultInstance();
      }

      public com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool build() {
        com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool buildPartial() {
        com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool result = new com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.uuid_ = uuid_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.nodeName_ = nodeName_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.storPoolName_ = storPoolName_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool) {
          return mergeFrom((com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool other) {
        if (other == com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool.getDefaultInstance()) return this;
        if (other.hasUuid()) {
          bitField0_ |= 0x00000001;
          uuid_ = other.uuid_;
          onChanged();
        }
        if (other.hasNodeName()) {
          bitField0_ |= 0x00000002;
          nodeName_ = other.nodeName_;
          onChanged();
        }
        if (other.hasStorPoolName()) {
          bitField0_ |= 0x00000004;
          storPoolName_ = other.storPoolName_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        if (!hasNodeName()) {
          return false;
        }
        if (!hasStorPoolName()) {
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object uuid_ = "";
      /**
       * <pre>
       * UUID
       * </pre>
       *
       * <code>optional string uuid = 1;</code>
       */
      public boolean hasUuid() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <pre>
       * UUID
       * </pre>
       *
       * <code>optional string uuid = 1;</code>
       */
      public java.lang.String getUuid() {
        java.lang.Object ref = uuid_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            uuid_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       * UUID
       * </pre>
       *
       * <code>optional string uuid = 1;</code>
       */
      public com.google.protobuf.ByteString
          getUuidBytes() {
        java.lang.Object ref = uuid_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          uuid_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * UUID
       * </pre>
       *
       * <code>optional string uuid = 1;</code>
       */
      public Builder setUuid(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        uuid_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * UUID
       * </pre>
       *
       * <code>optional string uuid = 1;</code>
       */
      public Builder clearUuid() {
        bitField0_ = (bitField0_ & ~0x00000001);
        uuid_ = getDefaultInstance().getUuid();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * UUID
       * </pre>
       *
       * <code>optional string uuid = 1;</code>
       */
      public Builder setUuidBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        uuid_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object nodeName_ = "";
      /**
       * <pre>
       * Node name
       * </pre>
       *
       * <code>required string node_name = 2;</code>
       */
      public boolean hasNodeName() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <pre>
       * Node name
       * </pre>
       *
       * <code>required string node_name = 2;</code>
       */
      public java.lang.String getNodeName() {
        java.lang.Object ref = nodeName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            nodeName_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       * Node name
       * </pre>
       *
       * <code>required string node_name = 2;</code>
       */
      public com.google.protobuf.ByteString
          getNodeNameBytes() {
        java.lang.Object ref = nodeName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          nodeName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * Node name
       * </pre>
       *
       * <code>required string node_name = 2;</code>
       */
      public Builder setNodeName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        nodeName_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * Node name
       * </pre>
       *
       * <code>required string node_name = 2;</code>
       */
      public Builder clearNodeName() {
        bitField0_ = (bitField0_ & ~0x00000002);
        nodeName_ = getDefaultInstance().getNodeName();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * Node name
       * </pre>
       *
       * <code>required string node_name = 2;</code>
       */
      public Builder setNodeNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        nodeName_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object storPoolName_ = "";
      /**
       * <pre>
       * Storage pool name
       * </pre>
       *
       * <code>required string stor_pool_name = 3;</code>
       */
      public boolean hasStorPoolName() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <pre>
       * Storage pool name
       * </pre>
       *
       * <code>required string stor_pool_name = 3;</code>
       */
      public java.lang.String getStorPoolName() {
        java.lang.Object ref = storPoolName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            storPoolName_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       * Storage pool name
       * </pre>
       *
       * <code>required string stor_pool_name = 3;</code>
       */
      public com.google.protobuf.ByteString
          getStorPoolNameBytes() {
        java.lang.Object ref = storPoolName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          storPoolName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * Storage pool name
       * </pre>
       *
       * <code>required string stor_pool_name = 3;</code>
       */
      public Builder setStorPoolName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        storPoolName_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * Storage pool name
       * </pre>
       *
       * <code>required string stor_pool_name = 3;</code>
       */
      public Builder clearStorPoolName() {
        bitField0_ = (bitField0_ & ~0x00000004);
        storPoolName_ = getDefaultInstance().getStorPoolName();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * Storage pool name
       * </pre>
       *
       * <code>required string stor_pool_name = 3;</code>
       */
      public Builder setStorPoolNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        storPoolName_ = value;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:com.linbit.linstor.proto.MsgDelStorPool)
    }

    // @@protoc_insertion_point(class_scope:com.linbit.linstor.proto.MsgDelStorPool)
    private static final com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool();
    }

    public static com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<MsgDelStorPool>
        PARSER = new com.google.protobuf.AbstractParser<MsgDelStorPool>() {
      public MsgDelStorPool parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new MsgDelStorPool(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MsgDelStorPool> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MsgDelStorPool> getParserForType() {
      return PARSER;
    }

    public com.linbit.linstor.proto.MsgDelStorPoolOuterClass.MsgDelStorPool getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_linbit_linstor_proto_MsgDelStorPool_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_linbit_linstor_proto_MsgDelStorPool_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\"linstor/proto/MsgDelStorPool.proto\022\030co" +
      "m.linbit.linstor.proto\"I\n\016MsgDelStorPool" +
      "\022\014\n\004uuid\030\001 \001(\t\022\021\n\tnode_name\030\002 \002(\t\022\026\n\016sto" +
      "r_pool_name\030\003 \002(\t"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_linbit_linstor_proto_MsgDelStorPool_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_linbit_linstor_proto_MsgDelStorPool_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_linbit_linstor_proto_MsgDelStorPool_descriptor,
        new java.lang.String[] { "Uuid", "NodeName", "StorPoolName", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
