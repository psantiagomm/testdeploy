def setupPipeline() {

    pipeline {
        agent any
        stages {
            stage('build') {
                steps {
                    echo "funciona"
                }
            }
        }
    }
}

return this