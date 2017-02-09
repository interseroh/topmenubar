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
- [Integration test]
  - [Given](#Given)
  
  - [Test case 1](#Test case 1)
    - [Have been done](#Have been done 1)
    - [Result of the Test case 1](#Result of the Test case 1)
  - [Test case 2](#Test case 2)
    - [Have been done](#Have been done 2)
    - [Result of the Test case 2](#Result of the Test case 2)
  - [Test case 3](#Test case 3)
    - [Have been done](#Have been done 3)
    - [Result of the Test case 3](#Result of the Test case 3)
  -[Conclusion](#Conclusion) 

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

 ### messaging
 This module respresents the messaging facilities of Top Menu Bar.

 ### topmenubar
 This module contains the graphical representation of Top Menu Bar plus a prototype landing page

##Integration test
The integration test simulates the injection of TomMenuBar in some existed page
 with Bootstrap components and responsive design.
 
   ### Given 
   The original page where have been built in the next Bootstrap components:
      - Navigation Bar
      - Modal dialog which can be activated by button 
      - Tables which have been located standalone in HTML page and built in other container
      - different Bootstrap containers with row and columns where are located short texts and 
        buttons which redirect to pages with details.
    
   ### Test case 1
   This test is presented with the page **resources/public/bstest_1_original.html**.
   
   - Have been done 1.
        - add next JavaScripts to html header.
           ```html
            <script type="text/javascript" language="javascript"
                       src="topmenubar/topmenubar.nocache.js"></script>
            <script type="text/javascript" language="javascript"
                       src="http://localhost:9010/applauncher/applauncher/applauncher.nocache.js">
            </script>
            ```
        - add applauncher css the html header
       
            ```html
            <link type="text/css" rel="stylesheet" href="http://localhost:9010/applauncher/applauncher.css">
            ```
    
        - add TopMenuBar at the top of the html body.
       
            ```html
            <div id="tmb_top_menu_bar" data-colour="#223d62"
                 data-headline="TEST APPLICATION" data-icon-url="images/entsorger-logo.png">
                <div id="tmb_app_launcher" data-application-url="http://localhost:9010/"
                     data-javascript-url="applauncher/applauncher/applauncher.nocache.js">
                </div>
                <div id="tmb_profile">
                </div>
                <div id="tmb_messaging">
                </div>
            </div>
        ```
      
   - Result of the Test case 1
        That is the simplest way for the integration of ToMenuBar where was not changed the original html
        content (aside header).
        As a result we have here the  problem with **invisibility of TopMenuBar**. 
        The problem is the class **navbar-fixed-top** which ist located in original navigation bar
        
  ### Test case 2
  
   This test case is presented also with the page **resources/public/bstest_1_original.html** as test case 1.
   
   - Have been done 2.
       Additionally to the same steps as in (#Test case 1) it should be doing one additional step more.
       - remove class **navbar-fixed-top**
         Here should be removed the class **navbar-fixed-top** from the attribute class of the tag **nav**.
         
   - Result of the Test case 2
   In this case the result is better. The ToMenuBar is visible on the top of page and menu bar from original page 
   is located under TopMenuBar.
   The problem here is that the **TopMenuBar lost the color**, which was defined by parameter data-color.
   
   ### Test case 3
   This test case is presented  with the page **resources/public/bstest_1_solution.html**.
   That is possible solution of integration for TopMenuBar.
   
   - Have been done 2.
    Additionally to the same steps as in (#Test case 1) and (#Test case 2) it should be doing one additional step more.
      - remove Bootstrat css and JavaScript from original page.
      ```html
          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
          <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
          <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
      ```
   - Result of the Test case 3.
      
   ### Conclusion   
   What should be done for the successful integration of the **TopMenuBar** in the pages which contains
    Bootstrap navigation bar.
    - check existing class **navbar-fixed-top** of the attribute **class** of the tag **nav**.
    - if the class **navbar-fixed-top** exists than remove this class as in the **Test case 2**
    - removes Bootstrat css and JavaScripts from the original page as in the **Test case 3**
   
      