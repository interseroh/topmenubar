/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package de.interseroh.tmb.applauncher.server.controller;

import de.interseroh.tmb.applauncher.shared.json.TargetedApplication;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

/**
 * Test for testing ApplauncherConfiguration controller
 */
public class ApplauncherConfigurationTest {
    private static final int ITEMS_AMOUNT = 6;
    private static final String DEV_URL="http://www.google.de";
    private static final String PROD_URL="http://www.yahoo.de";
    private static final String QAT_URL="http://www.apple.com";

    @Test
    public void getDEVConfigurationTest() throws Exception{
        test(AppEnvironments.DEV,DEV_URL);
    }

    @Test
    public void getPRODConfigurationTest() throws Exception{
        test(AppEnvironments.PROD,PROD_URL);
    }

    @Test
    public void getQATConfigurationTest() throws Exception{
        test(AppEnvironments.QAT,QAT_URL);
    }

    private void test(AppEnvironments env, String checkUrl) throws Exception{
        System.setProperty(ApplauncherConfiguration.APP_LAUNCHER_ENV_KEY,env.name());
        ApplauncherConfiguration appConfig = new ApplauncherConfiguration();
        List<TargetedApplication> configuration = appConfig.getConfiguration();
        Assert.assertNotNull(configuration);
        Assert.assertEquals(ITEMS_AMOUNT,configuration.size());
        Assert.assertEquals(checkUrl,configuration.get(0).getApplicationURL());

    }

}
