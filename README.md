# topmenubar
Top Menu Bar for Interseroh Webapps.

## Build Status

[![Travis Build](https://travis-ci.org/interseroh/topmenubar.svg?branch=master)](https://travis-ci.org/interseroh/topmenubar)
[![codecov](https://codecov.io/gh/interseroh/topmenubar/branch/master/graph/badge.svg)(https://codecov.io/gh/interseroh/topmenubar)


## Table of Contents
- [Introduction](#introduction)
- [Maven modules](#maven-modules)
  - [topmenubar-parent](#topmnuebar-parent)
  - [applauncher](#applauncher)
  - [messaging](#messaging)
  - [profile](#profile)
  - [topmenubar](#topmenubar)

##Introduction
This application is a top menu bar for Interseroh Webapps. The project represents four microservices.
The UI(User Interface) of each microservice is based on GWT framework(http://www.gwtproject.org/) and server part on
Spring boot framework (https://projects.spring.io/spring-boot/).

## Maven modules
Each microservice is represented with one maven modul under topmenubar-parent modul.
There is the next maven modules structure:

 - topmenubar-parent
   - applauncher
   - messaging
   - profile
   - topmenubar
 
 ### topmenuebar-parent
 That is a parent modul with pom packaging. The pom.xml file of this module contains the depenecies an plugings management and profiles for the rest of modules.
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
 That modul respresents "Application launcher" of Top Menu Bar
 
 ### messaging
 That modul respresents "Messagung" of Top Menu Bar
 
 ### topmenubar
  That modul respresents "Messagung" of Top Menu Bar
 
 

