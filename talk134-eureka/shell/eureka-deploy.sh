#!/bin/bash

TALK134_HOME=/home/ec2-user/deploy/eureka
JAR_PATH=`find $TALK134_HOME -name talk134-eureka*.jar`

PID=`ps -ef | grep talk134-eureka*.jar | grep -v grep | awk '{print $2}'`
echo "process is $PID"
echo "jar_path is $JAR_PATH"

if [ -z $PID ]
then
  echo "Process is not running"
else
kill -15 $PID
fi

echo "start~"
nohup java -jar $JAR_PATH > /dev/null 2>&1 &
