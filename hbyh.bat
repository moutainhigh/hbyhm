call mvnw install -f pom.xml
call del H:\快车道\SVN服务器2\hbyhm.war
call move G:\gitHub\hbyhm\target\demo-hbyhm_war.war H:\快车道\SVN服务器2\hbyhm.war
H:
call cd \快车道\SVN服务器2\
call svn ci -m "hbyhm" hbyhm.war    
G:
call cd G:\gitHub\hbyhm\