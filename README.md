# topmenubar
Top Menu Bar for Interseroh Webapps.


## Build Status

[![Travis Build](https://travis-ci.org/interseroh/topmenubar.svg?branch=master)](https://travis-ci.org/interseroh/topmenubar)
[![Quality Gate](http://sonarqube.com/api/badges/gate?key=de.interseroh.tmb:topmenubar-parent)](https://sonarqube.com/dashboard?id=de.interseroh.tmb%3Atopmenubar-parent)
[![codecov](https://codecov.io/gh/interseroh/topmenubar/branch/master/graph/badge.svg)](https://codecov.io/gh/interseroh/topmenubar)
[![Heroku](https://heroku-badge.herokuapp.com/?app=topmenubar&root=/topmenubar/topmenubar.html)](https://topmenubar.herokuapp.com/topmenubar/topmenubar.html)


## Table of Contents
- [Introduction](#introduction)
- [Maven modules](#maven-modules)
  - [topmenubar-parent](#topmenubar-parent)
  - [applauncher](#applauncher)
  - [messaging](#messaging)
  - [profile](#profile)
  - [topmenubar](#topmenubar)
- [Integration Test](#integration-test)
  - [Requirements](#requirements)
  - [Test Case 1](#test-case-1)
  - [Test Case 2](#test-case-2)
  - [Test Case 3](#test-case-3)
- [Conclusion](#conclusion) 


## Introduction
This application is a top menu bar for various Webapps. The project is represented by four micro services.
The UI (User Interface) of each microservice is based on GWT framework (http://www.gwtproject.org) and a matching
backend basing on Spring boot framework (https://projects.spring.io/spring-boot).


## Maven Modules
Each microservice is represented with one maven module under topmenubar-parent module.
There are following modules:

- topmenubar-parent
  - applauncher
  - messaging
  - profile
  - topmenubar

### topmenubar-parent
This is the parent module with pom packaging. The pom.xml file of this module contains the dependencies, a plugins management, and profiles for the rest of modules.
 
The plugins are parametrized with three placeholders:

- `${i18n.gwt.module} (Reference to configuration of the main GWT module)
- `${gwt.dev.module}` (Reference to the configuration of development GWT module)
- `${i18n.messages.bundle}` (Reference to the configuration of development GWT module)

The values for these placeholders are provided by properties in pom.xml of each child module.
For example for the module `#topmenubar` that are:
- `i18n.gwt.module = de.interseroh.tmb.TopMenuBar` (In the application that is de.interseroh.tmb.TopMenuBar.gwt.xml)
- `gwt.dev.module = de.interseroh.tmb.TopMenuBarDevelopment` (In the application that is `de.interseroh.tmb.TopMenuBarDevelopment.gwt.xml`)
- `i18n.messages.bundle = de.interseroh.tmb.client.Messages` (In the application that is `de.interseroh.tmb.client.Messages.properties`)

### applauncher
This module represents the application launcher panel of the Top Menu Bar.
 
#### Configuration Properties
It utilizes the dev.applauncher.json file by default for configuring the panel.
You can override it by setting the applauncher.config.json property.
 
Example:
 
 `-Dapplauncher.config.json=classpath:prod.applauncher.json`

### messaging
This module represents the messaging facilities of Top Menu Bar.

### profile
to be defined.

### topmenubar
This module contains the graphical representation of Top Menu Bar plus a prototype landing page


## Integration Test
The integration test simulates the injection of TopMenuBar in an existing page containing Bootstrap components and been developed applying the principles of responsive design.
 
### Requirements 
The original page has been built using the following Bootstrap components:
- Navigation Bar
- Modal dialog which can be activated by button 
- Tables which have been located standalone in HTML page and built in another container
- Different Bootstrap containers with row and columns containing short texts and buttons (which redirect to pages with details).
    
### Test Case 1
This test is represented with the page **resources/public/bstest_1_original.html**.
   
#### Test Procedure 1 
- Add next JavaScripts to html header.

```html
<script type="text/javascript" language="javascript"
    src="topmenubar/topmenubar.nocache.js"></script>
<script type="text/javascript" language="javascript"
    src="http://localhost:9010/applauncher/applauncher/applauncher.nocache.js">
</script>
```

- Add applauncher css the html header
       
```html
<link type="text/css" rel="stylesheet" href="http://localhost:9010/applauncher/applauncher.css">
```
    
- Add TopMenuBar at the top of the html body.
       
```html
<div id="tmb_top_menu_bar" data-color="#223d62"
    data-headline="TEST APPLICATION" data-icon-url="images/entsorger-logo.png">
    <div id="tmb_app_launcher" data-application-url="http://localhost:9010/"
        data-javascript-url="applauncher/applauncher/applauncher.nocache.js">
    </div>
    <div id="tmb_profile"></div>
    <div id="tmb_messaging"></div>
</div>
```
      
#### Result of the Test Case 1
This is the simplest way of integration of ToMenuBar without influencing the original html content (aside header).
As a result there is a problem with **invisibility of TopMenuBar**. 
The problem is the class **navbar-fixed-top** which is located inside of original navigation bar
        
        
### Test Case 2
This test case is co-represented with the page **resources/public/bstest_1_original.html** as test case 1.
   
#### Test Procedure 2
Additionally to the same steps as in (#Test case 1) we need to perform one more additional step.
       
- Remove class **navbar-fixed-top**
We should remove the class **navbar-fixed-top** from the attribute class of the tag **nav**.
         
#### Result of the Test Case 2
In this case we achieve a better result. The TopMenuBar is visible on the top of page and page's original menu bar is located under TopMenuBar. 

The problem here is that the **TopMenuBar lost the color**, which was defined by parameter data-color.
   
### Test Case 3
This test case is represented  with the page **resources/public/bstest_1_solution.html**.
That is possible solution of integration for TopMenuBar.
   
#### Test Procedure 3
Additionally to the same steps as in (#Test case 1) and (#Test case 2) it should be doing one additional step more.

- Remove Bootstrap css and JavaScript from original page.

```html
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
    integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" 
    integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous">
</script>
```

#### Result of the Test Case 3
      
## Conclusion   
Proceeding for the successful integration of the **TopMenuBar** in the pages which contain Bootstrap navigation bar.
- Check existing class **navbar-fixed-top** of the attribute **class** of the tag **nav**.
- If the class **navbar-fixed-top** exists than remove this class as in the **Test case 2**
- Removes Bootstrap css and JavaScripts from the original page as in the **Test case 3**
   