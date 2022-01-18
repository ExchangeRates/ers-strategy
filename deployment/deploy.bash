cd ..

mvn clean install

docker build -t ers -f deployment/dockerfile .
docker tag ers azch97/ers:latest
docker push azch97/ers:latest


scp -r ./deployment/kubernetes server:~/ers-strategy
ssh server 'kubectl replace --force -f ~/ers-strategy/deployment.yml'

