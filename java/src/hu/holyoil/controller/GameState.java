package hu.holyoil.controller;

public enum GameState {
    RUNNING(""), WON_GAME("WON GAME"), LOST_GAME("LOST GAME");

    private final String status;

    GameState(String _status) {
        status = _status;
    }

    @Override
    public String toString() {
        return status;
    }
}
