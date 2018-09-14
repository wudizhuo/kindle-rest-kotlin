[![Build Status](https://travis-ci.org/wudizhuo/kindle-rest-kotlin.svg?branch=master)](https://travis-ci.org/wudizhuo/kindle-rest-kotlin) &nbsp;
[![codecov](https://codecov.io/gh/wudizhuo/kindle-rest-kotlin/branch/master/graph/badge.svg)](https://codecov.io/gh/wudizhuo/kindle-rest-kotlin)
## kindle-rest-service

To run:

```
$ ./gradlew bootRun
```

For Product run step:

docker:
```
sudo docker run -t -d --name kindle-rest-service --net host wudizhuo/kindle-rest-service

```

Dependence Docker

Ngnix with the nginx.conf
 
nginx.conf
```
events {
    worker_connections  1024;
}

http {
 server {
   listen 80 default_server;
   server_name localhost www.kindlezhushou.com kindlezhushou.com;
   location / {
        root   /usr/share/nginx/html;
        index  index.html index.htm index.php;
        try_files $uri $uri/ /index.php?$uri&$args;
    }
 }
 server {
    listen 80;
    server_name api.kindlezhushou.com;
    
    proxy_set_header Host $http_host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    
    location / {
         proxy_pass http://127.0.0.1:8083/;
     }
 
    location /v3 {
        proxy_pass http://127.0.0.1:8083/;
    }
  }
  client_max_body_size 65M;
}
```
```
sudo docker run -t -d --name nginx --net host -v ~/nginx.conf:/etc/nginx/nginx.conf:ro -v ~/kindle_web_react:/usr/share/nginx/html nginx
```

mwader/postfix-relay

```
sudo docker run -t -d \
        -p 25:25\
    -e POSTFIX_myhostname=www.kindlezhushou.com \
    -e POSTFIX_inet_protocols=ipv4 \
    -e POSTFIX_message_size_limit=62428800 \
    --name postfix \
    mwader/postfix-relay
```

mongo
```
sudo docker run --name mongo -t -d -p 27017:27017 mongo

```

Next TODO Step: 

Create docker-compose.yml to group the docker steps. 


Develop Docker Step

```
./gradlew build docker
docker push wudizhuo/kindle-rest-service
```


