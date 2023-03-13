package com.example.TicTacToe;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Board {
    int n;
    char[][] board;

    Board(int n) {
        this.n = n;
        this.board = new char[n][n];
    }

    void set(Position position, char ch) {
        board[position.x][position.y] = ch;
    }

    void reset(Position position) {

    }

    void printBoard() {
        for(char[] row: board){
            for(char ch: row){
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }
}

class Player {
    char ch;

    Player(char ch) {
        this.ch = ch;
    }
}

class Position {
    int x, y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Move {
    Player player;
    Position position;

    Move(Player player, Position position) {
        this.player = player;
        this.position = position;
    }
}

enum GameStatus {
    NOT_STARTED,
    STARTED,
    FINISHED,
    DRAW
}

public class Game {
    Board board;
    Player player1, player2;
    List<Move> moves;
    GameStatus gameStatus;
    Player winner;
    boolean turn;

    Game(Player p1, Player p2, Board board) {
        this.player1 = p1;
        this.player2 = p2;
        this.board = board;
        this.gameStatus = GameStatus.NOT_STARTED;
        this.moves = new ArrayList<>();
    }

    Player getTurn() {
        return turn ? player1 : player2;
    }

    void start() {
        gameStatus = GameStatus.STARTED;
        Scanner sc = new Scanner(System.in);
        while (!(gameStatus.equals(GameStatus.FINISHED) || gameStatus.equals(GameStatus.DRAW))) {
            Player p = getTurn();
            System.out.println("Player " + p);
            int x = sc.nextInt();
            int y = sc.nextInt();
            makeMove(new Move(p, new Position(x, y)));
            board.printBoard();
            turn = !turn;
            Pair<Player, GameStatus> pg = checkWin();
        }
    }

    Pair<Player, GameStatus> checkWin(){
        return new Pair<>(player1, GameStatus.FINISHED);
    }

    void makeMove(Move move) {
        moves.add(move);
        board.set(move.position, move.player.ch);
    }

    Player getWinner() {
        return winner;
    }

    public static void main(String[] args) {
        Game game = new Game(new Player('X'), new Player('O'), new Board(3));
        game.board.printBoard();
        game.start();
        System.out.println(game.getWinner());
    }
}
