call mvnw install -f pom.xml
call del E:\apitest\hbyhm.war
call move E:\GIT_code\hbyhm\hbyhm\target\demo-0.0.1-SNAPSHOT.war E:\apitest\hbyhm.war
e:
call cd \apitest\
call svn ci -m "hbyhm" hbyhm.war
e:
call cd E:\GIT_code\hbyhm\hbyhm\target\