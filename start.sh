export MAVEN_OPTS="-Xmx768m -XX:MaxPermSize=256m -Dmaven.repo.local=repo -agentpath:/opt/yjp/bin/linux-x86-64/libyjpagent.so=triggers=triggers.txt,delay=20000,dir=snapshots,probe=LongRunningMethodProbe,probeclasspath=probes"
mvn -Dmaven.test.skip=true clean install embedded-glassfish:run
