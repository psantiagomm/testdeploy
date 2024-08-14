#!/bin/bash

source ./deploy/scripts/sh/functions.sh

PROJECT=$PROJECT_NAME

REDIS_PASSWORD=$(sh ./deploy/scripts/sh/encrypt.sh -m "$MASTER_PASS" -p "$REDIS_PASSWORD")
APP_PASSWORD=$(sh ./deploy/scripts/sh/encrypt.sh -m "$MASTER_PASS" -p "$APP_PASSWORD")

MESSAGES_PROPERTIES=$(echo "$MESSAGES_PROPERTIES" | sed '2,$ s/^/    /')
APPLICATION_PROPERTIES=$(normalize "${APPLICATION_PROPERTIES}")
RESILIENCE_PROPERTIES=$(echo "${RESILIENCE_PROPERTIES}" | sed '2,$ s/^/    /')

awk -v project="$PROJECT" \
  -v application_properties="$APPLICATION_PROPERTIES" \
  -v messages_properties="$MESSAGES_PROPERTIES" \
  -v resilience_properties="$RESILIENCE_PROPERTIES" \
  -v app_password="$APP_PASSWORD" \
  -v redis_password="$REDIS_PASSWORD" '
{
    gsub(/{{PROJECT}}/, project);
    gsub(/{{APPLICATION_PROPERTIES}}/, application_properties);
    gsub(/{{MESSAGES_PROPERTIES}}/, messages_properties);
    gsub(/{{RESILIENCE_PROPERTIES}}/, resilience_properties);
    gsub(/{{REDIS_PASSWORD}}/, redis_password);
    gsub(/{{APP_PASSWORD}}/, app_password);
    print;
}' configmap-template.yaml > configmap.yaml


echo "El configmap generado es"
cat configmap.yaml
