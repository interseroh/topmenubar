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

import com.fasterxml.jackson.databind.ObjectMapper;
import de.interseroh.tmb.applauncher.shared.ApplauncherServiceEndpoint;
import de.interseroh.tmb.applauncher.shared.json.AppProperty;
import de.interseroh.tmb.applauncher.shared.json.ApplauncherProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alexadmin on 01.02.2017.
 */
@Controller
@CrossOrigin
public class ApplauncherConfiguration {
    private static final Logger logger = Logger.getLogger(ApplauncherConfiguration.class.getName());


    public ApplauncherConfiguration(){
    }

    @RequestMapping(value = ApplauncherServiceEndpoint.APPLAUNCHER_CONFIG, method = RequestMethod.GET)
    public @ResponseBody
    List<AppProperty> getConfiguration(){
        String env = System.getProperty("ApplauncherEnv");
        if(env==null){
            env =AppEnvironments.DEV.name();
        }

        AppEnvironments ENV = AppEnvironments.valueOf(env);

        ObjectMapper mapper = new ObjectMapper();
        try( InputStream jsonIs = ApplauncherConfiguration.class.getResourceAsStream("/"+ENV.getConfigurationFile())) {
            ApplauncherProperties appPropes = mapper.readValue(jsonIs, ApplauncherProperties.class);
            List<AppProperty> listAppProps = appPropes.getApplauncherProperties().getAppProperty();
            jsonIs.close();
            return listAppProps;
        }catch(IOException ex){
            logger.severe(ex.getMessage());
            throw new RuntimeException(ex);
        }catch(Exception ex2){
            logger.severe("Unexpected Exception");
            throw new RuntimeException(ex2);
        }
    }
}
