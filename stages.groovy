// reusableStages.groovy
def call() {
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    echo "Building the application with PROJECT_NAME=${env.PROJECT_NAME}"
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
