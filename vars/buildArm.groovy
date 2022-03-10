def call() {
  node ('arm') {
    if(env.BRANCH_NAME == 'master') {
      sh 'forstok build --arch arm'
    }
  }
}
