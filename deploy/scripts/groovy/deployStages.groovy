def buildStage() {
    defineEnvVars()
    echo "Building the application... APP_PROJECT_NAME=${env.APP_PROJECT_NAME}"
    echo "Image: ${APP_DOCKER_REGISTRY}/${APP_IMAGE_FULL_NAME}"
    docker.build("${APP_DOCKER_REGISTRY}/${APP_IMAGE_FULL_NAME}", "-f $APP_DOCKERFILE $APP_DOCKER_CONTEXT")
    echo "Build completed successfully"
}

def pushStage() {
    defineEnvVars()
    docker.withRegistry("http://${APP_DOCKER_REGISTRY}") {
        docker.image("${APP_DOCKER_REGISTRY}/${APP_IMAGE_FULL_NAME}").push()
    }
}

def deployStage() {
    defineEnvVars()
    withCredentials([string(credentialsId: "$APP_MASTER_PASS_CREDENTIAL_ID", variable: 'MASTER_PASS')]) {
    sh '''
        #!/bin/bash
        chmod +x ./deploy/scripts/sh/deploy-01-deploy.sh
        ./deploy/scripts/sh/deploy-01-deploy.sh
    '''
    }
}

def defineEnvVars() {
    defineDefaultVars()
    defineImage()
}

def defineDefaultVars() {
    // Default environment variables
    env.APP_DOCKER_REGISTRY = "${env.APP_DOCKER_REGISTRY ?: 'localhost:5000'}"
}

def defineImage() {
    def pom = readMavenPom file: "$APP_POM_PATH"
    env.APP_ARTIFACT_ID = pom.artifactId
    env.APP_ARTIFACT_VERSION = pom.version
    env.APP_IMAGE_FULL_NAME = "${APP_ARTIFACT_ID}:${APP_ARTIFACT_VERSION}"


    echo "POM Content: ${pom}"
    echo "ArtifactId: ${pom.artifactId ?: 'No ArtifactId found'}"
    echo "Version: ${pom.version ?: 'No Version found'}"
}

return this