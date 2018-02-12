## Spring Boot Example

This is a basic example of how to use Kotlin in a Spring Boot application. See the [accompanying tutorial](http://kotlinlang.org/docs/tutorials/spring-boot-restful.html)
for more information.

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
phantomjs

use 'phantomjs testPhantomjs.js' for debug Phantomjs

```
sudo docker run -t -d --name phantomjs -p 8910:8910 wernight/phantomjs phantomjs --webdriver=8910
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


