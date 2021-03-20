@ECHO OFF
dir /s /b *.java > .sources
javac @.sources -d out\production\java
ECHO Compilation successful!
PAUSE
