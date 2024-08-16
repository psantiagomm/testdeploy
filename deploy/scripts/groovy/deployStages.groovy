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
        chmod +x ${APP_SH_SCRIPTS_DEPLOY_PATH}
        ${APP_SH_SCRIPTS_DEPLOY_PATH}
    '''
    }
}

def defineEnvVars() {
    env.APP_SCRIPTS_PATH = "${env.APP_SCRIPTS_PATH ?: './deploy/scripts'}"
    env.APP_GROOVY_SCRIPTS_FUNCTIONS_PATH = defineValue(env.APP_GROOVY_SCRIPTS_FUNCTIONS_PATH, "${env.APP_SCRIPTS_PATH}/groovy/functions.groovy")

    def functions = load "${APP_GROOVY_SCRIPTS_FUNCTIONS_PATH}"
    functions.defineDefaultVars()
    functions.defineImage()
}

def defineValue(value, defaultValue) {
    return "${value ?: defaultValue}"
}

return this