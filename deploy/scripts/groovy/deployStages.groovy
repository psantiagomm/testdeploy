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
        chmod +x ${APP_SCRIPTS_DEPLOY_PATH}
        ${APP_SCRIPTS_DEPLOY_PATH}
    '''
    }
}

def defineEnvVars() {
    def functions = load './deploy/scripts/groovy/functions.groovy'
    functions.defineDefaultVars()
    functions.defineImage()
}

return this