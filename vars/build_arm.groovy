def call() {
  stage('arm') {
    agent { label 'arm' }

    when {
      branch 'master'
    }

    steps {
      sh 'forstok build --arch arm'
    }
  }
}
