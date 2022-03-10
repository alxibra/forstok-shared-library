def call() {
  node ('master') {
    if(env.BRANCH_NAME == 'master') {
      sh 'forstok deploy --cluster polling'
    }
  }
}

