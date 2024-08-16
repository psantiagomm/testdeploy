def defineDefaultVars() {
    // Default environment variables
    echo "Default environment variables"
    env.APP_DOCKER_REGISTRY = "${env.APP_DOCKER_REGISTRY ?: 'localhost:5000'}"
    env.APP_SCRIPTS_PATH = "${env.APP_SCRIPTS_PATH ?: './deploy/scripts'}"

    env.APP_SH_SCRIPTS_DEPLOY_PATH = defineValue(env.APP_SH_SCRIPTS_DEPLOY_PATH, "${env.APP_SCRIPTS_PATH}/sh/deploy-01-deploy.sh")
    env.APP_KUBE_DEPLOY_PATH = defineValue(env.APP_KUBE_DEPLOY_PATH, "deploy/deployment.yaml")
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

def defineDefaultVarsUpdateProperties() {
    // Default environment variables
    echo "Default environment variables"
    env.APP_DOCKER_REGISTRY = "${env.APP_DOCKER_REGISTRY ?: 'localhost:5000'}"
    env.APP_SCRIPTS_PATH = "${env.APP_SCRIPTS_PATH ?: './deploy/scripts'}"

    env.APP_SH_SCRIPTS_PROPERTIES_CONFIGMAP_PATH = defineValue(env.APP_SH_SCRIPTS_PROPERTIES_CONFIGMAP_PATH, "${env.APP_SCRIPTS_PATH}/sh/update-properties-01-configmap.sh")
    env.APP_SH_SCRIPTS_PROPERTIES_WAIT_PATH = defineValue(env.APP_SH_SCRIPTS_PROPERTIES_WAIT_PATH, "${env.APP_SCRIPTS_PATH}/sh/update-properties-02-wait.sh")
    env.APP_SH_SCRIPTS_PROPERTIES_REFRESH_PATH = defineValue(env.APP_SH_SCRIPTS_PROPERTIES_REFRESH_PATH, "${env.APP_SCRIPTS_PATH}/sh/update-properties-03-refresh-pods.sh")
}


def defineValue(value, defaultValue) {
    return "${value ?: defaultValue}"
}

return this