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

package org.jclouds.nirvanix.sdn;

import java.util.Properties;

import org.jclouds.Constants;
import org.jclouds.PropertiesBuilder;

/**
 * Builds properties used in SDN Clients
 * 
 * @author Adrian Cole
 */
public class SDNPropertiesBuilder extends PropertiesBuilder {

   @Override
   protected Properties defaultProperties() {
      Properties properties = super.defaultProperties();
      properties.setProperty(Constants.PROPERTY_API_VERSION, "2.5.6");
      properties.setProperty(Constants.PROPERTY_ENDPOINT, "http://services.nirvanix.com");
      return properties;
   }

   public SDNPropertiesBuilder(Properties properties) {
      super(properties);
   }

}
