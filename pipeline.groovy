def setupPipeline() {

    stage('Build Docker Image') {
        steps {
            echo "Building Docker image..."
        }
    }

    stage('Push Docker Image') {
        steps {
            echo "Pushing Docker image to registry..."
        }
    }

    // Otros stages pueden ser agregados aquí
}

return this