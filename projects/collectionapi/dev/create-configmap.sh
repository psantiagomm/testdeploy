#!/bin/bash

source ./deploy/scripts/sh/functions.sh

PROJECT=$APP_PROJECT_NAME

MESSAGES_PROPERTIES=$(normalize "$MESSAGES_PROPERTIES")
APPLICATION_PROPERTIES=$(normalize "${APPLICATION_PROPERTIES}")
RESILIENCE_PROPERTIES=$(normalize "${RESILIENCE_PROPERTIES}")

# Crear el archivo configmap.yaml con los valores de los parámetros
cat <<EOF > configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: $APP_PROJECT_NAME
data:
  application.properties: |
    $APPLICATION_PROPERTIES
  messages.properties: |
    $MESSAGES_PROPERTIES
  resilience-dev.yaml: |
    $RESILIENCE_PROPERTIES
EOF


echo "El configmap generado es"
cat configmap.yaml
