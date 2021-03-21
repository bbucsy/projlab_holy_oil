@ECHO OFF
dir /s /b *.java > .sources
javac @.sources -d out\production\java -encoding utf8
ECHO Compilation successful!
PAUSE
