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
package org.jclouds.aws.s3;

import org.jclouds.http.httpnio.config.HttpNioConnectionPoolClientModule;
import org.testng.annotations.Test;

import com.google.inject.Module;

@Test(sequential = true, testName = "s3.JCloudsNioPerformanceLiveTest", groups = {"live"})
public class JCloudsNioPerformanceLiveTest extends BaseJCloudsPerformance {

    @Override
    protected S3ContextFactory buildS3ContextFactory(String AWSAccessKeyId,
                                           String AWSSecretAccessKey) {
        return super.buildS3ContextFactory(AWSAccessKeyId, AWSSecretAccessKey)
                  .withPoolMaxConnectionReuse(75)
                  .withPoolMaxSessionFailures(2)
                  .withPoolRequestInvokerThreads(1)
                  .withPoolIoWorkerThreads(2)
                  .withPoolMaxConnections(12);
    }

    @Override
    protected Module createHttpModule() {
        return new HttpNioConnectionPoolClientModule();
    }
}