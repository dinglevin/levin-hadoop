#!/bin/bash

# resolve links - $0 may be a softlink
PRG="${0}"
while [ -h "${PRG}" ]; do
  ls=`ls -ld "${PRG}"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "${PRG}"`/"$link"
  fi
done

BASEDIR=`dirname ${PRG}`
BASEDIR=`cd ${BASEDIR}/..;pwd`

source ${BASEDIR}/../commonEnv.sh

export HADOOP_CONF_DIR=${BASEDIR}/config
export HADOOP_LOG_DIR=${BASEDIR}/logs
export HADOOP_PID_DIR=${BASEDIR}/pid

export HADOOP_SECURITY_LOGGER=DEBUG,RFAS
export HDFS_AUDIT_LOGGER=DEBUG,RFAAUDIT
export HADOOP_ROOT_LOGGER=DEBUG,RFA

${HADOOP_HOME}/bin/hdfs namenode &