def call {
  node('slave') {
    checkout scm
    if(env.BRANCH_NAME == 'master') {
      sh 'forstok build --arch x86'
    }
  }
}
