#!/bin/bash

# Aplicar el ConfigMap en Minikube
kubectl apply -f configmap.yaml -n "$APP_KUBE_CONTEXT"

rm configmap.yaml