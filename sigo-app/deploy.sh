echo "Minikube"
minikube docker-env
echo "Criando imagem docker "
docker build -t sigo-app .
echo "Kubernetes deploy"
kubectl apply -f deployment.yaml