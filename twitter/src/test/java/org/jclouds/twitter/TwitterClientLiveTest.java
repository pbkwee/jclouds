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
package org.jclouds.twitter;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.rest.RestContextFactory.contextSpec;
import static org.jclouds.rest.RestContextFactory.createContext;

import java.util.SortedSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.jclouds.rest.RestContext;
import org.jclouds.twitter.domain.Status;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

/**
 * Tests behavior of {@code TwitterClient}
 * 
 * @author Adrian Cole
 */
@Test(groups = "live", testName = "twitter.TwitterClientLiveTest")
public class TwitterClientLiveTest {

   private TwitterClient connection;
   private RestContext<TwitterClient, TwitterAsyncClient> context;

   @BeforeGroups(groups = "live")
   public void setupClient() throws InterruptedException, ExecutionException, TimeoutException {
      String user = checkNotNull(System.getProperty("jclouds.test.user"), "jclouds.test.user");
      String password = checkNotNull(System.getProperty("jclouds.test.key"), "jclouds.test.key");

      context = createContext(contextSpec("twitter", "http://twitter.com", "1", user, password,
               TwitterClient.class, TwitterAsyncClient.class));

      connection = context.getApi();
   }

   @Test
   public void testGetMyMentions() throws Exception {
      SortedSet<Status> response = connection.getMyMentions();
      assert (response.size() > 0);
   }

   @AfterGroups(groups = "live")
   void tearDown() {
      if (context != null)
         context.close();
   }

}
