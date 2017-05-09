// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MsgLgcCreateResource.proto

package com.linbit.drbdmanage.proto;

public final class MsgLgcCreateResourceOuterClass {
  private MsgLgcCreateResourceOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MsgLgcCreateResourceOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.linbit.drbdmanage.proto.MsgLgcCreateResource)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * Resource name
     * </pre>
     *
     * <code>string res_name = 1;</code>
     */
    java.lang.String getResName();
    /**
     * <pre>
     * Resource name
     * </pre>
     *
     * <code>string res_name = 1;</code>
     */
    com.google.protobuf.ByteString
        getResNameBytes();

    /**
     * <pre>
     * Resource properties map
     * </pre>
     *
     * <code>map&lt;string, string&gt; props = 2;</code>
     */
    int getPropsCount();
    /**
     * <pre>
     * Resource properties map
     * </pre>
     *
     * <code>map&lt;string, string&gt; props = 2;</code>
     */
    boolean containsProps(
        java.lang.String key);
    /**
     * Use {@link #getPropsMap()} instead.
     */
    @java.lang.Deprecated
    java.util.Map<java.lang.String, java.lang.String>
    getProps();
    /**
     * <pre>
     * Resource properties map
     * </pre>
     *
     * <code>map&lt;string, string&gt; props = 2;</code>
     */
    java.util.Map<java.lang.String, java.lang.String>
    getPropsMap();
    /**
     * <pre>
     * Resource properties map
     * </pre>
     *
     * <code>map&lt;string, string&gt; props = 2;</code>
     */

    java.lang.String getPropsOrDefault(
        java.lang.String key,
        java.lang.String defaultValue);
    /**
     * <pre>
     * Resource properties map
     * </pre>
     *
     * <code>map&lt;string, string&gt; props = 2;</code>
     */

    java.lang.String getPropsOrThrow(
        java.lang.String key);
  }
  /**
   * <pre>
   * Legacy drbdmanage-1 create_resource()
   * </pre>
   *
   * Protobuf type {@code com.linbit.drbdmanage.proto.MsgLgcCreateResource}
   */
  public  static final class MsgLgcCreateResource extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.linbit.drbdmanage.proto.MsgLgcCreateResource)
      MsgLgcCreateResourceOrBuilder {
    // Use MsgLgcCreateResource.newBuilder() to construct.
    private MsgLgcCreateResource(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MsgLgcCreateResource() {
      resName_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private MsgLgcCreateResource(
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
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              resName_ = s;
              break;
            }
            case 18: {
              if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
                props_ = com.google.protobuf.MapField.newMapField(
                    PropsDefaultEntryHolder.defaultEntry);
                mutable_bitField0_ |= 0x00000002;
              }
              com.google.protobuf.MapEntry<java.lang.String, java.lang.String>
              props__ = input.readMessage(
                  PropsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
              props_.getMutableMap().put(
                  props__.getKey(), props__.getValue());
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
      return com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 2:
          return internalGetProps();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource.class, com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource.Builder.class);
    }

    private int bitField0_;
    public static final int RES_NAME_FIELD_NUMBER = 1;
    private volatile java.lang.Object resName_;
    /**
     * <pre>
     * Resource name
     * </pre>
     *
     * <code>string res_name = 1;</code>
     */
    public java.lang.String getResName() {
      java.lang.Object ref = resName_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        resName_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * Resource name
     * </pre>
     *
     * <code>string res_name = 1;</code>
     */
    public com.google.protobuf.ByteString
        getResNameBytes() {
      java.lang.Object ref = resName_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        resName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PROPS_FIELD_NUMBER = 2;
    private static final class PropsDefaultEntryHolder {
      static final com.google.protobuf.MapEntry<
          java.lang.String, java.lang.String> defaultEntry =
              com.google.protobuf.MapEntry
              .<java.lang.String, java.lang.String>newDefaultInstance(
                  com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_PropsEntry_descriptor, 
                  com.google.protobuf.WireFormat.FieldType.STRING,
                  "",
                  com.google.protobuf.WireFormat.FieldType.STRING,
                  "");
    }
    private com.google.protobuf.MapField<
        java.lang.String, java.lang.String> props_;
    private com.google.protobuf.MapField<java.lang.String, java.lang.String>
    internalGetProps() {
      if (props_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            PropsDefaultEntryHolder.defaultEntry);
      }
      return props_;
    }

    public int getPropsCount() {
      return internalGetProps().getMap().size();
    }
    /**
     * <pre>
     * Resource properties map
     * </pre>
     *
     * <code>map&lt;string, string&gt; props = 2;</code>
     */

    public boolean containsProps(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      return internalGetProps().getMap().containsKey(key);
    }
    /**
     * Use {@link #getPropsMap()} instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String> getProps() {
      return getPropsMap();
    }
    /**
     * <pre>
     * Resource properties map
     * </pre>
     *
     * <code>map&lt;string, string&gt; props = 2;</code>
     */

    public java.util.Map<java.lang.String, java.lang.String> getPropsMap() {
      return internalGetProps().getMap();
    }
    /**
     * <pre>
     * Resource properties map
     * </pre>
     *
     * <code>map&lt;string, string&gt; props = 2;</code>
     */

    public java.lang.String getPropsOrDefault(
        java.lang.String key,
        java.lang.String defaultValue) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      java.util.Map<java.lang.String, java.lang.String> map =
          internalGetProps().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <pre>
     * Resource properties map
     * </pre>
     *
     * <code>map&lt;string, string&gt; props = 2;</code>
     */

    public java.lang.String getPropsOrThrow(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      java.util.Map<java.lang.String, java.lang.String> map =
          internalGetProps().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
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
      if (!getResNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, resName_);
      }
      com.google.protobuf.GeneratedMessageV3
        .serializeStringMapTo(
          output,
          internalGetProps(),
          PropsDefaultEntryHolder.defaultEntry,
          2);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getResNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, resName_);
      }
      for (java.util.Map.Entry<java.lang.String, java.lang.String> entry
           : internalGetProps().getMap().entrySet()) {
        com.google.protobuf.MapEntry<java.lang.String, java.lang.String>
        props__ = PropsDefaultEntryHolder.defaultEntry.newBuilderForType()
            .setKey(entry.getKey())
            .setValue(entry.getValue())
            .build();
        size += com.google.protobuf.CodedOutputStream
            .computeMessageSize(2, props__);
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
      if (!(obj instanceof com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource)) {
        return super.equals(obj);
      }
      com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource other = (com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource) obj;

      boolean result = true;
      result = result && getResName()
          .equals(other.getResName());
      result = result && internalGetProps().equals(
          other.internalGetProps());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + RES_NAME_FIELD_NUMBER;
      hash = (53 * hash) + getResName().hashCode();
      if (!internalGetProps().getMap().isEmpty()) {
        hash = (37 * hash) + PROPS_FIELD_NUMBER;
        hash = (53 * hash) + internalGetProps().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource parseFrom(
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
    public static Builder newBuilder(com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource prototype) {
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
     * Legacy drbdmanage-1 create_resource()
     * </pre>
     *
     * Protobuf type {@code com.linbit.drbdmanage.proto.MsgLgcCreateResource}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.linbit.drbdmanage.proto.MsgLgcCreateResource)
        com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResourceOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_descriptor;
      }

      @SuppressWarnings({"rawtypes"})
      protected com.google.protobuf.MapField internalGetMapField(
          int number) {
        switch (number) {
          case 2:
            return internalGetProps();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + number);
        }
      }
      @SuppressWarnings({"rawtypes"})
      protected com.google.protobuf.MapField internalGetMutableMapField(
          int number) {
        switch (number) {
          case 2:
            return internalGetMutableProps();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + number);
        }
      }
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource.class, com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource.Builder.class);
      }

      // Construct using com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource.newBuilder()
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
        resName_ = "";

        internalGetMutableProps().clear();
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_descriptor;
      }

      public com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource getDefaultInstanceForType() {
        return com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource.getDefaultInstance();
      }

      public com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource build() {
        com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource buildPartial() {
        com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource result = new com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        result.resName_ = resName_;
        result.props_ = internalGetProps();
        result.props_.makeImmutable();
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
        if (other instanceof com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource) {
          return mergeFrom((com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource other) {
        if (other == com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource.getDefaultInstance()) return this;
        if (!other.getResName().isEmpty()) {
          resName_ = other.resName_;
          onChanged();
        }
        internalGetMutableProps().mergeFrom(
            other.internalGetProps());
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
        com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object resName_ = "";
      /**
       * <pre>
       * Resource name
       * </pre>
       *
       * <code>string res_name = 1;</code>
       */
      public java.lang.String getResName() {
        java.lang.Object ref = resName_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          resName_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       * Resource name
       * </pre>
       *
       * <code>string res_name = 1;</code>
       */
      public com.google.protobuf.ByteString
          getResNameBytes() {
        java.lang.Object ref = resName_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          resName_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * Resource name
       * </pre>
       *
       * <code>string res_name = 1;</code>
       */
      public Builder setResName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        resName_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * Resource name
       * </pre>
       *
       * <code>string res_name = 1;</code>
       */
      public Builder clearResName() {
        
        resName_ = getDefaultInstance().getResName();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * Resource name
       * </pre>
       *
       * <code>string res_name = 1;</code>
       */
      public Builder setResNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        resName_ = value;
        onChanged();
        return this;
      }

      private com.google.protobuf.MapField<
          java.lang.String, java.lang.String> props_;
      private com.google.protobuf.MapField<java.lang.String, java.lang.String>
      internalGetProps() {
        if (props_ == null) {
          return com.google.protobuf.MapField.emptyMapField(
              PropsDefaultEntryHolder.defaultEntry);
        }
        return props_;
      }
      private com.google.protobuf.MapField<java.lang.String, java.lang.String>
      internalGetMutableProps() {
        onChanged();;
        if (props_ == null) {
          props_ = com.google.protobuf.MapField.newMapField(
              PropsDefaultEntryHolder.defaultEntry);
        }
        if (!props_.isMutable()) {
          props_ = props_.copy();
        }
        return props_;
      }

      public int getPropsCount() {
        return internalGetProps().getMap().size();
      }
      /**
       * <pre>
       * Resource properties map
       * </pre>
       *
       * <code>map&lt;string, string&gt; props = 2;</code>
       */

      public boolean containsProps(
          java.lang.String key) {
        if (key == null) { throw new java.lang.NullPointerException(); }
        return internalGetProps().getMap().containsKey(key);
      }
      /**
       * Use {@link #getPropsMap()} instead.
       */
      @java.lang.Deprecated
      public java.util.Map<java.lang.String, java.lang.String> getProps() {
        return getPropsMap();
      }
      /**
       * <pre>
       * Resource properties map
       * </pre>
       *
       * <code>map&lt;string, string&gt; props = 2;</code>
       */

      public java.util.Map<java.lang.String, java.lang.String> getPropsMap() {
        return internalGetProps().getMap();
      }
      /**
       * <pre>
       * Resource properties map
       * </pre>
       *
       * <code>map&lt;string, string&gt; props = 2;</code>
       */

      public java.lang.String getPropsOrDefault(
          java.lang.String key,
          java.lang.String defaultValue) {
        if (key == null) { throw new java.lang.NullPointerException(); }
        java.util.Map<java.lang.String, java.lang.String> map =
            internalGetProps().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
      }
      /**
       * <pre>
       * Resource properties map
       * </pre>
       *
       * <code>map&lt;string, string&gt; props = 2;</code>
       */

      public java.lang.String getPropsOrThrow(
          java.lang.String key) {
        if (key == null) { throw new java.lang.NullPointerException(); }
        java.util.Map<java.lang.String, java.lang.String> map =
            internalGetProps().getMap();
        if (!map.containsKey(key)) {
          throw new java.lang.IllegalArgumentException();
        }
        return map.get(key);
      }

      public Builder clearProps() {
        internalGetMutableProps().getMutableMap()
            .clear();
        return this;
      }
      /**
       * <pre>
       * Resource properties map
       * </pre>
       *
       * <code>map&lt;string, string&gt; props = 2;</code>
       */

      public Builder removeProps(
          java.lang.String key) {
        if (key == null) { throw new java.lang.NullPointerException(); }
        internalGetMutableProps().getMutableMap()
            .remove(key);
        return this;
      }
      /**
       * Use alternate mutation accessors instead.
       */
      @java.lang.Deprecated
      public java.util.Map<java.lang.String, java.lang.String>
      getMutableProps() {
        return internalGetMutableProps().getMutableMap();
      }
      /**
       * <pre>
       * Resource properties map
       * </pre>
       *
       * <code>map&lt;string, string&gt; props = 2;</code>
       */
      public Builder putProps(
          java.lang.String key,
          java.lang.String value) {
        if (key == null) { throw new java.lang.NullPointerException(); }
        if (value == null) { throw new java.lang.NullPointerException(); }
        internalGetMutableProps().getMutableMap()
            .put(key, value);
        return this;
      }
      /**
       * <pre>
       * Resource properties map
       * </pre>
       *
       * <code>map&lt;string, string&gt; props = 2;</code>
       */

      public Builder putAllProps(
          java.util.Map<java.lang.String, java.lang.String> values) {
        internalGetMutableProps().getMutableMap()
            .putAll(values);
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


      // @@protoc_insertion_point(builder_scope:com.linbit.drbdmanage.proto.MsgLgcCreateResource)
    }

    // @@protoc_insertion_point(class_scope:com.linbit.drbdmanage.proto.MsgLgcCreateResource)
    private static final com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource();
    }

    public static com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<MsgLgcCreateResource>
        PARSER = new com.google.protobuf.AbstractParser<MsgLgcCreateResource>() {
      public MsgLgcCreateResource parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new MsgLgcCreateResource(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MsgLgcCreateResource> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MsgLgcCreateResource> getParserForType() {
      return PARSER;
    }

    public com.linbit.drbdmanage.proto.MsgLgcCreateResourceOuterClass.MsgLgcCreateResource getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_PropsEntry_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_PropsEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\032MsgLgcCreateResource.proto\022\033com.linbit" +
      ".drbdmanage.proto\"\243\001\n\024MsgLgcCreateResour" +
      "ce\022\020\n\010res_name\030\001 \001(\t\022K\n\005props\030\002 \003(\0132<.co" +
      "m.linbit.drbdmanage.proto.MsgLgcCreateRe" +
      "source.PropsEntry\032,\n\nPropsEntry\022\013\n\003key\030\001" +
      " \001(\t\022\r\n\005value\030\002 \001(\t:\0028\001b\006proto3"
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
    internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_descriptor,
        new java.lang.String[] { "ResName", "Props", });
    internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_PropsEntry_descriptor =
      internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_descriptor.getNestedTypes().get(0);
    internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_PropsEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_linbit_drbdmanage_proto_MsgLgcCreateResource_PropsEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}