def call(String type) {
	def configs = readYaml file: 'config.yml'
	def services = configs.services
	def arch = [x86: false, arm: false]
	def cluster = [polling: false, development: false, api: false]

    services.each { s ->
        def serviceArch = s.getAt('arch')
        if (serviceArch == 'arm' && !arch.arm) {
            arch.arm = true
        } else if (serviceArch == 'x86' && !arch.x86) {
            arch.x86 = true
        }

        def serviceCluster = s.getAt('cluster')
        if (serviceCluster == 'development' && !cluster.development) {
            cluster.development = true
        } else if (serviceCluster == 'polling' && !cluster.polling) {
            cluster.polling = true
        } else if (serviceCluster == 'api' && !cluster.api) {
            cluster.api = true
        }
    }
	
	switch(type) {
		case 'build':
			echo 'make ' + type
			action = [
				arm: {buildArm()},
				x86: {build_x86()}
			]

	    	archs = [:]
			arch.each { name, state ->
				if (state) {
					archs["${name}"] = action[name]
				}
			}
			return {
				parallel archs
			}
			break
		
		case 'deploy':
			echo 'make ' + type
			action = [
				polling: {deployPolling()},
				development: {deployDevelopment()},
				api: {deployApi()}
			]

			clusters = [:]
			cluster.each { name, state ->
				if (state) {
					clusters["${name}"] = action[name]
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