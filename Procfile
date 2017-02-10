web: java $JAVA_OPTS -jar zuul-server/target/zuul-server*.jar --server.port=$PORT
info: heroku-settings/info.sh
topmenubar: java $JAVA_OPTS -jar topmenubar/target/topmenubar*.jar --server.port=9014 --applauncher.url=https://$HEROKU_APP_NAME.herokuapps.com/applauncher
applauncher: java $JAVA_OPTS -jar applauncher/target/applauncher*.jar --server.port=9010