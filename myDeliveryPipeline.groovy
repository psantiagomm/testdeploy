def call() {

    pipeline {
        agent any
        stages {
            stage('checkout git') {
                steps {
                    echo "funciona"
                }
            }
        }
    }
}
