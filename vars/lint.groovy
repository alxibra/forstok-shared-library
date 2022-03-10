def call() {
  node ('slave') {
    if(env.BRANCH_NAME != 'master') {
      docker.image('golang:1.16.3').withRun('--user root') { c ->
        docker.image('golang:1.16.3').inside("--user root") {
            sh 'go get github.com/golangci/golangci-lint/cmd/golangci-lint@v1.40.1'
        }
      }
    }
  }
}

