###
 # @说明: 这里写说明哈
 # @Description: file content
 # @Author: tt
 # @Date: 2019-06-17 17:02:00
 # @LastEditTime: 2019-06-17 17:03:01
 # @LastEditors: tt
###
"/work/sd128/work/source/JAVA/hbyhm/mvnw" install -f "/work/sd128/work/source/JAVA/hbyhm/pom.xml"
rm /work/sd128/work/source/JAVA/erp/kjb/hbyhm.war
mv /work/sd128/work/source/JAVA/hbyhm/target/tt_demo1-cy.war /work/sd128/work/source/JAVA/erp/kjb/hbyhm.war
cd /work/sd128/work/source/JAVA/erp/kjb/
svn ci -m "tt" hbyhm.war