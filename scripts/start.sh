#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app"
JAR_FILE="$PROJECT_ROOT/spring-webapp.jar"

LOG_PATH="/home/ubuntu/log"
APP_LOG="$LOG_PATH/application.log"
ERROR_LOG="$LOG_PATH/error.log"
DEPLOY_LOG="$LOG_PATH/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE

# jar 파일 실행
nohup java -jar -Dspring.profiles.active=prod $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG