def deployStage() {
    defineEnvVars()
    withCredentials([string(credentialsId: "$APP_MASTER_PASS_CREDENTIAL_ID", variable: 'MASTER_PASS')]) {
    sh '''
        #!/bin/bash
        pwd
        ls -l
        chmod +x ./deploy/scripts/sh/deploy-01-deploy.sh
        ./deploy/scripts/sh/deploy-01-deploy.sh
    '''
    }
}

def defineEnvVars() {
    env.IMAGE_NAME = env.APP_PROJECT_NAME
    env.APP_FUNCIONA = "Valor de funciona"

    def pom = readMavenPom file: "$APP_POM_PATH"
    env.APP_ARTIFACT_ID = pom.artifactId
    env.APP_ARTIFACT_VERSION = pom.version
    env.APP_IMAGE_FULL_NAME = "${APP_ARTIFACT_ID}:${APP_ARTIFACT_VERSION}"


    echo "POM Content: ${pom}"
    echo "ArtifactId: ${pom.artifactId ?: 'No ArtifactId found'}"
    echo "Version: ${pom.version ?: 'No Version found'}"
}

return this