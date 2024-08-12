// reusableStages.groovy
def runStages() {
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    echo "Building the application with"
                }
            }
            stage('Deploy') {
                steps {
                    echo "Deploying the application..."
                }
            }
        }
    }
}

return this