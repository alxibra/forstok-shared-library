def call(Map config) {
  pipeline {
    agent none
    stages {
      stage ('QA'){
        parallel {
          stage('Test') {
            agent {
              docker {
                image config.goVer
                label 'slave'
                args '--user root'
              }
            }
            when {
              not {
                branch 'master'
              }
            }
            steps {
                sh 'go test -v ./...'
            }
          }
          stage('linter') {
            agent {
              docker {
                image config.goVer
                label 'slave'
                args '--user root'
              }
            }
            when {
              not {
                branch 'master'
              }
            }
            steps {
                sh 'go get -u golang.org/x/lint/golint'
                sh 'golint -set_exit_status ./...'
            }
          }
        }
      }
      stage('build') {
        agent { label "slave" }
        when {
           branch 'master'
        }
        environment {
            AWS_KEY = credentials('AWS_KEY')
            AWS_SECRET = credentials('AWS_SECRET')
            AWS_REGION = credentials('AWS_REGION')
        }
        steps {
            sh 'bin/build'
        }
      }
      stage('deploy to production') {
        agent { label "master" }
        when {
            branch 'master'
        }
        environment {
            AWS_KEY = credentials('AWS_KEY')
            AWS_SECRET = credentials('AWS_SECRET')
            AWS_REGION = credentials('AWS_REGION')
        }
        steps {
            sh 'bin/production'
        }
        post {
          success {
            slackSend message: "${env.JOB_NAME} is success, info: ${env.BUILD_URL}",
                      color: 'good'
          }
          failure {
            slackSend message: "${env.JOB_NAME} fails, info: ${env.BUILD_URL}",
                      color: 'danger'
          }
        }
      }
    }
  }
}
