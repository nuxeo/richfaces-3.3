set LICENSE_PATH="D:\Work\Projects\RichFaces\maven\clover.license"


echo Clean Intall
call TestCoverage.jar
call mvn clean install
echo Pick all test..
call TestCoverage.jar copy
echo Generate coverage database...
call mvn com.atlassian.maven.plugins:maven-clover-plugin:3.7:instrument -P clover -Dclover.license.path=%LICENSE_PATH%
echo Generate report...
call mvn com.atlassian.maven.plugins:maven-clover-plugin:3.7:clover -P clover -Dclover.license.path=%LICENSE_PATH%
call TestCoverage.jar
