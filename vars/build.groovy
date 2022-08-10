def call() {
    arch = [x86: false, arm: false]
    script{ configs = readYaml (file: 'config.yml') }
    services = configs[services]

    services.each { s ->
        serviceArch = s.getAt('arch')
        if (serviceArch == 'arm' && !arch[arm]) {
            arch[arm] = true
        } else if (serviceArch == 'x86' && !arch["x86"]) {
            arch[x86] = true
        }
    }

    if (arch[arm]) {
        node ('arm') {
            checkout scm
            if(env.BRANCH_NAME == 'master') {
                sh 'forstok build --arch arm'
            }
        }
    }

    if (arch[x86]) {
        node('slave') {
            checkout scm
            if(env.BRANCH_NAME == 'master') {
                sh 'forstok build --arch x86'
            }
        }
    }
}