# topmenubar
Top Menu Bar for Interseroh Webapps.

## Build Status

[![Travis Build](https://travis-ci.org/interseroh/topmenubar.svg?branch=master)](https://travis-ci.org/interseroh/topmenubar)
[![codecov](https://codecov.io/gh/interseroh/topmenubar/branch/master/graph/badge.svg)](https://codecov.io/gh/interseroh/topmenubar)
[![Heroku](https://heroku-badge.herokuapp.com/?app=topmenubar&root=/topmenubar/topmenubar.html)](https://topmenubar.herokuapp.com/topmenubar/topmenubar.html)


## Table of Contents
- [Introduction](#introduction)
- [Maven modules](#maven-modules)
  - [topmenubar-parent](#topmnuebar-parent)
  - [applauncher](#applauncher)
  - [messaging](#messaging)
  - [profile](#profile)
  - [topmenubar](#topmenubar)

##Introduction
This application is a top menu bar for various Webapps. The project is represented by four microservices.
The UI(User Interface) of each microservice is based on GWT framework(http://www.gwtproject.org/) and a matching
backend basing on Spring boot framework (https://projects.spring.io/spring-boot/).

## Maven modules
Each microservice is represented with one maven modul under topmenubar-parent modul.
There is the next maven modules structure:

 - topmenubar-parent
   - applauncher
   - messaging
   - profile
   - topmenubar

 ### topmenuebar-parent
 That is a parent module with pom packaging. The pom.xml file of this module contains the dependencies, a plug ins management, and profiles for the rest of modules.
 The plugins are parametriesied with three placeholders:

 - ${i18n.gwt.modul} (Reference to configuration of the main GWT modul )
 - ${gwt.dev.modul} (Reference to the configuration of development GWT modul )
 - ${i18n.messages.bundle} (Reference to the configuration of development GWT modul )

 The values for these placeholders are provided by properties in pom.xml of each child module.
 For example for the modul #topmenubar that are:
  - i18n.gwt.modul = de.interseroh.tmb.TopMenuBar (In the application that is de.interseroh.tmb.TopMenuBar.gwt.xml)
  - gwt.dev.modul = de.interseroh.tmb.TopMenuBarDevelopment (In the application that is de.interseroh.tmb.TopMenuBarDevelopment.gwt.xml)
  - i18n.messages.bundle = de.interseroh.tmb.client.Messages (In the application that is de.interseroh.tmb.client.Messages.properties)


 ### applauncher
 This module respresents the application launcher panel of the Top Menu Bar.
 
 #### Configuration Properties
 It utilizes the dev.applauncher.json file by default for configuring the panel.
 You can override it by setting the applauncher.config.json property.
 
 Example:
 
 `-Dapplauncher.config.json=classpath:prod.applauncher.json`

 ### messaging
 This module respresents the messaging facilities of Top Menu Bar.

 ### topmenubar
 This module contains the graphical representation of Top Menu Bar plus a prototype landing page
