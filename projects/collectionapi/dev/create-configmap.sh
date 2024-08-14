#!/bin/bash

source ./deploy/scripts/sh/functions.sh

PROJECT=$APP_PROJECT_NAME

REDIS_PASSWORD=$(sh ./deploy/scripts/sh/encrypt.sh -m "$MASTER_PASS" -p "$REDIS_PASSWORD")
APP_PASSWORD=$(sh ./deploy/scripts/sh/encrypt.sh -m "$MASTER_PASS" -p "$APP_PASSWORD")

MESSAGES_PROPERTIES=$(echo "$MESSAGES_PROPERTIES" | sed '2,$ s/^/    /')
APPLICATION_PROPERTIES=$(normalize "${APPLICATION_PROPERTIES}")
RESILIENCE_PROPERTIES=$(echo "${RESILIENCE_PROPERTIES}" | sed '2,$ s/^/    /')

# Crear el archivo configmap.yaml con los valores de los parámetros
cat <<EOF > configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: testjenkins
data:
  application.properties: |
    $APPLICATION_PROPERTIES
    app.redis.password=$REDIS_PASSWORD
    app.password=$APP_PASSWORD
  messages.properties: |
    $MESSAGES_PROPERTIES
  resilience-dev.yaml: |
    $RESILIENCE_PROPERTIES
EOF


echo "El configmap generado es"
cat configmap.yaml
