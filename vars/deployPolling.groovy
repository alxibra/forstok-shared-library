def call() {
  node ('master') {
    checkout scm
    if(env.BRANCH_NAME == 'master') {
      sh 'forstok deploy --cluster polling'
    }
  }
}

