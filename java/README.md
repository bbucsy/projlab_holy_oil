# Holy Oil Game

## Projekt leírása

* JDK version: 1.8
* Recommended IDE: Jetbrains IntelliJ IDEA 
	* version: 2019.1 <=

## Fejlesztési utasítások

### Eclipse IDE

1. File -> Import... -> Existing projects into workspace
2. Keresse ki a projekt könyvtárát
3. Pipálja be a java nevezetű projektet
4. Finish
5. Run Configuration beállításánál keressük ki a Main osztályt mint a main method helyét.

### IntelliJ IDEA

1. Nyissa meg a projektmappát (ahol az .imp fájl található) az IDEA segítségével
2. Az Add Configuration... menügomb megnyomása után állítsa be a projekt Main osztályát az applikáció futására, válassza ki a Java 1.8 verziót a JDK-ra, és válassza ki a java modult.

## Fordítási és futattási utasítások kézzel

### Fordítás

Parancssori fordítás Windowson:

1. Navigáljunk el a projekt mappájába (ahol az .iml fájl is van)
2. Feltételezve, hogy a JDK (v1.8<=) már a PATH környezeti változóban elérhető, adjuk meg a következő 2 db parancsot egy parancsértelmezőben (pl.: bash, powershell, cmd):
	* dir /s /b *.java > .sources
	* javac @.sources -d out\production\java -encoding utf8
3. Az első parancs egy .sources fájlba kikeresi a .java forráskódok elérési útvonalát, amelyet a második parancs fel is használ a fordításhoz, a lefordított .class fájlokat elérhetjük a projekt gyökérkönyvtárának out alkönyvtárában.
4. Alternatív: futtassuk a compile.bat fájlt, amely ugyanazt a parancsot futtatja le, amelyet fent is említettünk.

### Futtatás

Parancssori futtatás fordítás után Windowson:

1. Feltételezve van, hogy a projekt könyvtárában megtörtént a fordítás (lásd feljebb).
2. A projekt könyvtárában a parancsértelmezőjével futtassa a következő parancsot:
java -cp out\production\java hu.holyoil.Main
3. Alternatív: futtassa a run.bat fájlt, amely ugyanazt a parancsot futtatja le, amelyet fent is említettünk.

## Tesztelés

A testdir mappában test_*.txt fájlnévvel léteznek a dokumentált teszteseteink, ahol a * a teszteset megnevezését referálja.
Ugyanezen mappában res_*.txt fájlnévvel léteznek a dokumentált tesztesetek várt eredményei.

### Tesztek futtatása (Windowson)

#### Egy teszt futtatása

1. Fő könyvtárában megnyitunk egy parancssort.
2. Kiadjuk `run_test.bat <teszt fájl útvonal> <elvárt kimenet fájl útvonal>` parancsot.

#### Összes teszt futtatása

1. Fő könyvtárában megnyitunk egy parancssort.
2. Kiadjuk `run_all_tests.bat` parancsot.

### Teszt kimenetének egyéni vizsgálata

Parancssorban Windowson (fordítás után):

0. Írja meg a tesztet a testdir mappában test_*.txt fájlnévvel, ahol a * helyére mehet a teszteset megnevezése ízlés szerint.
1. Feltételezve van, hogy a projekt könyvtárában megtörtént a fordítás (lásd feljebb).
2. A projekt könyvtárában a parancsértelmezőjével futtassa a következő parancsot (a * helyére az elkészült tesztfájl megnevezése kerül):
run.bat < test_*.txt
3. Ha esetleg ki szeretné vezetni másik fájlba az eredményt, futtassa így:
run.bat < test_*.txt > manual_res_*.txt

## Egyéb segédparancsok

1. Fájllista generálása bash-ben (git bash vagy linux bash):
find . \( -path ./testdir -o -path ./.idea -o -path ./out -o -path ./src/hu/holyoil/skeleton \) -prune -o -type f -printf '%f\t%s\t%CF\n' > filelist.txt
2. Ezt bemásolod a txt-fájlból egy excel táblába, majd onnan másolod át a doksiba. (formázás ne zavarjon)
2. Ha van git a gépeden, kell legyen git bash-ed is, add ki ezt a parancsot cmd.exe-ben:
"%PROGRAMFILES%/git/usr/bin/bash.exe" -i -l
