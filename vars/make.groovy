def call(String type) {
    def configs = readYaml file: 'config.yml'
    def services = configs.services
    def arch = [:]
    def cluster = [:]

    services.each { s ->
        arch["${s.getAt('arch')}"] = 1
        cluster["${s.getAt('cluster')}"] = 1
    }

    switch(type) {
        case 'build':
            echo 'make ' + type
            archs = [:]
            arch.each { name, _ ->
                if (state) {
                    archs["${name}"] = {build("${name}")}
                }
            }
            return {
                parallel archs
            }
            break
		
        case 'deploy':
            echo 'make ' + type
            clusters = [:]
            cluster.each { name, _ ->
                if (state) {
                    clusters["${name}"] = {withCredentials([string(credentialsId: 'ENV_GPG_PASSPHRASE', variable: 'ENV_GPG_PASSPHRASE')]) {deploy("${name}")}}
                }
            }
            return {
                parallel clusters
            }
            break

        default:
            echo 'invalid type argument for "make" function'
            break
    }
}
