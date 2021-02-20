echo "Criando build e testes "
./mvnw.cmd package
echo "Minikube"
minikube docker-env
echo "Criando imagem docker "
docker build -t sigo-standard-microservice .
echo "Kubernetes deploy"
kubectl apply -f deployment.yaml