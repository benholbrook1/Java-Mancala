package ui;

import mancala.MancalaGame;
import mancala.Player;
import mancala.Saver;
import mancala.UserProfile;

import mancala.PitNotFoundException;
import mancala.InvalidMoveException;
import mancala.GameNotOverException;
import java.io.IOException;

import java.lang.StringBuilder;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.Color;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.nio.file.Files;



public class MancalaUI extends JFrame{
    
    private JPanel gameScreen;
    private JPanel gameBoard;
    private JPanel infoPanel;
    private JLabel startMessage;
    private JLabel gameVersionLabel;
    private JLabel currentPlayerLabel;
    private JLabel playerOneNameLabel;
    private JLabel playerTwoNameLabel;
    private JButton playButton;
    private JButton helpButton;
    private PositionAwareButton pitButtons[][];
    private PositionAwareButton storeButtons[];
    private JMenuBar menuBar;
    private MancalaGame game;
    private UserProfile playerOneProfile = new UserProfile("default", 0, 0);
    private UserProfile playerTwoProfile = new UserProfile("default", 0, 0);
    private JFileChooser profileSelection;

    public MancalaUI(String title){
        super();
        windowSetUp(title);
        setUpStartScreen();

        pack();
    }

    private void windowSetUp(String title){

        this.setTitle(title);
        gameScreen = new JPanel();
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void setUpStartScreen(){

        gameScreen.add(startMessage());
        add(gameScreen, BorderLayout.NORTH);

        gameScreen.add(playButton());
        add(gameScreen, BorderLayout.WEST);

        gameScreen.add(helpButton());
        add(gameScreen, BorderLayout.EAST);

    }

    private JLabel startMessage(){
        startMessage = new JLabel("Welcome To Mancala");
        return startMessage;
    }

    private JButton playButton(){
        playButton = new JButton("PLAY");
        playButton.addActionListener(e -> playGame());
        return playButton;
    }

    private JButton helpButton(){
        helpButton = new JButton("HELP");
        helpButton.addActionListener(e -> helpScreen());
        return helpButton;
    }

    private void helpScreen(){

        String mancalaGameDescription = "This is a simple implementation of the board game Mancala. There is a board with 2 sides and\n" +
        "6 \"pits\" per side, there are also 2 \"stores\" on either side of the board.\n" +
        "Each pit will start with 4 stones, and the stores start empty. The goal is to get the most amount of stones in\n" +
        "your store. Each move a player may choose a pit from their side of the board to distribute stones from.\n" +
        "\n" +
        "This game has 2 different rule sets that you can choose from; select \"CHANGE GAME RULES\" from the menu in the\n" +
        "top left of the screen to change to a new rule set. The rules are as follows:\n" +
        "\n" +
        "Kalah Rules:\n" +
        "To start the game, a player initiates by selecting all pieces from any hole on their side. Progressing in a\n" +
        "counter-clockwise direction, the player places one stone in each hole until the stones are depleted. Adhere to\n" +
        "the following rules during play:\n" +
        "1. If you encounter your own store, deposit one piece in it. If it's your opponent's store, skip it.\n" +
        "2. If the last piece placed is in your own store, earn a bonus turn.\n" +
        "3. If the concluding piece lands in an empty hole on your side, capture that piece and any pieces in the\n" +
        "directly opposite hole.\n" +
        "4. All captured pieces must be placed in your store. The game concludes when all six spaces on one side of the\n" +
        "Mancala board are vacated. The player with the most pieces on their side at the end wins.\n" +
        "\n" +
        "Ayoayo Rules:\n" +
        "Playing Ayoayo follows a structure akin to Kalah, with similar game-ending conditions. However, there are\n" +
        "distinctions in stone distribution:\n" +
        "1. While distributing stones, exclude the starting pit. If there are enough stones to circumnavigate the board\n" +
        "without reaching the starting pit on the second pass, leave the starting pit empty.\n" +
        "2. After distributing stones, if the final stone lands in a pit on either side with existing stones, collect\n" +
        "and redistribute all stones in that pit. This multi-lap play persists until the last seed falls into an empty\n" +
        "pit, concluding the turn unless a capture occurs.\n" +
        "3. Capture occurs when the last stone lands in an empty pit on the player's side, and the opposite pit holds\n" +
        "stones. In this scenario, capture all opponent stones from the opposite pit, placing them in the player's\n" +
        "store. The player's final stone remains in play. If the opposite pit is empty, or if the final stone lands\n" +
        "on the opponent's side, no capture occurs, and the turn concludes without capturing any stones.\n" +
        "\n" +
        "To make a move, click the pit that you would like to move stones from; if it is a legal move, the turn will be played\n";


        JOptionPane.showMessageDialog(null,mancalaGameDescription, "HELP", JOptionPane.INFORMATION_MESSAGE);

    }

    private void playGame(){

        game = new MancalaGame();

        loadProfiles();

        gameScreen.removeAll();

        makeMenu();
        setJMenuBar(menuBar);

        gameBoard = makeBoard(6,2);
        gameScreen.add(gameBoard);

        pack();
        updateView();
    }

    private void loadProfiles(){

        int selection = 0;
        JOptionPane loadProfile = new JOptionPane();
        String[] options = {"LOAD PROFILE", "CREATE NEW"};
        String playerNames[] = {"", ""};
        
        // IF ASSETS FOLDER IS EMPTY WE SHOULD MAKE THEM JUST HAVE TO CREATE PLAYERS
        Path currentDir = Paths.get(System.getProperty("user.dir"));
        String folderName = "assets/";
        Path assetsFolderPath = currentDir.resolve(folderName);

        if (!Files.exists(assetsFolderPath)){
            for(int i = 0; i < 2; i++){
                try {
                    playerNames[i] = getPlayerName();
                    createProfile(playerNames[i], i + 1);
                } catch (IOException ex){
                }
            }
        } else {
            for(int i = 0; i < 2; i++){
                selection = loadProfile.showOptionDialog(null, String.format("Would you like to load a player profile, or create a new profile for Player %d?", i + 1), String.format("Player %d", i + 1), 1, 2, null, options, options[0]);
                if (selection == 0){
                    try{
                        loadProfile(i + 1);
                    } catch (IOException ex){
                        JOptionPane.showMessageDialog(null, "Error, could not load files, creating new file", "Error", JOptionPane.INFORMATION_MESSAGE);
                        try {
                            playerNames[i] = getPlayerName();
                            createProfile(playerNames[i], i + 1);
                        } catch (IOException e){
                        }
                    }
                } else if (selection == 1){
                    try {
                        playerNames[i] = getPlayerName();
                        createProfile(playerNames[i], i + 1);
                    } catch (IOException ex){
                    }
                }
            }
        }

        playerNames[0] = playerOneProfile.getName(); 
        playerNames[1] = playerTwoProfile.getName();

        Player playerOne = new Player(playerNames[0]);
        Player playerTwo = new Player(playerNames[1]);

        game.setPlayers(playerOne, playerTwo); 
    }

    private void createProfile(String playerName, int playerNum) throws IOException{

        String fileName = playerName + ".user";

        if (playerNum == 1){
            playerOneProfile = new UserProfile(playerName, 0, 0);
            Saver.saveObject(playerOneProfile, fileName);
        } else if (playerNum == 2){
            playerTwoProfile = new UserProfile(playerName, 0, 0);
            Saver.saveObject(playerTwoProfile, fileName);
        }
    }

    private void loadProfile(int playerNum) throws IOException{

        Path currentDir = Paths.get(System.getProperty("user.dir"));
        String folderName = "assets/";
        Path assetsFolderPath = currentDir.resolve(folderName);

        FileFilter filter = new FileNameExtensionFilter("Player Save", "user");

        profileSelection = new JFileChooser(assetsFolderPath.toString());
        profileSelection.setFileFilter(filter);

        int returnValue = profileSelection.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String fileName = profileSelection.getSelectedFile().getName();
            if (playerNum == 1){ 
                playerOneProfile = (UserProfile) Saver.loadObject(fileName);
            } else if (playerNum == 2){
                playerTwoProfile = (UserProfile) Saver.loadObject(fileName);
            }
        }
    }

    private String getPlayerName(){

        JOptionPane getName = new JOptionPane();
        String name = getName.showInputDialog("Enter your Name");
        if (name == null){
            name = "default";
        }
        return name;
    }

    private JPanel makeBoard(int width, int height){

        JPanel board = new JPanel();
        JPanel pits = new JPanel();

        board.setLayout(new BorderLayout());

        storeButtons = new PositionAwareButton[2];
        storeButtons[0] = new PositionAwareButton(String.format("%d", game.getStoreCount(1)));
        storeButtons[1] = new PositionAwareButton(String.format("%d", game.getStoreCount(2)));

        pitButtons = new PositionAwareButton[height][width];
        pits.setLayout(new GridLayout(height, width));

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int pitNum = getPitNum(y, x);
                try {
                    pitButtons[y][x] = new PositionAwareButton(String.format("%d", game.getNumStones(pitNum)));
                    pitButtons[y][x].setPosition(pitNum);
                    pitButtons[y][x].addActionListener(e -> {takeTurn(e);});
                    pits.add(pitButtons[y][x]);
                } catch (PitNotFoundException ex){
                }
            }
        }

        JPanel gameInfo = loadGameInfo();

        board.add(storeButtons[0], BorderLayout.EAST);
        board.add(storeButtons[1], BorderLayout.WEST);
        board.add(pits, BorderLayout.CENTER);
        board.add(gameInfo, BorderLayout.SOUTH);

        return board;
    }

    private JPanel loadGameInfo(){

        infoPanel = new JPanel();

        Border blackBorder = BorderFactory.createLineBorder(Color.black);
        infoPanel.setBorder(blackBorder);

        JPanel infoPanelUpper = new JPanel();
        JPanel infoPanelLower = new JPanel();

        infoPanelUpper.setLayout(new BorderLayout());
        infoPanelLower.setLayout(new BorderLayout());
        infoPanel.setLayout(new BorderLayout());

        playerOneNameLabel = new JLabel(String.format("Player One: %s  ", playerOneProfile.getName()));
        playerTwoNameLabel = new JLabel(String.format("  Player Two: %s", playerTwoProfile.getName()));

        gameVersionLabel = new JLabel(String.format("Game Rules: %s  ", game.getRulesName()));
        currentPlayerLabel = new JLabel(String.format("  %s's Turn", game.getCurrentPlayer().toString()));

        infoPanelUpper.add(playerOneNameLabel, BorderLayout.EAST);
        infoPanelUpper.add(playerTwoNameLabel, BorderLayout.WEST);
        infoPanelLower.add(gameVersionLabel, BorderLayout.EAST);
        infoPanelLower.add(currentPlayerLabel, BorderLayout.WEST);

        infoPanel.add(infoPanelUpper, BorderLayout.NORTH);
        infoPanel.add(infoPanelLower, BorderLayout.SOUTH);

        return infoPanel;
    }

    private void takeTurn(ActionEvent e){
        PositionAwareButton clicked = (PositionAwareButton) (e.getSource());
        try{
            game.move(clicked.getPosition());
            updateBoard();
            updateInfo();
            checkGameOver();
        } catch (InvalidMoveException ex){
        }
    }

    private void checkGameOver(){

        if (game.isGameOver()){

            JOptionPane gameOverScreen = new JOptionPane();
            JOptionPane askToSave = new JOptionPane();
            int menuSelection;
            int saveSelection;
            String options [] = {"NEW GAME", "MENU"};
            Player winner;

            try{
                winner = game.getWinner();
                updateBoard();
            } catch (GameNotOverException ex) {
                winner = new Player("tie");
            }

            if (winner.getName() == "tie"){
                menuSelection = gameOverScreen.showOptionDialog(null, String.format("Game Over! Result: Tie Game!"), "GAME OVER", 1, 2, null, options, options[0]);
            } else {
                menuSelection = gameOverScreen.showOptionDialog(null, String.format("Game Over! Result: %s wins!", winner.getName()), "GAME OVER", 1, 2, null, options, options[0]);
            }

            saveSelection = askToSave.showConfirmDialog(null, "Would you like to save this game to each player's stats?", "SAVE STATS", JOptionPane.YES_NO_OPTION);

            if (saveSelection == 0){
                saveStats(winner);
            }

            if (menuSelection == 0){
                playGame();
            } else {
                toMenu();

            }
        }
    }

    private void saveStats(Player winner){

        String version = game.getRulesName();

        if (version == "kalah"){
            playerOneProfile.incrementKalahPlayed();
            playerTwoProfile.incrementKalahPlayed();

            if (winner.getName() == playerOneProfile.getName()){
                playerOneProfile.incrementKalahWins();
                playerTwoProfile.incrementKalahLosses();
            } else if (winner.getName() == playerTwoProfile.getName()){
                playerOneProfile.incrementKalahLosses();
                playerTwoProfile.incrementKalahWins();
            }

        } else if (version == "ayo"){
            playerOneProfile.incrementAyoPlayed();
            playerTwoProfile.incrementAyoPlayed();

            if (winner.getName() == playerOneProfile.getName()){
                playerOneProfile.incrementAyoWins();
                playerTwoProfile.incrementAyoLosses();
            } else if (winner.getName() == playerTwoProfile.getName()){
                playerOneProfile.incrementAyoLosses();
                playerTwoProfile.incrementAyoWins();
            }
        }
        try{
            Saver.saveObject(playerOneProfile, playerOneProfile.getName() + ".user");
            Saver.saveObject(playerTwoProfile, playerTwoProfile.getName() + ".user");
        } catch (IOException ex){
            JOptionPane errorMessage = new JOptionPane();
            errorMessage.showMessageDialog(null, "Error! Files could not be saved!");
        }
    }

    private void updateBoard(){

        for(int y = 0; y < 2; y++){
            for(int x = 0; x < 6; x++){
                try {
                    int pitNum = getPitNum(y, x);
                    pitButtons[y][x].setText(String.format("%d", game.getNumStones(pitNum)));
                } catch (PitNotFoundException ex){
                }
            }
        }
        for(int i = 1; i <= 2; i++){
            storeButtons[i - 1].setText(String.format("%d", game.getStoreCount(i)));
        }
    }

    private void updateInfo(){

        playerOneNameLabel.setText(String.format("Player One: %s  ", playerOneProfile.getName()));
        playerTwoNameLabel.setText(String.format("  Player Two: %s", playerTwoProfile.getName()));
        
        gameVersionLabel.setText(String.format("Game Rules: %s  ", game.getRulesName()));
        currentPlayerLabel.setText(String.format("  %s's Turn", game.getCurrentPlayer().toString()));
    }

    private void makeMenu(){

        menuBar = new JMenuBar();
        JMenu menu = new JMenu("MENU");
        JMenuItem saveGame = new JMenuItem("SAVE GAME");
        saveGame.addActionListener(e -> {saveGame(e);});
        JMenuItem loadGame = new JMenuItem("LOAD GAME");
        loadGame.addActionListener(e -> {loadGame(e);});
        JMenuItem viewPlayerStats = new JMenuItem("STATS");
        viewPlayerStats.addActionListener(e -> {viewStats(e);});
        JMenuItem gameVersion = new JMenuItem("CHANGE GAME RULES");
        gameVersion.addActionListener(e -> {changeGameRules(e);});
        JMenuItem quit = new JMenuItem("QUIT");
        quit.addActionListener(e -> {toMenu();});

        menu.add(saveGame);
        menu.add(loadGame);
        menu.add(viewPlayerStats);
        menu.add(gameVersion);
        menu.add(quit);
        
        menuBar.add(menu);

    }

    private void toMenu(){

        getContentPane().removeAll();

        menuBar.removeAll();
        gameScreen.removeAll();
        gameBoard.removeAll();
        windowSetUp("Mancala");
        setUpStartScreen();
        updateView();

        pack();


    }

    private void loadGame(ActionEvent e){

        Path currentDir = Paths.get(System.getProperty("user.dir"));
        String folderName = "assets/";
        Path assetsFolderPath = currentDir.resolve(folderName);

        FileFilter filter = new FileNameExtensionFilter("Game Save", "sav");

        profileSelection = new JFileChooser(assetsFolderPath.toString());
        profileSelection.setFileFilter(filter);

        int returnValue = profileSelection.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String fileName = profileSelection.getSelectedFile().getName();
            
            try {
                game = (MancalaGame) Saver.loadObject(fileName);
                updateBoard();
                updateProfiles();
                updateInfo();
            } catch (IOException ex){
                JOptionPane.showMessageDialog(null, "Error could not load that file as a game", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void updateProfiles(){

        try{
            playerOneProfile = (UserProfile) Saver.loadObject(game.getPlayerOne().getName() + ".user");
            playerTwoProfile = (UserProfile) Saver.loadObject(game.getPlayerTwo().getName() + ".user");
        } catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Error failed to load old user files, overwritting to current players.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }


    }

    private void saveGame(ActionEvent e){

        String saveName = JOptionPane.showInputDialog(null, "Enter a name for your saved game", "SAVE GAME", JOptionPane.QUESTION_MESSAGE);

        try{
            Saver.saveObject(game, saveName + ".sav");
        } catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Error could not save the game", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changeGameRules(ActionEvent e){

        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to change the game rules? (this will cause your current game to be overriden)", "GAME RULES", JOptionPane.YES_NO_OPTION);

        if (response == 0){
            game.startNewGame();
            game.changeRules();
            updateInfo();
            updateBoard();
        }
    }

    private void viewStats(ActionEvent e){

        StringBuilder stats = new StringBuilder();;

        stats.append("------------------------------\n");
        stats.append(playerOneProfile.getName() + " Stats\n");
        stats.append("------------------------------\n");
        stats.append(String.format("Total Games Played = %d\n", playerOneProfile.getAyoPlayed() + playerOneProfile.getKalahPlayed()));
        stats.append(String.format("Total Wins = %d\n", playerOneProfile.getAyoWins() + playerOneProfile.getKalahWins()));
        stats.append(String.format("Total Losses = %d\n\n", playerOneProfile.getAyoLosses() + playerOneProfile.getKalahLosses()));
        stats.append(String.format("Kalah Games Played = %d\n", playerOneProfile.getKalahPlayed()));
        stats.append(String.format("Kalah Wins = %d\n", playerOneProfile.getKalahWins()));
        stats.append(String.format("Kalah Losses = %d\n\n", playerOneProfile.getKalahLosses()));
        stats.append(String.format("Ayo Games Played = %d\n", playerOneProfile.getAyoPlayed()));
        stats.append(String.format("Ayo Wins = %d\n", playerOneProfile.getAyoWins()));
        stats.append(String.format("Ayo Losses = %d\n\n", playerOneProfile.getAyoLosses()));

        stats.append("------------------------------\n");
        stats.append(playerTwoProfile.getName() + " Stats\n");
        stats.append("------------------------------\n");
        stats.append(String.format("Total Games Played = %d\n", playerTwoProfile.getAyoPlayed() + playerTwoProfile.getKalahPlayed()));
        stats.append(String.format("Total Wins = %d\n", playerTwoProfile.getAyoWins() + playerTwoProfile.getKalahWins()));
        stats.append(String.format("Total Losses = %d\n\n", playerTwoProfile.getAyoLosses() + playerTwoProfile.getKalahLosses()));
        stats.append(String.format("Kalah Games Played = %d\n", playerTwoProfile.getKalahPlayed()));
        stats.append(String.format("Kalah Wins = %d\n", playerTwoProfile.getKalahWins()));
        stats.append(String.format("Kalah Losses = %d\n\n", playerTwoProfile.getKalahLosses()));
        stats.append(String.format("Ayo Games Played = %d\n", playerTwoProfile.getAyoPlayed()));
        stats.append(String.format("Ayo Wins = %d\n", playerTwoProfile.getAyoWins()));
        stats.append(String.format("Ayo Losses = %d\n\n", playerTwoProfile.getAyoLosses()));

        JOptionPane.showMessageDialog(null,stats.toString());
    }

    // WARNING --> This will not scale with different sized boards
    private int getPitNum(int y, int x){

        int pitNum = 0;

        if (y == 1){
            pitNum = x + 1;
        } else if (y == 0){
            pitNum = 12 - (x + y);
        }
        return pitNum;
    }

    private void updateView(){
        gameScreen.updateUI();
    }

    /**
     * Our programs main function
     * 
     * @param args the list of arguments given to it by the command line
     */
    public static void main(String[] args){

        MancalaUI ui = new MancalaUI("Mancala");
        ui.setVisible(true);

    }

}

