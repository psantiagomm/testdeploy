def defineDefaultVars() {
    // Default environment variables
    echo "Default environment variables"
    env.APP_DOCKER_REGISTRY = "${env.APP_DOCKER_REGISTRY ?: 'localhost:5000'}"
    env.APP_SCRIPTS_PATH = "${env.APP_SCRIPTS_PATH ?: './deploy/scripts'}"
    env.APP_SCRIPTS_DEPLOY_PATH = defineValue(env.APP_SCRIPTS_DEPLOY_PATH, "${env.APP_SCRIPTS_PATH}/sh/deploy-01-deploy.sh")
    echo "APP_SCRIPTS_DEPLOY_PATH es ${APP_SCRIPTS_DEPLOY_PATH}"
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

def defineValue(value, defaultValue) {
    return "${value ?: defaultValue}"
}

return this