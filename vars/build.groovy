def call(String arch) {
  def nodeId = ['arm': 'arm', 'x86': 'slave']
  node(nodeId[arch]) {
    checkout scm
    if(env.BRANCH_NAME == 'master') {
      sh 'forstok build --arch ' + arch
    }
  }
}
