set MAVEN_OPTS=-Xmx768m -XX:MaxPermSize=256m -Dmaven.repo.local=".\repo"
mvn -Dmaven.test.skip=true clean install
