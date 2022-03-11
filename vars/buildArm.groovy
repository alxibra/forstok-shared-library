def call() {
  node ('arm') {
    checkout scm
    if(env.BRANCH_NAME == 'master') {
      sh 'forstok build --arch arm'
    }
  }
}
