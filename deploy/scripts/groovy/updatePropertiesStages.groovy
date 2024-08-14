def updateConfigmapStage() {
    withCredentials([string(credentialsId: 'TESTJENKINS_MATER_PASS', variable: 'MASTER_PASS')]) {
    sh '''
        #!/bin/bash
        chmod +x ./deploy/scripts/sh/update-properties-01-configmap.sh
        ${APP_CREATE_CONFIGMAP_SCRIPT}
        ./deploy/scripts/sh/update-properties-01-configmap.sh
    '''
    }
}

def waitStage() {
    sh 'chmod +x ./deploy/scripts/sh/update-properties-02-wait.sh'
    sh './deploy/scripts/sh/update-properties-02-wait.sh'
}

def refreshPodsStage() {
    sh 'chmod +x ./deploy/scripts/sh/update-properties-03-refresh-pods.sh'
    sh './deploy/scripts/sh/update-properties-03-refresh-pods.sh'
}

return this