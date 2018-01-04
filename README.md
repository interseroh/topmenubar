# topmenubar
Top Menu Bar for Interseroh Webapps.


## Build Status

[![Travis Build](https://travis-ci.org/interseroh/topmenubar.svg?branch=master)](https://travis-ci.org/interseroh/topmenubar)
[![Quality Gate](http://sonarqube.com/api/badges/gate?key=de.interseroh.tmb:topmenubar-parent)](https://sonarqube.com/dashboard?id=de.interseroh.tmb%3Atopmenubar-parent)
[![Dependency Status](https://www.versioneye.com/user/projects/58cc04666893fd003facbf7f/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/58cc04666893fd003facbf7f)
[![codecov](https://codecov.io/gh/interseroh/topmenubar/branch/master/graph/badge.svg)](https://codecov.io/gh/interseroh/topmenubar)
[![Heroku](https://heroku-badge.herokuapp.com/?app=topmenubar&root=/landing/index.html)](https://topmenubar.herokuapp.com/landing/index.html)


## Table of Contents
- [Introduction](#introduction)
- [Maven modules](#maven-modules)
  - [topmenubar-parent](#topmenubar-parent)
  - [applauncher](#applauncher)
    - [Configuration Properties](#configuration-properties)
    - [Configuration File](#configuration-file)
  - [messaging](#messaging)
  - [profile](#profile)
  - [topmenubar](#topmenubar)
- [Configuration](#configuration)
- [Integration of topmenubar and applauncher into your application](#integration-of-topmenubar-and-applauncher-into-your-application)
  - [Integrating TopMenuBar (incl. AppLauncher) above an existing Bootsrap Navbar](#integrating-topmenubar-incl-applauncher-above-an-existing-bootstrap-navbar)
  - [Integrating Applauncher (without TopMenuBar) within an existing Bootstrap Navbar](#integrating-applauncher-without-topmenubar-within-an-existing-bootstrap-navbar)
  - [Integrating TopMenuBar (incl. AppLauncher) with custom menu items](#integrating-topmenubar-incl-applauncher-with-custom-menu-items)
- [Integration Test](#integration-test)
  - [Requirements](#requirements)
  - [Test Case 1](#test-case-1)
  - [Test Case 2](#test-case-2)
  - [Test Case 3](#test-case-3)
- [Conclusion](#conclusion)
- [Interseroh-styles](#interseroh-styles) 
  - [LESS](#less) 
  - [Bootstrap Config](#Bootstrap-Config)
  - [variables less](#variables)
- [Dockerizing](#dockerizing)
 
- [Docker Container](#docker-container)
- [Interseroh Styles](#interseroh-styles) 


## Introduction
This application is a top menu bar for various Web apps. The project is represented by four micro services.
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
##### config file
It utilizes the `dev.applauncher.json` file by default for configuring the panel.
You can override it by setting the `applauncher.config.json` property.
 
Examples:
 
 `-Dapplauncher.config.json=classpath:prod.applauncher.json`
 `-Dapplauncher.config.json=file:/var/config/applauncher.json`
 
##### image folder
By default the images in src/main/resources/public/images will bes used.
You can override this value to set a custom image folder. Be aware that the
complete folder will be public accessible.
Note: The name of the image folder has to be closed with a slash.

Examples:

 `-Dapplauncher.images.location=classpath:public/images/`
 `-Dapplauncher.images.location=file:/var/public/images/`

#### Configuration File
For an example you can look at the existing development file. [dev.applauncher.json](applauncher/src/main/resources/dev.applauncher.json)

For each application in the applauncher you have one entry like the following.
You can add as much applications as you want to this configuration file.
```
{
        "imageURL": "images/google-logo.png",
        "caption": "Google",
        "applicationURL": "http://www.google.de"
}
```
- __imageURL__: The relative URL to the image that will be displayed in the applauncher.
- __caption__: The name of the application that will be displayed in the applauncher.
- __applicationURL__: The full URL to the application.

### messaging
This module represents the messaging facilities of Top Menu Bar.

### profile
The profile application handles user management and is currently availale in two flavours:

* mocked service (just for simulation purpose)
* life service accessing a user /userinfo rest service returning full name and email address

#### Enabling

One can enable the profile application by building the application with -Pwith-profile option. Notice, if you choose 
this option, you need to decide whether to build with -Pwith-usermgt or with -Pwith-usermgt-mock.

#### Configuration

The service is configured externally by setting the attributes:

Example:

	<div id="tmb_profile" 
	    class="fa fa-user headertabs" 
	    data-tmb-javascript-url="/profile/profile.nocache.js" 
	    data-tmb-sso-url="http://localhost:9000/ep/openid_connect_login?identifier=http%3A%2F%2Flocalhost%3A8080%2Fopenid-connect-server-webapp%2F" 
	    data-tmb-application-url="http://localhost:9012/profile" 
	    data-tmb-user-info="http://localhost:9000/ep/"
	    data-tmb-cookie-path="/ep"
	    data-tmb-logout-url="http://www.google.de">

* data-tmb-sso-url - the URL where to redirect if no session is found
* data-tmb-user-info - the URL where the /userinfo REST service is found. This service should be capable to be called 
without any parameters determining the user by the JSESSIONID cookie.
* data-tmb-cookie-path - the path, under which the JSESSIONID cookie will be set by the authenticating system
* data-tmb-logout-url - an url to navigate, after the logout has been processed

A simple integration sample is currently available in templates/topmenubar-above-navbar.html of the landing page demo app.

### topmenubar
This module contains the graphical representation of Top Menu Bar plus a prototype landing page


## Configuration
In a production environment you should define the location of the configuration file
and image folder. The configuration file and image folder in the sources are just used for
development purpose. You do not want to commit your configuration and images to a
public version control repository like GitHub.

See [applauncher](#applauncher) for configuration properties and configuration file.


## Integration of TopMenuBar and AppLauncher into your application

### Requirements
- You must know the URLs where topmenubar and applauncher are deployed.

### Integrating TopMenuBar (incl. AppLauncher) above an existing Bootstrap Navbar
For a complete example see [topmenubar-above-navbar.html](tmb-demo/src/main/resources/templates/topmenubar-above-navbar.html).
- Add javascript header.
```html

<script type="text/javascript" language="javascript"
    src="${topmenubarUrl}/topmenubar/topmenubar.nocache.js"></script>
```

- Add div tag for topmenubar at the top of the html body.
```html
<div id="tmb_top_menu_bar"
    data-tmb-headline="TEST APPLICATION"
    data-tmb-icon-url="${topmenubarUrl}/images/logo.svg">
<div id="tmb_app_launcher"
    data-tmb-application-url="${applauncherUrl}"
    data-tmb-javascript-url="/applauncher/applauncher.nocache.js">
</div>
<div id="tmb_portal_links"></div>
<div id="tmb_icons_right"></div>
<div id="tmb_profile" class="fa fa-user headertabs"></div>
<div id="tmb_messaging" class="fa fa-envelope-o headertabs"></div>
</div>
```

#### Configuring TopMenuBar above an existing Bootsrap Navbar
- Changing the name and image of the application displayed in the TopMenuBar and the background color of the TopMenuBar:
Just change the attributes in the outer div tag of the TopMenuBar.
```html
<div id="tmb_top_menu_bar" 
    data-tmb-bgcolor="#223d62"
    data-tmb-headline="TEST APPLICATION"
    data-tmb-icon-url="${topmenubarUrl}/images/logo.svg">
```

### Integrating Applauncher (without TopMenuBar) within an existing Bootstrap Navbar
For a complete example see [applauncher-within-navbar.html](tmb-demo/src/main/resources/templates/applauncher-within-navbar.html).
- Add javascript header.
```html
<script type="text/javascript" language="javascript"
    src="${applauncherUrl}/applauncher/applauncher.nocache.js"></script>
```

- Add div tag to your existing Navbar.
```html
<div id="tmb_app_launcher"
    data-tmb-application-url="${applauncherUrl}"
    data-tmb-javascript-url="/applauncher/applauncher.nocache.js">
</div>
```

### Integrating TopMenuBar (incl. AppLauncher) with custom menu items
For a complete example see [topmenubar-with-portalitems.html](tmb-demo/src/main/resources/templates/topmenubar-with-portalitems.html).
- Add javascript header.
```html

<script type="text/javascript" language="javascript"
    src="${topmenubarUrl}/topmenubar/topmenubar.nocache.js"></script>

```

- Add div tag for topmenubar at the top of the html body.
```html
<div id="tmb_top_menu_bar" data-tmb-bgcolor="#223d62"
    data-tmb-headline="TEST APPLICATION"
    data-tmb-icon-url="${topmenubarUrl}/images/logo.svg">
<div id="tmb_app_launcher"
    data-tmb-application-url="${applauncherUrl}"
    data-tmb-javascript-url="/applauncher/applauncher.nocache.js">
</div>
<div id="tmb_portal_links"></div>
<div id="tmb_icons_right"></div>
<div id="tmb_profile" class="fa fa-user headertabs"></div>
<div id="tmb_messaging" class="fa fa-envelope-o headertabs"></div>
</div>
```

- Add an unordered HTML list with id "internal_topics" into the html body to define your custom links in the TopMenuBar.
```html
<ul id="internal_topics">
    <li>
        <a href="https://www.interseroh.de" target="_blank">Home</a>
    </li>
    <li>
        <a href="https://www.interseroh.de/en/company/about-us-interseroh" target="_blank">About</a>
    </li>
    <li>
        <a href="https://www.interseroh.de/en/contact" target="_blank">Contact</a>
    </li>
</ul>
```

#### Configuring TopMenuBar with custom menu items
- Alignment of menu items if menu collapses: Just change the css class of the unordered list with the menu items.
    - right: `<ul id="internal_topics" class="text-right">`
    - left:  `<ul id="internal_topics">`
- Changing the name and image of the application displayed in the TopMenuBar and the background color of the TopMenuBar:
Just change the attributes in the outer div tag of the TopMenuBar.
```html
<div id="tmb_top_menu_bar" 
    data-tmb-bgcolor="#223d62"
    data-tmb-headline="TEST APPLICATION"
    data-tmb-icon-url="${topmenubarUrl}/images/logo.svg">
```

## Customizing the design
### Themes
Add a predefined theme to topmenubar by setting the attribute `data-tmb-theme`.
You can set one of the following values to this attribute.
- default (or attribute not set): *background-color: "#f8f8f8", text-color="#777777"*
- darkblue:  *background-color: "#223d62", text-color="white"*
- white:  *background-color: "white", text-color="#777777"*
```html
<div id="tmb_top_menu_bar"
    data-tmb-headline="TEST APPLICATION"
    data-tmb-icon-url="${topmenubarUrl}/images/logo.svg"
    data-tmb-theme="darkblue">
<div id="tmb_app_launcher"
    data-tmb-application-url="${applauncherUrl}"
    data-tmb-javascript-url="/applauncher/applauncher.nocache.js">
</div>
<div id="tmb_portal_links"></div>
<div id="tmb_icons_right"></div>
<div id="tmb_profile" class="fa fa-user headertabs"></div>
<div id="tmb_messaging" class="fa fa-envelope-o headertabs"></div>
</div>
```

### Colors
Add your own background color and text color to topmenubar by setting the attributes
`data-tmb-bgcolor` and `data-tmb-txtcolor`.
- `data-tmb-bgcolor` and `data-tmb-txtcolor` will overwrite the used theme except own styled background for the logo
- it's better not to combine own backgroundcolor with theme "darkblue"
- means you can overwrite text color and background color independently except logo-backgrundcolor is set by the theme
- then now you have to set `data-tmb-txtcolor` as well otherwise its default color
```html
<div id="tmb_top_menu_bar"
    data-tmb-headline="TEST APPLICATION"
    data-tmb-icon-url="${topmenubarUrl}/images/logo.svg"
    data-tmb-bgcolor="#0000FF" 
    data-tmb-txtcolor="#00FF00" >
<div id="tmb_app_launcher"
    data-tmb-application-url="${applauncherUrl}"
    data-tmb-javascript-url="/applauncher/applauncher.nocache.js">
</div>
<div id="tmb_portal_links"></div>
<div id="tmb_icons_right"></div>
<div id="tmb_profile" class="fa fa-user headertabs"></div>
<div id="tmb_messaging" class="fa fa-envelope-o headertabs"></div>
</div>
```


## Integration Test
The integration test simulates the injection of TopMenuBar in an existing page containing Bootstrap components and been developed applying the principles of responsive design.
 
### Requirements 
The original page has been built using the following Bootstrap components:
- Navigation Bar
- Modal dialog which can be activated by button 
- Tables which have been located standalone in HTML page and built in another container
- Different Bootstrap containers with row and columns containing short texts and buttons (which redirect to pages with details).
    
### Test Case 1
This test is represented with the page `resources/public/bstest_1_original.html`.
   
#### Test Procedure 1 
- Add next JavaScripts to html header.

```html
<script type="text/javascript" language="javascript"
    src="topmenubar/topmenubar.nocache.js"></script>
<script type="text/javascript" language="javascript"
    src="http://localhost:9010/applauncher/applauncher/applauncher.nocache.js">
</script>

```
    
- Add TopMenuBar at the top of the html body.
       
```html
<div id="tmb_top_menu_bar" data-color="#223d62"
    data-headline="TEST APPLICATION" data-icon-url="images/logo.svg">
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
This test case is co-represented with the page **resources/public/bst``est_1_original.html** as test case 1.
   
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
- Check existing class `navbar-fixed-top` of the attribute **class** of the tag `nav`.
- If the class `navbar-fixed-top` exists than remove this class as in the **Test case 2**
- Removes Bootstrap css and JavaScripts from the original page as in the **Test case 3**

   
      
# Docker Container 
This project is using the fabric8.io docker plugin for maven and enables the developer to create docker images in zero time. 

## Requirements
Besides the necessary maven environment, a working docker container build environment is mandatory (this means, you must be able to run docker build commands successfully). 

## Building the images
All you have to do is enabling the with-docker-profile. The build command for building the image is fired through maven:
   
```
mvn clean install -Pwith-docker -Ddocker.registry=<DOCKER_REGISTRY_URL> \
-Ddocker.username=<DOCKER_REGISTRY_USERNAME> \
-Ddocker.password=<DOCKER_REGISTRY_PASSWORD>
```

_Please verify that the plugin version in the command line matches the plugin version in the parent pom.xml!_
      
As a result of this build you will find an image carrying the build version in the local docker registry:
   
```
    $ docker images
   
    REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
    topmenubar          1.0.0-SNAPSHOT      0677b5eb24a8        3 minutes ago       767 MB
    topmenubar          latest              0677b5eb24a8        3 minutes ago       767 MB
    applauncher         1.0.0-SNAPSHOT      41183726d7a9        3 minutes ago       752 MB
    applauncher         latest              41183726d7a9        3 minutes ago       752 MB

```

Hint: You may call the maven build with -Pwith-docker settings 
and setting all required parameters manually, or you choose jenkins2, setting up a new build pipeline and execute the Jenkinsfile.

![Jenkins configuration](https://github.com/interseroh/topmenubar/etc/jenkins.jpg)

The build will produces a docker image for each service.

## Interseroh-styles  

# Interseroh Styles  

## LESS
Use less to generate css files.
   
http://lesscss.org
   
If you change something in the css file and the deployment is started, the changes 
will be overwritten by the less-files.
   
## Bootstrap Config
Bootstrap is configured by http://getBootstrap.com
   
To change style import the config-file "config.json" from folder "config" before 
to inculde all changes generated until now

All styles are now generated by the original http://www.getBootstrap.com 
no more from http://www.lavishbootstrap.com.
   
## Variables
Colors are defined in the config.less and imported by the _interseroh.less_ and _individual.less_.

## Buttons with Interseroh Style (BIG)
If you want to get Buttons with interseroh style with the dimensions from the styleguide, add class "interseroh-style" to the element.
