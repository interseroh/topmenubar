#!/bin/bash

env

if [ "$SERVICE_NAME" == "topmenubar" ]; then
    java $JAVA_OPTS -jar $SERVICE_NAME/target/$SERVICE_NAME*.jar --server.port=$PORT --applauncher.url=https://tmb-applauncher.herokuapp.com/applauncher
else
    java $JAVA_OPTS -jar $SERVICE_NAME/target/$SERVICE_NAME*.jar --server.port=$PORT
fi