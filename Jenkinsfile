node {
    def mvnHome
    stage('Preparation') { // for display purposes
        // Get some code from a GitHub repository
        git branch: 'feature/TPI-16-dockertest', url: 'https://github.com/interseroh/topmenubar.git'
        // Get the Maven tool.
        // ** NOTE: This 'M3' Maven tool must be configured
        // **       in the global configuration.
        mvnHome = tool 'maven-default'
    }
    stage('Build') {
        // Run the maven build
        if (isUnix()) {
            sh "'${mvnHome}/bin/mvn' clean install io.fabric8:docker-maven-plugin:0.19.1:build -Pwith-docker"
        } else {
            bat(/"${mvnHome}\bin\mvn" clean package/)
        }
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
}