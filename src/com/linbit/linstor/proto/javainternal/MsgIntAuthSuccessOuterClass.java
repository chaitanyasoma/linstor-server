// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: linstor/proto/javainternal/MsgIntAuthSuccess.proto

package com.linbit.linstor.proto.javainternal;

public final class MsgIntAuthSuccessOuterClass {
  private MsgIntAuthSuccessOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MsgIntAuthSuccessOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.linbit.linstor.proto.javainternal.MsgIntAuthSuccess)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * The next expected full sync id
     * </pre>
     *
     * <code>sint64 expected_full_sync_id = 1;</code>
     */
    long getExpectedFullSyncId();
  }
  /**
   * <pre>
   * linstor - Internal message of a successful authentication expected full sync id
   * </pre>
   *
   * Protobuf type {@code com.linbit.linstor.proto.javainternal.MsgIntAuthSuccess}
   */
  public  static final class MsgIntAuthSuccess extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.linbit.linstor.proto.javainternal.MsgIntAuthSuccess)
      MsgIntAuthSuccessOrBuilder {
    // Use MsgIntAuthSuccess.newBuilder() to construct.
    private MsgIntAuthSuccess(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MsgIntAuthSuccess() {
      expectedFullSyncId_ = 0L;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private MsgIntAuthSuccess(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 8: {

              expectedFullSyncId_ = input.readSInt64();
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
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.internal_static_com_linbit_linstor_proto_javainternal_MsgIntAuthSuccess_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.internal_static_com_linbit_linstor_proto_javainternal_MsgIntAuthSuccess_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess.class, com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess.Builder.class);
    }

    public static final int EXPECTED_FULL_SYNC_ID_FIELD_NUMBER = 1;
    private long expectedFullSyncId_;
    /**
     * <pre>
     * The next expected full sync id
     * </pre>
     *
     * <code>sint64 expected_full_sync_id = 1;</code>
     */
    public long getExpectedFullSyncId() {
      return expectedFullSyncId_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (expectedFullSyncId_ != 0L) {
        output.writeSInt64(1, expectedFullSyncId_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (expectedFullSyncId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeSInt64Size(1, expectedFullSyncId_);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess)) {
        return super.equals(obj);
      }
      com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess other = (com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess) obj;

      boolean result = true;
      result = result && (getExpectedFullSyncId()
          == other.getExpectedFullSyncId());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + EXPECTED_FULL_SYNC_ID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getExpectedFullSyncId());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess parseFrom(
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
    public static Builder newBuilder(com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess prototype) {
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
     * linstor - Internal message of a successful authentication expected full sync id
     * </pre>
     *
     * Protobuf type {@code com.linbit.linstor.proto.javainternal.MsgIntAuthSuccess}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.linbit.linstor.proto.javainternal.MsgIntAuthSuccess)
        com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccessOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.internal_static_com_linbit_linstor_proto_javainternal_MsgIntAuthSuccess_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.internal_static_com_linbit_linstor_proto_javainternal_MsgIntAuthSuccess_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess.class, com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess.Builder.class);
      }

      // Construct using com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess.newBuilder()
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
        expectedFullSyncId_ = 0L;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.internal_static_com_linbit_linstor_proto_javainternal_MsgIntAuthSuccess_descriptor;
      }

      public com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess getDefaultInstanceForType() {
        return com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess.getDefaultInstance();
      }

      public com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess build() {
        com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess buildPartial() {
        com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess result = new com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess(this);
        result.expectedFullSyncId_ = expectedFullSyncId_;
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
        if (other instanceof com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess) {
          return mergeFrom((com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess other) {
        if (other == com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess.getDefaultInstance()) return this;
        if (other.getExpectedFullSyncId() != 0L) {
          setExpectedFullSyncId(other.getExpectedFullSyncId());
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long expectedFullSyncId_ ;
      /**
       * <pre>
       * The next expected full sync id
       * </pre>
       *
       * <code>sint64 expected_full_sync_id = 1;</code>
       */
      public long getExpectedFullSyncId() {
        return expectedFullSyncId_;
      }
      /**
       * <pre>
       * The next expected full sync id
       * </pre>
       *
       * <code>sint64 expected_full_sync_id = 1;</code>
       */
      public Builder setExpectedFullSyncId(long value) {
        
        expectedFullSyncId_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * The next expected full sync id
       * </pre>
       *
       * <code>sint64 expected_full_sync_id = 1;</code>
       */
      public Builder clearExpectedFullSyncId() {
        
        expectedFullSyncId_ = 0L;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:com.linbit.linstor.proto.javainternal.MsgIntAuthSuccess)
    }

    // @@protoc_insertion_point(class_scope:com.linbit.linstor.proto.javainternal.MsgIntAuthSuccess)
    private static final com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess();
    }

    public static com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<MsgIntAuthSuccess>
        PARSER = new com.google.protobuf.AbstractParser<MsgIntAuthSuccess>() {
      public MsgIntAuthSuccess parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new MsgIntAuthSuccess(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MsgIntAuthSuccess> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MsgIntAuthSuccess> getParserForType() {
      return PARSER;
    }

    public com.linbit.linstor.proto.javainternal.MsgIntAuthSuccessOuterClass.MsgIntAuthSuccess getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_linbit_linstor_proto_javainternal_MsgIntAuthSuccess_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_linbit_linstor_proto_javainternal_MsgIntAuthSuccess_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n2linstor/proto/javainternal/MsgIntAuthS" +
      "uccess.proto\022%com.linbit.linstor.proto.j" +
      "avainternal\"2\n\021MsgIntAuthSuccess\022\035\n\025expe" +
      "cted_full_sync_id\030\001 \001(\022b\006proto3"
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
    internal_static_com_linbit_linstor_proto_javainternal_MsgIntAuthSuccess_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_linbit_linstor_proto_javainternal_MsgIntAuthSuccess_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_linbit_linstor_proto_javainternal_MsgIntAuthSuccess_descriptor,
        new java.lang.String[] { "ExpectedFullSyncId", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
