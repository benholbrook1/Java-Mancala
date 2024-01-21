package ui;

import mancala.KalahRules;
import mancala.AyoRules;
import mancala.GameRules;

import mancala.MancalaGame;
import mancala.Player;
import mancala.Saver;
import java.util.Scanner;

import mancala.InvalidMoveException;
import mancala.GameNotOverException;

import java.io.IOException;

public class TextUI {

    private Scanner sc;
    private MancalaGame game;

    public TextUI(){
        sc = new Scanner(System.in);
        game = new MancalaGame();
    }

    public MancalaGame getGame(){
        return game;
    }

    void setGameState(MancalaGame gameState){
        game = gameState;
    }

    void setRules(GameRules ruleSet){
        getGame().setRules(ruleSet);
    }

    public MancalaGame setUpPlayers(){

        String playerOneName = "";
        String playerTwoName = "";

        Player playerOne = null;
        Player playerTwo = null;

        MancalaGame gameState = getGame();

        System.out.println("Welcome to Mancala!");
        System.out.print("Player 1: ");
        // here is where we would get the 2 players --> either load profile or create new profile based on the users name --> Implement this with the GUI
        playerOneName = sc.nextLine();
        System.out.print("Player 2: ");
        playerTwoName = sc.nextLine();

        playerOne = new Player(playerOneName);
        playerTwo = new Player(playerTwoName);

        gameState.setPlayers(playerOne, playerTwo);

        return gameState;

    }

    public int getMove() {

        boolean isValidNumber = false;
        int move = -1;
        MancalaGame gameState = getGame();

        System.out.println(gameState.getCurrentPlayer().toString() + "'s Move");
        System.out.print("Enter a pit number (or 'q' to quit): ");

        while (!isValidNumber) {
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("Quitting the program.");
                giveOptionToSave();
                System.exit(0); // Terminate the program
            } else {
                try {
                    move = Integer.parseInt(input);
                    isValidNumber = true;
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid Input. Try again.\n");
                    System.out.print("Enter a pit number (or 'q' to quit): ");
                }
            }
        }

        return move;
    }

    private void giveOptionToSave(){

        boolean validAnswer = false;
        String input;

        while(!validAnswer){
            System.out.print("Would you like to save the game? (Y or N): ");

            input = sc.next();

            if (input.equalsIgnoreCase("y")){
                saveGame();
                validAnswer = true;
            } else if (input.equalsIgnoreCase("n")){
                validAnswer = true;
            } else {
                System.out.println("\nInvalid Answer, Try again\n");
            }
        }
    }

    private void saveGame(){

        String fileName;

        clearScreen();
        System.out.print("Enter a name for your save file: ");

        fileName = sc.next();

        try{
            Saver.saveObject(getGame(), fileName + ".gameSave");
        }
        catch (IOException ex){
            System.out.println(ex);
        }

        
    }

    public boolean giveOptionToLoad(){

        boolean didLoad = false;
        boolean validAnswer = false;
        String input;

        while(!validAnswer){
            System.out.print("Would you like to load a previous game save? (Y or N): ");

            input = sc.nextLine();

            if (input.equalsIgnoreCase("y")){
                getSave();
                didLoad = true;
                validAnswer = true;
            } else if (input.equalsIgnoreCase("n")){
                validAnswer = true;
            } else {
                System.out.println("\nInvalid Answer, Try again\n");
            }
        }
        return didLoad;
    }

    private void getSave(){

        boolean validAnswer = false;
        String input;

        while(!validAnswer){

            System.out.print("Enter the name of your save: ");
            input = sc.nextLine();

            if (input.equals("d")){
                setGameState(new MancalaGame());
                break;
            }

            try{
                setGameState((MancalaGame) Saver.loadObject("assets/" + input + ".gameSave"));
                validAnswer = true;
            } catch (IOException ex){
                System.out.println("Error, IOException Caught, starting a new game");
                setGameState(new MancalaGame());
                validAnswer = true;
            }
        }
    } 

    public void makeMove(int move){

        boolean isValidMove = false;

        while(!isValidMove){
            try {
                game.move(move);
                game.updateCurrentPlayer();
                isValidMove = true;
            } catch (InvalidMoveException e){
                System.out.println("\nMove was not valid. Try Again.\n");
                move = getMove();
            }
        }
    }

    public void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public void printBoard(){
        System.out.print(getGame().toString());
    }

    public void getRuleSet(){

        boolean isValidRules = false;
        String input;

        while(!isValidRules){

            System.out.print("Which Game Rules Would You Like to Play With? (kalah, ayo): ");
            input = sc.nextLine();

            if (input.equalsIgnoreCase("kalah")){
                setRules(new KalahRules());
                isValidRules = true;
            } else if (input.equalsIgnoreCase("ayo")){
                setRules(new AyoRules());
                isValidRules = true;
            } else if (input.equalsIgnoreCase("q")){
                System.out.println("Quitting the program.");
                System.exit(0);
            } else {
                System.out.println("\nInvalid Rules, try again\n");
            }
        }
    }

    public static void main(String[] args) {

        TextUI ui = new TextUI();
        Player winner = null;
        boolean didLoadASave = false;

        didLoadASave = ui.giveOptionToLoad();

        if (!didLoadASave){
            ui.clearScreen();
            ui.setGameState(ui.setUpPlayers());
            ui.clearScreen();
            ui.getRuleSet();
        }

        while(!ui.getGame().isGameOver()){

            ui.printBoard();

            int move = ui.getMove();
            ui.makeMove(move);

            ui.clearScreen();
        }

        try {
            winner = ui.getGame().getWinner();
        } catch (GameNotOverException e){
            System.out.println(e);
        }

        ui.printBoard();

        System.out.println("||||||||||||||||||");
        System.out.println("||||Game Over!||||");
        System.out.println("||||||||||||||||||\n");
        if (winner != null){
            System.out.println(winner.toString() + " Won!");
        } else {
            System.out.println("Tie Game!");
        }
        ui.sc.close();

    }

}