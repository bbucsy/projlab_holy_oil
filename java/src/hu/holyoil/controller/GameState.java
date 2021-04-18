package hu.holyoil.controller;

/**
 * A játék egy állapotát leíró enum. Három állapota lehet egy játéknak: Éppen
 * fut, a játékosok nyertek, a játékosk vesztettek
 */
public enum GameState {
  RUNNING(""),
  WON_GAME("WON GAME"),
  LOST_GAME("LOST GAME");

  /**
   * Beszédesebb állapotnevek kiírásához használt string.
   */
  private final String status;

  /**
   * Konstruktor, beállítjuk a toString() híváskor kiírandó státusz szöveget
   * @param _status Kiírandó státusz szöveg
   */
  GameState(String _status) { status = _status; }

  /**
   * Beszédes státusz szövegek kiírása
   */
  @Override
  public String toString() {
    return status;
  }
}
