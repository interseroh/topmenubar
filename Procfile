web: java $JAVA_OPTS -jar tmb-demo/target/tmb-demo*.jar --server.port=$PORT --spring.profiles.active=heroku
topmenubar: java $JAVA_OPTS -jar topmenubar/target/topmenubar*.jar --server.port=9014 --applauncher.url=https://$HEROKU_APP_NAME.herokuapps.com/applauncher
applauncher: java $JAVA_OPTS -jar applauncher/target/applauncher*.jar --server.port=9010
info: heroku-settings/info.sh