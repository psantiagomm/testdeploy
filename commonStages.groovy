def buildStage() {
    defineEnvVars()
    echo "Building the application... PROJECT_NAME=${env.PROJECT_NAME}"

    echo "La imagen que se va a generar es: ${DOCKER_REGISTRY}/${IMAGE_FULL_NAME}"
    // docker.build("${DOCKER_REGISTRY}/${IMAGE_FULL_NAME}", "-f deploy/Dockerfile .")
}

def deployStage() {
    echo "Deploying the application..."
    // Deploy steps
}

def defineEnvVars() {
    env.IMAGE_NAME = env.PROJECT_NAME
    COMMIT_HASH = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
    IMAGE_TAG = "${env.BUILD_NUMBER}-${COMMIT_HASH}"
    IMAGE_FULL_NAME = "${IMAGE_NAME}:${IMAGE_TAG}"
}

return this