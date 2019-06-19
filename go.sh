###
 # @说明: 这里写说明哈
 # @Description: file content
 # @Author: tt
 # @Date: 2019-06-17 17:02:00
 # @LastEditTime: 2019-06-17 21:41:03
 # @LastEditors: tt
###
"/work/sd128/work/source/JAVA/hbyhm/mvnw" install -f "/work/sd128/work/source/JAVA/hbyhm/pom.xml"
rm /work/sd128/work/source/JAVA/erp/hbyhm.war
mv /work/sd128/work/source/JAVA/hbyhm/target/demo-hbyhm_war.war /work/sd128/work/source/JAVA/erp/hbyhm.war
cd /work/sd128/work/source/JAVA/erp/
svn ci -m "tt" hbyhm.war