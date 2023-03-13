package com.example.SnakeLadder;

public class Game {

//    Board board;
//    Dice dice;
//    Queue<Player> players;
//    GameStatus staus;
//
//    public Game(Board board, Dice dice)
//    {
//        this.board = board;
//        this.dice = dice;
//        this.players = new LinkedList<Player>();
//        this.status = GameStatus.NOT_STARTED;
//    }
//
//    public void startGame()
//    {
//        this.status = GameStatus.RUNNING;
//        board.printBoard();
//
//        // Run until we have only 1 player left on the board
//        while(players.size() > 1)
//        {
//            Player player = players.poll();
//
//            makeMove(currPlayer);
//
//            if(player.getPosition() == board.getTotalCells())
//                System.out.println(player.getName() + " has completed the game!");
//            else
//                players.add(player);
//        }
//
//        this.status = GameStatus.FINISHED;
//
//    }
//
//    private void makeMove(Player player) {
//
//        System.out.println();
//        System.out.println(currPlayer.getUserName()+"'s turn.");
//        System.out.println("Press anything to roll the dice.");
//
//        Scanner sc = new Scanner(System.in);
//        char c = sc.next().charAt(0);
//
//        int playerPosition = player.getPosition();
//        int rollValue = dice.roll();
//
//        int targetPosition = playerPosition + rollValue;
//
//        if(targetPosition > board.getTotalCells())
//            System.out.println("Invalid Move");
//        else
//        {
//            if(board.hasSpecialEntity(targetPosition))
//            {
//                targetPosition = board.getSpecialEntity(targetPosition).getEndPosition();
//            }
//        }
//
//        player.setPosition(targetPosition);
//
//    }
//
//    public void addPlayers(List<Player> all_players)
//    {
//        if(this.status == GameStatus.NOT_STARTED)
//        {
//            for(Player player : all_players)
//                this.players.add(player);
//        }
//        else
//            throw new GameAlreadyStartedException("Can't add players after game started")
//
//    }
}