# Holy Oil Game

## Fejlesztési utasítások

### Eclipse IDE

TODO

## Fordítási és futattási utasítások

### Fordítás

Parancssori fordítás Windowson:

1. Navigáljunk el a projekt mappájába (ahol az .iml fájl is van)
2. Feltételezve, hogy a JDK (v1.8<=) már a PATH környezeti változóban elérhető, adjuk meg a következő 2 db parancsot egy parancsértelmezőben (pl.: bash, powershell, cmd):
dir /s /b *.java > .sources
javac @.sources -d out\production\java

Az első parancs egy .sources fájlba kikeresi a .java forráskódok elérési útvonalát, amelyet a második parancs fel is használ a fordításhoz, a lefordított .class fájlokat elérhetjük a projekt gyökérkönyvtárának out alkönyvtárában.
3. Alternatív: futtassuk a compile.bat fájlt, amely ugyanazt a parancsot futtatja le, amelyet fent is említettünk.

### Futtatás

Parancssori futtatás fordítás után Windowson:

1. Feltételezve van, hogy a projekt könyvtárában megtörtént a fordítás (lásd feljebb).
2. A projekt könyvtárában a parancsértelmezőjével futtassa a következő parancsot:
java -cp out\production\java hu.holyoil.Main
3. Alternatív: futtassa a run.bat fájlt, amely ugyanazt a parancsot futtatja le, amelyet fent is említettünk.
