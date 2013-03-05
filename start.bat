
set MAVEN_OPTS=-Xmx1024m -XX:MaxPermSize=256m -Dmaven.repo.local=repo -agentpath:"C:\Program Files (x86)\YourKit Java Profiler 12.0.3\bin\win64\yjpagent.dll=triggers=triggers.txt,delay=20000,dir=snapshots"
mvn -Dmaven.test.skip=true clean install embedded-glassfish:run
