#!/bin/bash

# Variables
RESOURCE_GROUP=lostandfound-rg
AKS_CLUSTER=lostandfound-aks
ACR_NAME=lostandfoundacr
ACR_LOGIN_SERVER="${ACR_NAME}.azurecr.io"

# Services et chemins des dossiers Docker
declare -A SERVICES=(
  [user-service]="./user-service"
  [item-service]="./item-service"
  [notification-service]="./notification-service"
  [matching-service]="./matching-service"
  [payment-service]="./payment-service"
  [reclamation-service]="./reclamation-service"
  [api-gateway]="./api-gateway"
  [service-registry]="./service-registry"
  [ai-service]="./lostandfoundfrontend/ai-service"
  [angular-app]="./lostandfoundfrontend"
)

echo "Connexion au cluster AKS..."
az aks get-credentials --resource-group $RESOURCE_GROUP --name $AKS_CLUSTER

echo "Login au registre ACR..."
az acr login --name $ACR_NAME

echo "Build, tag et push des images vers ACR..."
for service in "${!SERVICES[@]}"; do
  path=${SERVICES[$service]}
  echo "Traitement du service $service depuis $path"
  docker build -t $service $path
  docker tag $service $ACR_LOGIN_SERVER/$service:latest
  docker push $ACR_LOGIN_SERVER/$service:latest
done

echo "Déploiement des ressources Kubernetes..."
kubectl apply -f ./k8s/

echo "Liste des pods et services dans le cluster :"
kubectl get pods
kubectl get svc

echo "Déploiement terminé !"
