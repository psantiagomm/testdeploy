#!/bin/bash

PROJECT=$APP_PROJECT_NAME
IMAGE="${DOCKER_REGISTRY}/${IMAGE_FULL_NAME}"

awk -v project="$PROJECT" -v image="$IMAGE" -v masterPass="$MASTER_PASS" '
{
    gsub(/{{PROJECT}}/, project);
    gsub(/{{IMAGE}}/, image);
    gsub(/{{MASTER_PASS}}/, masterPass);
    print;
}' ${APP_PROJECT_PATH}deploy/deployment.yaml > deployment.yaml

kubectl apply -f deployment.yaml -n "$APP_KUBE_NAMESPACE"

rm deployment.yaml