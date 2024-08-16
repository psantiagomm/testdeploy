def updateConfigmapStage() {
    defineEnvVars()
    withCredentials([string(credentialsId: "$APP_MASTER_PASS_CREDENTIAL_ID", variable: 'MASTER_PASS')]) {
    sh '''
        #!/bin/bash
        chmod +x ${APP_CREATE_CONFIGMAP_SCRIPT}
        chmod +x ${APP_SH_SCRIPTS_PROPERTIES_CONFIGMAP_PATH}
        ${APP_CREATE_CONFIGMAP_SCRIPT}
        ${APP_SH_SCRIPTS_PROPERTIES_CONFIGMAP_PATH}
    '''
    }
}

def waitStage() {
    defineEnvVars()
    sh 'chmod +x ${APP_SH_SCRIPTS_PROPERTIES_WAIT_PATH}'
    sh '${APP_SH_SCRIPTS_PROPERTIES_WAIT_PATH}'
}

def refreshPodsStage() {
    defineEnvVars()
    sh 'chmod +x ${APP_SH_SCRIPTS_PROPERTIES_REFRESH_PATH}'
    sh '${APP_SH_SCRIPTS_PROPERTIES_REFRESH_PATH}'
}

def defineEnvVars() {
    env.APP_SCRIPTS_PATH = "${env.APP_SCRIPTS_PATH ?: './deploy/scripts'}"
    env.APP_GROOVY_SCRIPTS_FUNCTIONS_PATH = defineValue(env.APP_GROOVY_SCRIPTS_FUNCTIONS_PATH, "${env.APP_SCRIPTS_PATH}/groovy/functions.groovy")

    def functions = load "${APP_GROOVY_SCRIPTS_FUNCTIONS_PATH}"
    functions.defineDefaultVarsUpdateProperties()
}

return this