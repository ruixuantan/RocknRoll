#!/bin/sh

compile() {
  echo Compiling sbt project...
  sbt clean compile
  echo Installing npm packages...
  cd rocknroll-web
  npm install
}

start_server() {
  echo starting server...
  sbt rocknrollServer/run
}

start_web() {
  echo Starting web...
  cd rocknroll-web
  npm start
}

test_and_lint() {
  echo Starting sbt tests...
  sbt rocknrollCore/test
  echo Linting web...
  cd rocknroll-web
  ng lint --fix=true
}

if [[ $1 == "compile" ]]
then
  compile
elif [[ $1 == "server" ]]
then
  start_server
elif [[ $1 == "web" ]]
then
  start_web
elif [[ $1 == "test" ]]
then
  test_and_lint
fi