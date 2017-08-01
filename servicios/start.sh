cd ProductService
mvn clean package
cd ..
docker-compose up -d --build
#docker swarm init
#docker stack deploy -c docker-compose.yml kallsonys