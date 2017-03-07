node {
    def mvnHome
    stage('Preparation') {
        // Using scm configuration from upstream project.
        checkout scm
        // Get the Maven tool.
        // ** NOTE: This 'maven-default' Maven tool must be configured
        // **       in the global configuration.
        mvnHome = tool 'maven-default'
    }
    stage('Build') {
        // Run the maven build
        if (isUnix()) {
            sh "'${mvnHome}/bin/mvn' clean install -Pwith-docker"
        } else {
            bat(/"${mvnHome}\bin\mvn" clean install -Pwith-docker/)
        }
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
}