pipeline {
  agent {
    kubernetes {
      label 'mypod'
      podTemplate {
        containerTemplate {
          name 'maven'
          image 'maven:3.3.9-jdk-8-alpine'
          ttyEnabled true
          command 'cat'
        }
        containerTemplate {
          name 'node'
          image 'node:9.2'
          ttyEnabled true
          command 'cat'
        }
      }
    }
  }
  environment {
    CONTAINER_ENV_VAR = 'container-env-var-value'
  }
  stages {
    stage('Run maven') {
      steps {
        container('maven') {
          sh 'echo INSIDE_CONTAINER_ENV_VAR = ${CONTAINER_ENV_VAR}'
          sh 'mvn -version'
        }
      }
    }
    stage('Run npm') {
      steps {
        container('node') {
          sh 'echo INSIDE_CONTAINER_ENV_VAR = ${CONTAINER_ENV_VAR}'
          sh 'npm -version'
        }
      }
    }
  }
}
