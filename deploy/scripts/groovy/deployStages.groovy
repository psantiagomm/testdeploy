def buildStage() {
    defineEnvVars()
    echo "Building the application... APP_PROJECT_NAME=${env.APP_PROJECT_NAME}"
    echo "La imagen que se va a generar es: ${DOCKER_REGISTRY}/${APP_IMAGE_FULL_NAME}"
    docker.build("${DOCKER_REGISTRY}/${APP_IMAGE_FULL_NAME}", "-f $APP_DOCKERFILE $APP_DOCKER_CONTEXT")
    echo "Imagen contruida exitosamente"
    echo "Funciona env.APP_FUNCIONA ${env.APP_FUNCIONA}"
    echo "Funciona APP_FUNCIONA ${APP_FUNCIONA}"
}

def pushStage() {
    defineEnvVars()
    docker.withRegistry("http://${DOCKER_REGISTRY}") {
        docker.image("${DOCKER_REGISTRY}/${APP_IMAGE_FULL_NAME}").push()
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
    env.IMAGE_NAME = env.APP_PROJECT_NAME
    env.APP_FUNCIONA = "Valor de funciona"
}

return this