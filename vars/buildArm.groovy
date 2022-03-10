def call() {
  node ('arm') {
    stage('arm') {
      if(env.BRANCH_NAME == 'master') {
        sh 'forstok build --arch arm'
      }
    }
  }
}
