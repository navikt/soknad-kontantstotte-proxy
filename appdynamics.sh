#!/bin/bash
if test -r "/opt/appdynamics/javaagent.jar";
then
    JAVA_OPTS="${JAVA_OPTS} -javaagent:/opt/appdynamics/javaagent.jar"
    JAVA_OPTS="${JAVA_OPTS} -Dappdynamics.agent.applicationName=${NAIS_APP_NAME}"
    JAVA_OPTS="${JAVA_OPTS} -Dappdynamics.agent.tierName=${ENVIRONMENT_NAME}-${NAIS_APP_NAME}"
    JAVA_OPTS="${JAVA_OPTS} -Dappdynamics.agent.reuse.nodeName=true"
    JAVA_OPTS="${JAVA_OPTS} -Dappdynamics.agent.reuse.nodeName.prefix=${ENVIRONMENT_NAME}_${NAIS_APP_NAME}_"
    JAVA_OPTS="${JAVA_OPTS} -Dappdynamics.jvm.shutdown.mark.node.as.historical=true"
    export JAVA_OPTS
fi