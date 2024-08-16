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