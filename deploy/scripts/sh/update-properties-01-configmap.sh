#!/bin/bash

# Aplicar el ConfigMap en Minikube
kubectl apply -f configmap.yaml

rm configmap.yaml