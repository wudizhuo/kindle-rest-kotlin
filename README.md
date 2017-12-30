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
sudo docker run -d --net host -t wudizhuo/kindle-rest-service

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
   server_name localhost api.kindlezhushou.com;
   location / {
        root   /usr/share/nginx/html;
        index  index.html index.htm index.php;
        try_files $uri $uri/ /index.php?$uri&$args;
    }

   location /v3 {
       proxy_pass http://127.0.0.1:8083/;
   }
 }
}
```
```
sudo docker run --net host -v ~/nginx.conf:/etc/nginx/nginx.conf:ro -v ~/kindle_web_react:/usr/share/nginx/html -d nginx
```
phantomjs

```
sudo docker run -d -p 8910:8910 wernight/phantomjs phantomjs --webdriver=8910
```

mwader/postfix-relay

```
sudo docker run \
        -p 25:25\
    -e POSTFIX_myhostname=www.kindlezhushou.com \
    -e POSTFIX_inet_protocols=ipv4 \
    mwader/postfix-relay
```

Next Step: 

Create docker-compose.yml to group the docker steps. 


