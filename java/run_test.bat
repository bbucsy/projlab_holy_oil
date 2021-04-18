@echo off
java -jar helper.jar -run "java" -runArgs "-cp out\production\java hu.holyoil.Main" -tf %1 -rf %2
pause

