def call() {
  pipeline {
    agent any
    stages {
      stage ('QA'){
        parallel {
          stage('Test') {
            agent {
              docker {
                image 'golang:1.14'
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
                image 'golang:1.14'
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
    }
  }
}
