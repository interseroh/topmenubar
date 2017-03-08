node {
    def mvnHome
    stage('Preparation') {
        // Using scm configuration from upstream project.
        checkout scm
        //Setting GIT_BRANCH in environment because jenkins does not set it in the current version.
        env.GIT_BRANCH = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
        println "BRANCH: $GIT_BRANCH"

        // Get the Maven tool.
        // ** NOTE: This 'maven-default' Maven tool must be configured
        // **       in the global configuration.
        mvnHome = tool 'maven-default'
    }
    stage('Build') {
        println "BRANCH: $GIT_BRANCH"
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