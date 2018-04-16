node {
    def mvnHome
    stage('Preparation') {
        // Using scm configuration from upstream project.
        checkout scm
        //Setting GIT_BRANCH in environment because jenkins does not set it in the current version.
        //env.GIT_BRANCH = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
        env.GIT_BRANCH = sh(returnStdout: true, script: 'git name-rev --name-only HEAD').trim()

        println "BRANCH: $GIT_BRANCH"

        // Get the Maven tool.
        // ** NOTE: This 'maven-default' Maven tool must be configured
        // **       in the global configuration.
        mvnHome = tool 'maven-default'
    }
    stage('Build') {
        println "BRANCH: $GIT_BRANCH"
        // Run the maven build
        sh "'${mvnHome}/bin/mvn' clean deploy -Pwith-docker -Pwith-profile -Pwith-usermgt -DaltDeploymentRepository=nexus::default::http://nexus/repository/releases"
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
}
