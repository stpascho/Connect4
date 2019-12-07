import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import java.io.*;
import java.util.Optional;

public class App extends Application {
    private final int ROWS = 6;
    private final int COLUMNS = 7;
    private char currentPlayer = 'r';
    final int RADIUS = 37;
    char [][] discGrid = new char[ROWS][COLUMNS];
    FileChooser fileChooser = new FileChooser();
    Label message = new Label("Red");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        primaryStage.setTitle("Connect 4");
        Label title = new Label("Connect 4");
        title.setStyle("-fx-font: 36 arial;");
        message.setStyle("-fx-font: 16 arial;");
        message.setTextFill(Color.RED);
        message.setVisible(true);

        GridPane grid = new GridPane();
        GridPane buttons = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        //Fill discGrid with the array from the save file if the user chooses it.
        discGrid = loadGame(primaryStage, discGrid, grid);
        //Take the discs from the array and display them in the UI.
        addDiscFromArray(discGrid, grid);

        //Button for column one.
        Button column0 = new Button("Column 1");
        column0.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int buttonClick = 0;
                currentPlayer = game(currentPlayer, discGrid, buttonClick, message, grid);
                setPlayerLabel(currentPlayer, message);
            }
        });
        //Button for column two.
        Button column1 = new Button("Column 2");
        column1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int buttonClick = 1;
                currentPlayer = game(currentPlayer, discGrid, buttonClick, message, grid);
                setPlayerLabel(currentPlayer, message);
            }
        });
        //Button for column three.
        Button column2 = new Button("Column 3");
        column2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int buttonClick = 2;
                currentPlayer = game(currentPlayer, discGrid, buttonClick, message, grid);
                setPlayerLabel(currentPlayer, message);
            }
        });
        //Button for column four.
        Button column3 = new Button("Column 4");
        column3.setOnAction(new EventHandler<ActionEvent>()  {
            @Override public void handle(ActionEvent e) {
                int buttonClick = 3;
                currentPlayer = game(currentPlayer, discGrid, buttonClick, message, grid);
                setPlayerLabel(currentPlayer, message);
            }
        });
        //Button for column five.
        Button column4 = new Button("Column 5");
        column4.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int buttonClick = 4;
                currentPlayer = game(currentPlayer, discGrid, buttonClick, message, grid);
                setPlayerLabel(currentPlayer, message);
            }
        });
        //Button for column six.
        Button column5 = new Button("Column 6");
        column5.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int buttonClick = 5;
                currentPlayer = game(currentPlayer, discGrid, buttonClick, message, grid);
                setPlayerLabel(currentPlayer, message);
            }
        });
        //Button for column seven.
        Button column6 = new Button("Column 7");
        column6.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int buttonClick = 6;
                currentPlayer = game(currentPlayer, discGrid, buttonClick, message, grid);
                setPlayerLabel(currentPlayer, message);
            }
        });

        //Add column buttons to button grid.
        buttons.add(column0, 0, 2, 1, 1);
        buttons.add(column1, 1, 2, 1, 1);
        buttons.add(column2, 2, 2, 1, 1);
        buttons.add(column3, 3, 2, 1, 1);
        buttons.add(column4, 4, 2, 1, 1);
        buttons.add(column5, 5, 2, 1, 1);
        buttons.add(column6, 6, 2, 1, 1);
        //Add label saying the current player.
        buttons.add(message, 3,0,1,1);
        //Set space between buttons.
        buttons.setHgap(10);
        buttons.setVgap(10);




        //Organize different UI elements in the window.
        AnchorPane.setLeftAnchor(title, 220.0);
        AnchorPane.setRightAnchor(title, 50.0);
        AnchorPane.setTopAnchor(title, 10.0);
        AnchorPane anchor = new AnchorPane(title);
        AnchorPane.setTopAnchor(grid, 150.0);
        AnchorPane.setLeftAnchor(grid, 0.0);
        AnchorPane.setRightAnchor(grid, 0.0);
        AnchorPane.setBottomAnchor(grid, 0.0);
        AnchorPane.setLeftAnchor(buttons, 10.0);
        AnchorPane.setRightAnchor(buttons, 0.0);
        AnchorPane.setBottomAnchor(buttons, 700.0);
        AnchorPane.setTopAnchor(buttons, 60.0);
        anchor.getChildren().add(buttons);
        anchor.getChildren().add(grid);
        anchor.setMinHeight(400);
        anchor.setMinWidth(400);
        //When user tries to close, ask if they want to save.
        primaryStage.setOnCloseRequest((WindowEvent event1) ->{
            saveGame();
        });
        //Set size of window.
        Scene scene = new Scene(anchor, 580, 660);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //Fill array with circles
    public void fillDiscGrid(char[][] discGrid){
        for(int loop = 0; loop < discGrid.length; loop++){
            for(int innerLoop = 0; innerLoop < discGrid[loop].length; innerLoop++){
                discGrid[loop][innerLoop] = 'w';
            }//end inner for
        }//end for
    }//end fillDiscGrid

    //Take circles from array and add to gridpane.
    public void addDiscFromArray(char[][] discGrid, GridPane grid) {
        grid.getChildren().removeAll();
        for (int loop = 0; loop < ROWS; loop++) {
            for (int innerLoop = 0; innerLoop < COLUMNS; innerLoop++) {
                if(discGrid[loop][innerLoop] == 'w') {
                    Circle whiteDisc = new Circle(RADIUS);
                    whiteDisc.setFill(Color.WHITE);
                    grid.add(whiteDisc, innerLoop, loop, 1, 1);
                }else if(discGrid[loop][innerLoop] == 'r') {
                    Circle redDisc = new Circle(RADIUS);
                    redDisc.setFill(Color.RED);
                    grid.add(redDisc, innerLoop, loop, 1, 1);
                }else{
                    Circle yellowDisc = new Circle(RADIUS);
                    yellowDisc.setFill(Color.YELLOW);
                    grid.add(yellowDisc, innerLoop, loop, 1, 1);
                }

            }
        }

    }

    //change current player
    public char changePlayer(char currentPlayer){
        if(currentPlayer == 'r'){
            return 'y';
        }else{
            return 'r';
        }
    }
    //Add a piece to the next available space.
    public boolean addPiece(char[][] discGrid, int buttonClick, char currentPlayer, Label message) {
        boolean result = false;
        if (discGrid[0][buttonClick] != 'w') {
            Alert fullColumn = new Alert(Alert.AlertType.INFORMATION);
            fullColumn.setTitle("Try Again");
            fullColumn.setHeaderText(null);
            fullColumn.setContentText("This column is full. Please choose another.");
            fullColumn.showAndWait();
            result = false;
        }
        for (int loop = ROWS - 1; loop >= 0; loop--) {
            if (discGrid[loop][buttonClick] == 'w') {
                discGrid[loop][buttonClick] = currentPlayer;
                result = true;
                break;
            }
        }
        return result;
    }
    //Check for a win
    public boolean checkForWin(char[][] discGrid, Label message, GridPane grid, char currentPlayer, int buttonClick) {
        boolean result = false;
        //check horizontally
        for (int loop = 0; loop < ROWS; loop++) {
            for (int innerLoop = 0; innerLoop < COLUMNS - 3; innerLoop++) {
                if (discGrid[loop][innerLoop] != 'w' && discGrid[loop][innerLoop] == discGrid[loop][innerLoop + 1] && discGrid[loop][innerLoop] == discGrid[loop][innerLoop + 2] && discGrid[loop][innerLoop] == discGrid[loop][innerLoop + 3]) {
                    result = true;
                }
            }
        }
        //check vertically
        for (int loop = 0; loop < COLUMNS; loop++) {
            for (int innerLoop = 0; innerLoop < ROWS - 3; innerLoop++) {
                if (discGrid[innerLoop][loop] != 'w' && discGrid[innerLoop][loop] == discGrid[innerLoop + 1][loop] && discGrid[innerLoop][loop] == discGrid[innerLoop + 2][loop] && discGrid[innerLoop][loop] == discGrid[innerLoop + 3][loop]) {
                    result = true;
                }
            }
        }
        //check diagonally left to right
        for (int loop = 0; loop < ROWS - 3; loop++) {
            for (int innerLoop = 0; innerLoop < COLUMNS - 3; innerLoop++) {
                if (discGrid[loop][innerLoop] != 'w' && discGrid[loop][innerLoop] == discGrid[loop + 1][innerLoop + 1]
                        && discGrid[loop][innerLoop] == discGrid[loop + 2][innerLoop + 2]
                        && discGrid[loop][innerLoop] == discGrid[loop + 3][innerLoop + 3]) {
                    result = true;
                }
            }
        }
        //check diagonally right to left
        for (int loop = 0; loop < ROWS - 3; loop++) {
            for (int innerLoop = 3; innerLoop < COLUMNS; innerLoop++) {
                if (discGrid[loop][innerLoop] != 'w' && discGrid[loop][innerLoop] == discGrid[loop + 1][innerLoop - 1]
                        && discGrid[loop][innerLoop] == discGrid[loop + 2][innerLoop - 2]
                        && discGrid[loop][innerLoop] == discGrid[loop + 3][innerLoop - 3]) {
                    result = true;
                }
            }
        }
        return result;
    }
    //Check if the board is full.
    public boolean isBoardFull(char[][]discGrid) {
        boolean result = true;
        for(int loop = COLUMNS -1; loop >= 0; loop--){
            if (discGrid[0][loop] == 'w') {
                result = false;
            }
        }
        return result;
    }
    //Display a message that the game has tied, and ask if they'd like to play again.
    public char endTieGame(GridPane grid, char currentPlayer){
        char result = currentPlayer;
        Alert tieDialog = new Alert(Alert.AlertType.CONFIRMATION);
        tieDialog.setTitle("Game Over");
        tieDialog.setHeaderText("It's a tie!");
        tieDialog.setContentText("Would you like to play again?");
        Optional<ButtonType> btnResult = tieDialog.showAndWait();
        //Reset game to initial values.
        if (btnResult.get() == ButtonType.OK){
            result = 'r';
            fillDiscGrid(discGrid);
            addDiscFromArray(discGrid, grid);
            setPlayerLabel(currentPlayer, message);
        } else {
            //close game
            Platform.exit();
        }
        return result;
    }

    public void setPlayerLabel(char currentPlayer, Label message){
        if(currentPlayer == 'r'){
            message.setText("Red");
            message.setTextFill(Color.RED);
            message.setVisible(true);
        }else{
            message.setText("Yellow");
            message.setTextFill(Color.GOLDENROD);
            message.setVisible(true);
        }
    }
    //Show a dialog confirming who won and ask if they want to play again.
    public char startNewGameAfterWin(GridPane grid, char currentPlayer){
        char result = currentPlayer;
        String winningPlayer;
        if(currentPlayer == 'r') {
            winningPlayer = "Red";
        }else {
            winningPlayer = "Yellow";
        }

        Alert winDialog = new Alert(Alert.AlertType.CONFIRMATION);
        winDialog.setTitle("Game Over");
        winDialog.setHeaderText(winningPlayer + " just won!");
        winDialog.setContentText("Would you like to play again?");
        Optional<ButtonType> btnResult = winDialog.showAndWait();
        //Reset game to initial values.
        if (btnResult.get() == ButtonType.OK){
            result = 'r';
            fillDiscGrid(discGrid);
            addDiscFromArray(discGrid, grid);
            setPlayerLabel(currentPlayer, message);
        //Otherwise shutdown game.
        } else {
            //close game
            Platform.exit();
        }
        return result;
    }

    public char game(char currentPlayer, char[][] discGrid, int buttonClick, Label message, GridPane grid){
        char result = currentPlayer;
        //Try to add new disc.
        if(addPiece(discGrid, buttonClick, currentPlayer, message) == true){
            addDiscFromArray(discGrid, grid);
            //Check to see if anyone has won since the last play.
            if(checkForWin(discGrid, message, grid, currentPlayer, buttonClick) == false){
                result = changePlayer(currentPlayer);
                if(isBoardFull(discGrid)){
                    //If the board is full, ask if they want to start a new game or close the app.
                    result = endTieGame(grid, currentPlayer);
                }
                //If someone has won. Ask if they want to start a new game.
            }else{
                result = startNewGameAfterWin(grid, currentPlayer);
            }
        }
        return result;
    }

    //Ask if the user if they want to save the game before shutting down.
    public void saveGame(){
        Alert saveRequest = new Alert(Alert.AlertType.CONFIRMATION);
        saveRequest.setTitle("Wait");
        saveRequest.setHeaderText(null);
        saveRequest.setContentText("Would you like to save your game for next time?");
        Optional<ButtonType> buttonResult = saveRequest.showAndWait();
        if (buttonResult.get() == ButtonType.OK) {
            File file = fileChooser.showSaveDialog(null);
            //Try to save the game.
            try {
                FileOutputStream fileStream = new FileOutputStream(file);
                ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

                objectStream.writeObject(discGrid);
                objectStream.writeObject(currentPlayer);

                objectStream.close();
                fileStream.close();
                Alert saved = new Alert(Alert.AlertType.INFORMATION);
                saved.setTitle("Save successful");
                saved.setHeaderText(null);
                saved.setContentText("See you soon!");
                saved.showAndWait();
                Platform.exit();
            //If save doesn't work, let the user know.
            } catch (Exception e) {
                Alert saveFailure = new Alert(Alert.AlertType.INFORMATION);
                saveFailure.setTitle("Save failed");
                saveFailure.setHeaderText(null);
                saveFailure.setContentText("Your game didn't save.");
                saveFailure.showAndWait();
            }
        }

    }

    //Ask user if they would like to load a game from a save file.
    public char[][] loadGame(Stage primaryStage, char[][] discGrid, GridPane grid) throws IOException, ClassNotFoundException {
        char[][] result = new char[ROWS][COLUMNS];
        Alert loadGameQuestion = new Alert(Alert.AlertType.CONFIRMATION);
        loadGameQuestion.setTitle("Welcome");
        loadGameQuestion.setHeaderText(null);
        loadGameQuestion.setContentText("Would you like to load a saved game?");
        Optional<ButtonType> buttonResult = loadGameQuestion.showAndWait();
        if (buttonResult.get() == ButtonType.OK) {
            //Try to load a save file.
            try {
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                FileInputStream fileStream = new FileInputStream(selectedFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);

                char[][] savedDiscGrid = (char[][]) objectInputStream.readObject();
                char savedCurrentPlayer = (char) objectInputStream.readObject();
                objectInputStream.close();
                result = savedDiscGrid.clone();
                currentPlayer = savedCurrentPlayer;
                if (currentPlayer == 'r') {
                    message.setText("Red");
                    message.setTextFill(Color.RED);
                } else {
                    message.setText("Yellow");
                    message.setTextFill(Color.GOLDENROD);
                }
            //If a save can't be found. Ask if they would like to play a new game or exit.
            } catch (Exception e) {
                Alert noSaveFound = new Alert(Alert.AlertType.CONFIRMATION);
                noSaveFound.setTitle("No save found");
                noSaveFound.setHeaderText(null);
                noSaveFound.setContentText("I'm sorry, but a save file can't be found. Play anyway?");
                Optional<ButtonType> btnResult = noSaveFound.showAndWait();
                if (btnResult.get() == ButtonType.OK) {
                    fillDiscGrid(discGrid);
                    result = discGrid;
                }else{
                    Platform.exit();
                }
            }

        }else{
            fillDiscGrid(discGrid);
            result = discGrid;
        }
        //return result;
        return result;
    }
}//close App.java