/**
 *
 * Copyright (C) 2010 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.jclouds.s3.domain;

import java.util.Map;

import org.jclouds.io.ContentMetadataBuilder;
import org.jclouds.io.payloads.BaseMutableContentMetadata;
import org.jclouds.s3.domain.ObjectMetadata.StorageClass;
import org.jclouds.s3.domain.internal.MutableObjectMetadataImpl;

import com.google.common.collect.ImmutableMap;

/**
 * Allows you to create {@link ObjectMetadata} objects.
 * 
 * @author Adrian Cole
 */
public class ObjectMetadataBuilder {
   public static ObjectMetadataBuilder create() {
      return new ObjectMetadataBuilder();
   }

   private final ContentMetadataBuilder contentMetadataBuilder = new ContentMetadataBuilder().contentType("binary/octet-stream");

   private String key;
   private StorageClass storageClass;
   private String cacheControl;
   private Map<String, String> userMetadata = ImmutableMap.of();
 
   
   public ObjectMetadataBuilder key(String key) {
      this.key = key;
      return this;
   }

   public ObjectMetadataBuilder storageClass(StorageClass storageClass) {
      this.storageClass = storageClass;
      return this;
   }

   public ObjectMetadataBuilder cacheControl(String cacheControl) {
      this.cacheControl = cacheControl;
      return this;
   }

   public ObjectMetadataBuilder userMetadata(Map<String, String> userMetadata) {
      this.userMetadata = ImmutableMap.copyOf(userMetadata);
      return this;
   }

   public ObjectMetadataBuilder contentDisposition(String contentDisposition) {
      contentMetadataBuilder.contentDisposition(contentDisposition);
      return this;
   }

   public ObjectMetadataBuilder contentEncoding(String contentEncoding) {
      contentMetadataBuilder.contentEncoding(contentEncoding);
      return this;

   }

   public ObjectMetadataBuilder contentLanguage(String contentLanguage) {
      contentMetadataBuilder.contentLanguage(contentLanguage);
      return this;

   }

   public ObjectMetadataBuilder contentLength(Long contentLength) {
      contentMetadataBuilder.contentLength(contentLength);
      return this;

   }

   public ObjectMetadataBuilder contentMD5(byte[] md5) {
      contentMetadataBuilder.contentMD5(md5);
      return this;

   }

   public ObjectMetadataBuilder contentType(String contentType) {
      contentMetadataBuilder.contentType(contentType);
      return this;

   }

   public ObjectMetadata build() {
      MutableObjectMetadataImpl toReturn = new MutableObjectMetadataImpl();
      toReturn.setContentMetadata(BaseMutableContentMetadata.fromContentMetadata(contentMetadataBuilder.build()));
      toReturn.setCacheControl(cacheControl);
      toReturn.setKey(key);
      toReturn.setStorageClass(storageClass);
      toReturn.setUserMetadata(userMetadata);
      return toReturn;
   }

}
