def buildStage() {
    defineEnvVars()
    sh '''
        #!/bin/bash
        whoami
        pwd
        ls -l
    '''
    echo "Building the application... PROJECT_NAME=${env.PROJECT_NAME}"
    echo "La imagen que se va a generar es: ${DOCKER_REGISTRY}/${IMAGE_FULL_NAME}"
    docker.build("${DOCKER_REGISTRY}/${IMAGE_FULL_NAME}", "-f $DOCKERFILE $DOCKER_CONTEXT")
}

def pushStage() {
    defineEnvVars()
    docker.withRegistry("http://${DOCKER_REGISTRY}") {
        docker.image("${DOCKER_REGISTRY}/${IMAGE_FULL_NAME}").push()
    }
}

def deployStage() {
    defineEnvVars()
    sh '''
        #!/bin/bash
        pwd
        ls -l
        chmod +x ./deploy/scripts/deploy-01-deploy.sh
        ./deploy/scripts/deploy-01-deploy.sh
    '''
}

def defineEnvVars() {
    env.IMAGE_NAME = env.PROJECT_NAME
    COMMIT_HASH = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
    IMAGE_TAG = "${env.BUILD_NUMBER}-${COMMIT_HASH}"
    IMAGE_FULL_NAME = "${IMAGE_NAME}:${IMAGE_TAG}"
}

return this