call mvnw install -f pom.xml
call del H:\快车道\SVN服务器2\hbyhm.war
call move G:\gitHub\hbyhm\target\demo-0.0.1-SNAPSHOT.war H:\快车道\SVN服务器2\hbyhm.war
h:
call cd \快车道\SVN服务器2\
call svn ci -m "hbyhm" hbyhm.war
g:
call cd G:\gitHub\hbyhm\