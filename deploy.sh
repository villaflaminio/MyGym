git pull
mvn clean install
docker stop mygym
docker rm mygym
docker build -t mygym .
docker run -d --name mygym -p 4004:4004 --add-host host.docker.internal:172.17.0.1 mygym --restart=always
