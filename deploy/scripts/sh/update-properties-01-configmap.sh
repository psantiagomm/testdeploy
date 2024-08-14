#!/bin/bash

# Aplicar el ConfigMap en Minikube
kubectl apply -f configmap.yaml --context="$APP_KUBE_CONTEXT"

rm configmap.yaml