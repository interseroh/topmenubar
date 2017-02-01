<!doctype html>
<!-- The DOCTYPE declaration above will set the     -->
<!-- browser's rendering engine into                -->
<!-- "Standards Mode". Replacing this declaration   -->
<!-- with a "Quirks Mode" doctype is not supported. -->

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <!--                                                               -->
    <!-- Consider inlining CSS to reduce the number of requested files -->
    <!--                                                               -->

    <link type="text/css" rel="stylesheet" href="topmenubar.css">
    <link type="text/css" rel="stylesheet" href="http://localhost:9010/applauncher/applauncher.css">
    <link rel="icon" href="images/favicon.png" type="image/x-icon"/>

    <!--                                           -->
    <!-- Any title is fine                         -->
    <!--                                           -->
    <title>Tmb GWT Webapp</title>

    <!--                                           -->
    <!-- This script loads your compiled module.   -->
    <!-- If you add any GWT meta tags, they must   -->
    <!-- be added before this line.                -->
    <!--                                           -->
    <script type="text/javascript" language="javascript"
            src="topmenubar/topmenubar.nocache.js"></script>
    <script  type="text/javascript" language="javascript"
            src="http://<%=request.getRemoteHost()%>:9010/applauncher/applauncher.nocache.js"></script>
</head>

<!--                                           -->
<!-- The body can have arbitrary html, or      -->
<!-- you can leave the body empty if you want  -->
<!-- to create a completely dynamic UI.        -->
<!--                                           -->
<body>

<!-- OPTIONAL: include this if you want history support -->
<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
        style="position: absolute; width: 0; height: 0; border: 0"></iframe>

<!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
<noscript>
    <div
            style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
        Your web browser must have JavaScript enabled in order for this
        application to display correctly.
    </div>
</noscript>

<div id="tmb_top_menu_bar" data-colour="#223d62" data-headline="TEST APPLICATION" data-icon-url="images/entsorger-logo.png">
    <div id="tmb_app_launcher">
    </div>
    <div id="tmb_profile">
    </div>
    <div id="tmb_messaging">
    </div>
</div>

</body>
</html>
