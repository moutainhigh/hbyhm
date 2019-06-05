call mvnw install -f pom.xml
call del E:\SVN\TEST\hbyhm.war
call move D:\gitRepository\hbyhm\target\demo-0.0.1-SNAPSHOT.war E:\SVN\TEST\hbyhm.war
e:
call cd \SVN\TEST\
call svn ci -m "hbyhm" hbyhm.war    
d:
call cd D:\gitRepository\hbyhm\