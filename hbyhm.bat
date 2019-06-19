call mvnw install -f pom.xml
call del E:\SVN\TEST\hbyhm.war
call move D:\gitRepository\hbyhm\target\demo-hbyhm_war.war E:\SVN\TEST\hbyhm.war
e:
call cd \SVN\TEST\
call svn ci -m "hbyhm" hbyhm.war    
d:
call cd D:\gitRepository\hbyhm\