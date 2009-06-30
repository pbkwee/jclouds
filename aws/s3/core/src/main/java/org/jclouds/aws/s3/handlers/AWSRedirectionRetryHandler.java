/**
 *
 * Copyright (C) 2009 Global Cloud Specialists, Inc. <info@globalcloudspecialists.com>
 *
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 */
package org.jclouds.aws.s3.handlers;

import java.net.URI;

import org.jclouds.aws.domain.AWSError;
import org.jclouds.aws.s3.reference.S3Constants;
import org.jclouds.aws.s3.util.S3Utils;
import org.jclouds.aws.s3.xml.S3ParserFactory;
import org.jclouds.http.HttpException;
import org.jclouds.http.HttpFutureCommand;
import org.jclouds.http.HttpHeaders;
import org.jclouds.http.HttpMethod;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.handlers.RedirectionRetryHandler;
import org.jclouds.util.Utils;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Handles Retryable responses with error codes in the 3xx range
 * 
 * @author Adrian Cole
 */
public class AWSRedirectionRetryHandler extends RedirectionRetryHandler {
   private final S3ParserFactory parserFactory;

   @Inject
   public AWSRedirectionRetryHandler(S3ParserFactory parserFactory,
            @Named("jclouds.http.max-redirects") int retryCountLimit) {
      super(retryCountLimit);
      this.parserFactory = parserFactory;
   }

   public boolean shouldRetryRequest(HttpFutureCommand<?> command, HttpResponse response) {
      if (response.getStatusCode() == 301 || response.getStatusCode() == 307) {
         byte[] content = S3Utils.closeConnectionButKeepContentStream(response);
         if (command.getRequest().getMethod() == HttpMethod.HEAD) {
            command.getRequest().setMethod(HttpMethod.GET);
            return true;
         } else {
            command.incrementRedirectCount();
            try {
               AWSError error = S3Utils.parseAWSErrorFromContent(parserFactory, command, response,
                        new String(content));
               String host = error.getDetails().get(S3Constants.ENDPOINT);
               if (host != null) {
                  if (host.equals(command.getRequest().getEndPoint().getHost())) {
                     // must be an amazon error related to
                     // http://developer.amazonwebservices.com/connect/thread.jspa?messageID=72287&#72287
                     try {
                        command.incrementFailureCount();
                        Thread.sleep(100);
                        return true;
                     } catch (InterruptedException e) {
                        command.setException(e);
                        return false;
                     }
                  } else {
                     URI endPoint = command.getRequest().getEndPoint();
                     endPoint = Utils.replaceHostInEndPoint(endPoint, host);
                     command.getRequest().setEndPoint(endPoint);
                     command.getRequest().getHeaders().removeAll(HttpHeaders.HOST);
                  }
                  return true;
               } else {
                  return false;
               }
            } catch (HttpException e) {
               return false;
            }
         }
      } else {
         return super.shouldRetryRequest(command, response);
      }
   }
}