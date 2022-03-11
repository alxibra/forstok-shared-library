def call() {
  node ('slave') {
    checkout scm
    if(env.BRANCH_NAME != 'master') {
      docker.image('alxibra/forstok-webhook-order-channel:0.0.4').withRun() { c ->
        docker.image('alxibra/forstok-webhook-order-channel:0.0.4').inside("--user root") {
            sh 'golangci-lint run'
            sh 'golint -set_exit_status ./...'
        }
      }
    }
  }
}

