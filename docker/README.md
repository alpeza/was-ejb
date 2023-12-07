

[https:///localhost:9043/ibm/console](https:///localhost:9043/ibm/console)

* Usuario: wsadmin
* Password: `docker exec decwas cat /tmp/PASSWORD`


`docker exec decwas tail -f /opt/IBM/WebSphere/AppServer/profiles/AppSrv01/logs/ffdc/*.txt`

docker exec decwas sh -c 'tail -f /opt/IBM/WebSphere/AppServer/profiles/AppSrv01/logs/ffdc/*.txt'

# Referencias 

* **MDB:** https://github.com/ibm-cloud-architecture/refarch-mq-messaging/blob/master/docs/mdb-twas/README.md
* **MDB:** https://www.youtube.com/watch?v=YnWU9IFz-dc

* https://unic8der.medium.com/spring-boot-on-websphere-c8114da409af
* https://www.youtube.com/watch?v=ZP8Um12Z_mk&list=PLhz9baCHY7qFbO7hHskDCkPQF5buqtuop
* https://blog.tericcabrel.com/web-application-springboot-thymeleaf-tailwindcss/


DecWas

```
https://10.241.122.200:9043/ibm/console/logon.jsp
USER_WAS=decadmin
PSW_WAS=decdec
```