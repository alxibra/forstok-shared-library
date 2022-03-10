def call() {
  checkout scm
  node ('slave') {
    if(env.BRANCH_NAME != 'master') {
      docker.image('golang:1.16.3').withRun('--user root') { c ->
        sh 'go get github.com/golangci/golangci-lint/cmd/golangci-lint@v1.40.1'
        sh 'golangci-lint run'
        sh 'golint -set_exit_status ./...'
      }
    }
  }
}

