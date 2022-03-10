def call() {
  node ('master') {
    stage('polling') {
      if(env.BRANCH_NAME == 'master') {
        sh 'forstok deploy --cluster polling'
      }
    }
  }
}

