/**
 *
 * Copyright (C) 2009 Cloud Conscious, LLC. <info@cloudconscious.com>
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
package org.jclouds.azure.storage.blob.config;

import org.jclouds.azure.storage.blob.AzureBlobAsyncClient;
import org.jclouds.azure.storage.blob.AzureBlobClient;
import org.jclouds.azure.storage.config.AzureStorageRestClientModule;
import org.jclouds.http.RequiresHttp;
import org.jclouds.rest.ConfiguresRestClient;

/**
 * Configures the Azure Blob Service connection, including logging and http transport.
 * 
 * @author Adrian Cole
 */
@ConfiguresRestClient
@RequiresHttp
public class AzureBlobRestClientModule extends
         AzureStorageRestClientModule<AzureBlobClient, AzureBlobAsyncClient> {

   public AzureBlobRestClientModule() {
      super(AzureBlobClient.class, AzureBlobAsyncClient.class);
   }

   @Override
   protected void configure() {
      install(new AzureBlobModule());
      super.configure();
   }

}