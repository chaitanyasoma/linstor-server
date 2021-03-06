// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: linstor/proto/MsgControlCtrl.proto

package com.linbit.linstor.proto;

public final class MsgControlCtrlOuterClass {
  private MsgControlCtrlOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MsgControlCtrlOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.linbit.linstor.proto.MsgControlCtrl)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * command
     * </pre>
     *
     * <code>optional string command = 1;</code>
     */
    boolean hasCommand();
    /**
     * <pre>
     * command
     * </pre>
     *
     * <code>optional string command = 1;</code>
     */
    java.lang.String getCommand();
    /**
     * <pre>
     * command
     * </pre>
     *
     * <code>optional string command = 1;</code>
     */
    com.google.protobuf.ByteString
        getCommandBytes();
  }
  /**
   * <pre>
   * linstor - Msg used to send commands to the controller e.g. shutdown
   * </pre>
   *
   * Protobuf type {@code com.linbit.linstor.proto.MsgControlCtrl}
   */
  public  static final class MsgControlCtrl extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.linbit.linstor.proto.MsgControlCtrl)
      MsgControlCtrlOrBuilder {
    // Use MsgControlCtrl.newBuilder() to construct.
    private MsgControlCtrl(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MsgControlCtrl() {
      command_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private MsgControlCtrl(
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
              command_ = bs;
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
      return com.linbit.linstor.proto.MsgControlCtrlOuterClass.internal_static_com_linbit_linstor_proto_MsgControlCtrl_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.linbit.linstor.proto.MsgControlCtrlOuterClass.internal_static_com_linbit_linstor_proto_MsgControlCtrl_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl.class, com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl.Builder.class);
    }

    private int bitField0_;
    public static final int COMMAND_FIELD_NUMBER = 1;
    private volatile java.lang.Object command_;
    /**
     * <pre>
     * command
     * </pre>
     *
     * <code>optional string command = 1;</code>
     */
    public boolean hasCommand() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <pre>
     * command
     * </pre>
     *
     * <code>optional string command = 1;</code>
     */
    public java.lang.String getCommand() {
      java.lang.Object ref = command_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          command_ = s;
        }
        return s;
      }
    }
    /**
     * <pre>
     * command
     * </pre>
     *
     * <code>optional string command = 1;</code>
     */
    public com.google.protobuf.ByteString
        getCommandBytes() {
      java.lang.Object ref = command_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        command_ = b;
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

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, command_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, command_);
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
      if (!(obj instanceof com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl)) {
        return super.equals(obj);
      }
      com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl other = (com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl) obj;

      boolean result = true;
      result = result && (hasCommand() == other.hasCommand());
      if (hasCommand()) {
        result = result && getCommand()
            .equals(other.getCommand());
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
      if (hasCommand()) {
        hash = (37 * hash) + COMMAND_FIELD_NUMBER;
        hash = (53 * hash) + getCommand().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl parseFrom(
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
    public static Builder newBuilder(com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl prototype) {
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
     * linstor - Msg used to send commands to the controller e.g. shutdown
     * </pre>
     *
     * Protobuf type {@code com.linbit.linstor.proto.MsgControlCtrl}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.linbit.linstor.proto.MsgControlCtrl)
        com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrlOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.linbit.linstor.proto.MsgControlCtrlOuterClass.internal_static_com_linbit_linstor_proto_MsgControlCtrl_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.linbit.linstor.proto.MsgControlCtrlOuterClass.internal_static_com_linbit_linstor_proto_MsgControlCtrl_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl.class, com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl.Builder.class);
      }

      // Construct using com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl.newBuilder()
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
        command_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.linbit.linstor.proto.MsgControlCtrlOuterClass.internal_static_com_linbit_linstor_proto_MsgControlCtrl_descriptor;
      }

      public com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl getDefaultInstanceForType() {
        return com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl.getDefaultInstance();
      }

      public com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl build() {
        com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl buildPartial() {
        com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl result = new com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.command_ = command_;
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
        if (other instanceof com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl) {
          return mergeFrom((com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl other) {
        if (other == com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl.getDefaultInstance()) return this;
        if (other.hasCommand()) {
          bitField0_ |= 0x00000001;
          command_ = other.command_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
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
        com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object command_ = "";
      /**
       * <pre>
       * command
       * </pre>
       *
       * <code>optional string command = 1;</code>
       */
      public boolean hasCommand() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <pre>
       * command
       * </pre>
       *
       * <code>optional string command = 1;</code>
       */
      public java.lang.String getCommand() {
        java.lang.Object ref = command_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            command_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       * command
       * </pre>
       *
       * <code>optional string command = 1;</code>
       */
      public com.google.protobuf.ByteString
          getCommandBytes() {
        java.lang.Object ref = command_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          command_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * command
       * </pre>
       *
       * <code>optional string command = 1;</code>
       */
      public Builder setCommand(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        command_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * command
       * </pre>
       *
       * <code>optional string command = 1;</code>
       */
      public Builder clearCommand() {
        bitField0_ = (bitField0_ & ~0x00000001);
        command_ = getDefaultInstance().getCommand();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * command
       * </pre>
       *
       * <code>optional string command = 1;</code>
       */
      public Builder setCommandBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        command_ = value;
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


      // @@protoc_insertion_point(builder_scope:com.linbit.linstor.proto.MsgControlCtrl)
    }

    // @@protoc_insertion_point(class_scope:com.linbit.linstor.proto.MsgControlCtrl)
    private static final com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl();
    }

    public static com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<MsgControlCtrl>
        PARSER = new com.google.protobuf.AbstractParser<MsgControlCtrl>() {
      public MsgControlCtrl parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new MsgControlCtrl(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MsgControlCtrl> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MsgControlCtrl> getParserForType() {
      return PARSER;
    }

    public com.linbit.linstor.proto.MsgControlCtrlOuterClass.MsgControlCtrl getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_linbit_linstor_proto_MsgControlCtrl_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_linbit_linstor_proto_MsgControlCtrl_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\"linstor/proto/MsgControlCtrl.proto\022\030co" +
      "m.linbit.linstor.proto\"!\n\016MsgControlCtrl" +
      "\022\017\n\007command\030\001 \001(\t"
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
    internal_static_com_linbit_linstor_proto_MsgControlCtrl_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_linbit_linstor_proto_MsgControlCtrl_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_linbit_linstor_proto_MsgControlCtrl_descriptor,
        new java.lang.String[] { "Command", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
