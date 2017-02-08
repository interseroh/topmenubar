#!/bin/bash

env

if [ "$SERVICE_NAME" == "topmenubar" ]; then

   PR_NO=`echo $HEROKU_APP_NAME | sed "s/[a-zA-Z\\-]//g"`
   echo "PullRequest: $PR_NO"

    if [ -n "$PR_NO" ]; then
        PR_SUFFIX="-pr-$PR_NO"
    else
        PR_SUFFIX=""
    fi

    APPLAUNCHER_URL="https://tmb-applauncher$PR_SUFFIX.herokuapp.com/applauncher"

    echo $APPLAUNCHER_URL

   java $JAVA_OPTS -jar $SERVICE_NAME/target/$SERVICE_NAME*.jar --server.port=$PORT --applauncher.url=APPLAUNCHER_URL

else

   java $JAVA_OPTS -jar $SERVICE_NAME/target/$SERVICE_NAME*.jar --server.port=$PORT
fi