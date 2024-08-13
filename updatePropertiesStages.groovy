def updateConfigmapStage() {
    sh '''
        #!/bin/bash
        chmod +x ./scripts/update-properties-01-configmap.sh
        ./scripts/update-properties-01-configmap.sh
    '''
}

def waitStage() {
    sh 'chmod +x ./scripts/update-properties-02-wait.sh'
    sh './scripts/update-properties-02-wait.sh'
}

def refreshPodsStage() {
    sh 'chmod +x ./scripts/update-properties-03-refresh-pods.sh'
    sh './scripts/update-properties-03-refresh-pods.sh'
}

return this