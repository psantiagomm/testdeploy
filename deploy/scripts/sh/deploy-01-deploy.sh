#!/bin/bash

PROJECT=$APP_PROJECT_NAME
IMAGE="${APP_DOCKER_REGISTRY}/${APP_IMAGE_FULL_NAME}"


awk -v project="$PROJECT" \
    -v image="$IMAGE" \
    -v profile="$APP_PROFILE" \
    -v port="$APP_PORT" \
    -v nodePort="$APP_NODEPORT" \
    -v replicas="$APP_REPLICAS" \
    -v masterPass="$MASTER_PASS" '
{
    gsub(/{{PROJECT}}/, project);
    gsub(/{{IMAGE}}/, image);
    gsub(/{{PROFILE}}/, profile);
    gsub(/{{PORT}}/, port);
    gsub(/{{NODEPORT}}/, nodePort);
    gsub(/{{APP_REPLICAS}}/, replicas);
    gsub(/{{MASTER_PASS}}/, masterPass);
    print;
}' deploy/deployment.yaml > deployment.yaml

kubectl apply -f deployment.yaml -n "$APP_KUBE_NAMESPACE"

rm deployment.yaml