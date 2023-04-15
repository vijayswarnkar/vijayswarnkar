package com.example.TicTacToe;

import java.util.List;

class Position {

}
class Board {
    void mark(Position position, Player player) {

    }
}
class Player {
    
}
class History {

}
interface Strategy {
    boolean isWinning(Game game);
}
class BasicStrategy implements Strategy {
    public boolean isWinning(Game game){
        return false;
    }
}
enum GameStatus {
    NOT_STARTED,
    STARTED,
    WON,
    DRAW
}
public class Game {
    Board board;
    List<Player> players;    
    History history;
    Strategy strategy;
    int turn;
    GameStatus status;

    Game(){
        
    }
    void replay() {

    }

    void display() {

    }

    void mark(Position position){

    }

    void start(){

    }
}

class Runner {
    public static void main(String[] args) {
        System.out.println("Game starting...");
        Game game = new Game();        
    }
}