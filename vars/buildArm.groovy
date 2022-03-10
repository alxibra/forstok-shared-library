def call() {
  node ('arm') {
    stage('arm') {

      when {
        branch 'master'
      }

      steps {
        sh 'forstok build --arch arm'
      }
    }
  }
}
