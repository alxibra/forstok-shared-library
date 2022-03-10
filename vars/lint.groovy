def call() {
  checkout scm
  node ('slave') {
    if(env.BRANCH_NAME != 'master') {
      docker.image('alxibra/forstok-webhook-order-channel:0.0.4').withRun('--user root') { c ->
        sh 'golangci-lint run'
        sh 'golint -set_exit_status ./...'
      }
    }
  }
}

