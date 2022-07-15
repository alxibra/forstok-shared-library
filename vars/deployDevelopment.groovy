def call() {
  node ('built-in') {
    checkout scm
    if(env.BRANCH_NAME == 'master') {
      sh 'forstok deploy --cluster development'
    }
  }
}


