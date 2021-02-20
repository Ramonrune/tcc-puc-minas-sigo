echo "Criando build e testes "
./mvnw.cmd -DAMQP_HOST=***REMOVED*** package
echo "Minikube"
minikube docker-env
echo "Criando imagem docker "
docker build -t sigo-industry-management-microservice .
echo "Kubernetes deploy"
kubectl apply -f deployment.yaml