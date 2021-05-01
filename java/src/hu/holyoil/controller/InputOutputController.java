package hu.holyoil.controller;

import hu.holyoil.Main;
import hu.holyoil.commandhandler.Logger;
import hu.holyoil.commandhandler.addneighbourcommand.AddNeighbourCommandHandler;
import hu.holyoil.commandhandler.causesunstormcommand.CauseSunstormCommandHandler;
import hu.holyoil.commandhandler.createcommand.CreateCommandHandler;
import hu.holyoil.commandhandler.docommand.DoCommandHandler;
import hu.holyoil.commandhandler.explodeasteroidcommand.ExplodeAsteroidCommandHandler;
import hu.holyoil.commandhandler.loadcommand.LoadCommandHandler;
import hu.holyoil.commandhandler.statecommand.StateCommandHandler;
import java.io.InputStream;
import java.util.*;

/**
 * A prototípus bemeneti nyelvét feldolgozó singleton parancsértelmező objektum.
 * Megállapítja melyik parancsot adta ki a felhasználó, majd megcsinálja a
 * szükséges beállításokat, esetleg meghívja egy specifikus parancs
 * értelemzőjének megfelelő függvényét.
 */
public class InputOutputController {

  /**
   * Statikus singleton példány.
   */
  private static InputOutputController inputOutputController;

  /**
   * Egy lista a lehetséges kiadott parancsokról.
   */
  private static List<String> commands = Arrays.asList(
      "echo_off", "echo_on", "do", "create", "load", "add_neighbour", "step",
      "cause_sunstorm", "explode_asteroid", "disable_random", "state", "exit",
      "play", "generate");

  /**
   * Levenshtein szövegtávolságot kiszámoló függvény.
   * @param a Az első összevetendő szöveg
   * @param b A második összevetendő szöveg
   * @return A két input Levenshtein távolsága
   */
  private static int Distance(String a, String b) {
    int[][] d = new int[a.length() + 1][b.length() + 1];
    for (int i = 0; i < a.length() + 1; i++) {
      for (int j = 0; j < b.length() + 1; j++) {
        if (i == 0)
          d[0][j] = j;
        else if (j == 0)
          d[i][0] = i;
        else
          d[i][j] = Collections.min(Arrays.asList(
              d[i - 1][j] + 1, d[i][j - 1] + 1,
              d[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1)));
      }
    }
    return d[a.length()][b.length()];
  }

  /**
   * Az osztály singleton példányát léehet elérni vele.
   * @return Az osztály statikus példánya.
   */
  public static InputOutputController GetInstance() {
    if (inputOutputController == null) {
      inputOutputController = new InputOutputController();
    }

    if (Logger.GetName(inputOutputController) == null) {
      Logger.RegisterObject(inputOutputController, ": InputOutputController");
    }

    return inputOutputController;
  }

  /**
   * Értelmez egy parancs sorozatot.
   * Soronként értelemezi a parancsokat, egészen addig amíg az InputStream
   * végére nem ér.
   * @param inputStream Az inputstream, ahol a parancsok érkeznek.
   */
  public void ParseCommand(InputStream inputStream) {
    Scanner scanner = new Scanner(inputStream);
    boolean isRunning = true;
    boolean isPlayMode = false;

    while (isRunning && scanner.hasNextLine()) {
      String line = scanner.nextLine();
      boolean commandSuccess = true;

      if (line.length() <= 0) {
        continue;
      }

      String[] command = line.split(" ");
      switch (command[0]) {
        // részletes logolás kikapcsolása
      case "echo_off": {
        Logger.SetEnabled(false);
        break;
      }
      // részletes logolás bekapcsolása
      case "echo_on": {
        Logger.SetEnabled(true);
        break;
      }
      // Játékos karakter léptetése
      case "do": {
        commandSuccess = new DoCommandHandler().Handle(line);
        break;
      }
      // Új objektum létrehozása
      case "create": {
        commandSuccess = new CreateCommandHandler().Handle(line);
        break;
      }
      // Pálya betöltése
      case "load": {
        commandSuccess = new LoadCommandHandler().Handle(line);
        break;
      }
      // Aszteroida szomszédság beállítása
      case "add_neighbour": {
        commandSuccess = new AddNeighbourCommandHandler().Handle(line);
        break;
      }
      // Játék léptetése
      case "step": {
        AIController.GetInstance().Step();
        SunController.GetInstance().Step();
        GameController.GetInstance().Step();
        break;
      }
      // Napvihar elindítása
      case "cause_sunstorm": {
        commandSuccess = new CauseSunstormCommandHandler().Handle(line);
        break;
      }
      // Aszteroida felrobbantása
      case "explode_asteroid": {
        commandSuccess = new ExplodeAsteroidCommandHandler().Handle(line);
        break;
      }
      // Véletlenszerű viselkedés kikapcsolása
      case "disable_random": {
        Main.isRandomEnabled = false;
        SunController.GetInstance().SetCountdown(30);
        break;
      }
      // Aktuális állapot kiírása
      case "state": {
        boolean temp = Logger.IsEnabled();
        Logger.SetEnabled(true);
        commandSuccess = new StateCommandHandler().Handle(line);
        Logger.SetEnabled(temp);
        break;
      }
      // Kilépés
      case "exit": {
        isRunning = false;
        break;
      }
      // Teszt módból játék módba váltás
      case "play": {
        isPlayMode = true;
        Logger.SetEnabled(false);
        break;
      }
      // Egy új pálya generálása
      case "generate": {
        boolean temp = Logger.IsEnabled();
        Logger.SetEnabled(false);
        GameController.GetInstance().StartGame(3);
        Logger.SetEnabled(temp);
        break;
      }
      // Nem felismert parancs
      default: {
        commandSuccess = false;
        break;
      }
      }
      // sikertelen parancsok kezelése
      if (!commandSuccess) {
        if (isPlayMode) {
          // play mode -> Javasoljuk a legjobban hasonlító parancsot
          System.out.println("Command not recognised.");
          String closest = Collections.min(
              commands, Comparator.comparingInt(s -> Distance(s, line)));
          System.out.println("\t Did you mean: " + closest + " ?");
        } else {
          // not in playmode -> Kilépünk egy hibaüzenettel
          System.out.println("Command not recognized: " + line.split(" ")[0]);
          isRunning = false;
        }
      }
    }
  }
}
