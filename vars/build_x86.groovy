def call {
  stage('x86') {
    agent { label 'slave' }
    when {
      branch 'master'
    }
    steps {
      sh 'forstok build --arch x86'
    }
  }
}
