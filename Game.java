import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class Game extends MainGame{
    Scanner scan = new Scanner(System.in);
    final char BOT = 'o';
    final char USER = 'x';
    char[] arr = {'1','2','3','4','5','6','7','8','9'};
    Board board = new Board(arr);
    int position;
    int count = 0;
    boolean r1Access = true, r2Access = true, r3Access = true, r4Access = true, r5Access =  true, r6Access = true, r7Access =true, r8Access = true, r9Access = true;
    PrintWriter printWriter;

    {
        try {
            printWriter = new PrintWriter("data.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void writeToFile(String message){
        try(FileWriter fw = new FileWriter("data.txt",true);
            BufferedWriter bufferedWriter = new BufferedWriter(fw);
            PrintWriter printWriter = new PrintWriter(bufferedWriter)){
            printWriter.println(message);
            printWriter.close();
        }
        catch (IOException e){

        }
    }

    BufferedWriter bufferedWriter = new BufferedWriter(printWriter);

    boolean validatePosition(Board board, int position){
        if(board.getBoard()[position] == 'x' || board.getBoard()[position] == 'o')
            return false;
        else
            return true;
    }

    boolean isWin(Board board, char input){
        if((board.getBoard()[0] == input && board.getBoard()[1] == input && board.getBoard()[2] == input) ||
                (board.getBoard()[3] == input && board.getBoard()[4] == input && board.getBoard()[5] == input) ||
                (board.getBoard()[6] == input && board.getBoard()[7] == input && board.getBoard()[8] == input) ||
                (board.getBoard()[0] == input && board.getBoard()[3] == input && board.getBoard()[6] == input) ||
                (board.getBoard()[1] == input && board.getBoard()[4] == input && board.getBoard()[7] == input) ||
                (board.getBoard()[2] == input && board.getBoard()[5] == input && board.getBoard()[8] == input) ||
                (board.getBoard()[0] == input && board.getBoard()[4] == input && board.getBoard()[8] == input) ||
                (board.getBoard()[2] == input && board.getBoard()[4] == input && board.getBoard()[6] == input)){
            return true;
        }else {
            return false;
        }
    }

    int isWin(Board board,int depth){
        if((board.getBoard()[0] == BOT && board.getBoard()[1] == BOT && board.getBoard()[2] == BOT) ||
                (board.getBoard()[3] == BOT && board.getBoard()[4] == BOT && board.getBoard()[5] == BOT) ||
                (board.getBoard()[6] == BOT && board.getBoard()[7] == BOT && board.getBoard()[8] == BOT) ||
                (board.getBoard()[0] == BOT && board.getBoard()[3] == BOT && board.getBoard()[6] == BOT) ||
                (board.getBoard()[1] == BOT && board.getBoard()[4] == BOT && board.getBoard()[7] == BOT) ||
                (board.getBoard()[2] == BOT && board.getBoard()[5] == BOT && board.getBoard()[8] == BOT) ||
                (board.getBoard()[0] == BOT && board.getBoard()[4] == BOT && board.getBoard()[8] == BOT) ||
                (board.getBoard()[2] == BOT && board.getBoard()[4] == BOT && board.getBoard()[6] == BOT)){
            return -(10);
        }else if((board.getBoard()[0] == USER && board.getBoard()[1] == USER && board.getBoard()[2] == USER) ||
                (board.getBoard()[3] == USER && board.getBoard()[4] == USER && board.getBoard()[5] == USER) ||
                (board.getBoard()[6] == USER && board.getBoard()[7] == USER && board.getBoard()[8] == USER) ||
                (board.getBoard()[0] == USER && board.getBoard()[3] == USER && board.getBoard()[6] == USER) ||
                (board.getBoard()[1] == USER && board.getBoard()[4] == USER && board.getBoard()[7] == USER) ||
                (board.getBoard()[2] == USER && board.getBoard()[5] == USER && board.getBoard()[8] == USER) ||
                (board.getBoard()[0] == USER && board.getBoard()[4] == USER && board.getBoard()[8] == USER) ||
                (board.getBoard()[2] == USER && board.getBoard()[4] == USER && board.getBoard()[6] == USER)){
            return 10;
        }{
            return 0;
        }
    }


    int validateEvaluationFunction(Board board,char input,int depth){
        if(isWin(board, input) == true){
            if(input == 'x')
                return 10-depth;
            else
                return -(10+depth);
        }else{
            return 0;
        }
    }


    int minimax(Board board, int depth, char input){
        int score = isWin(board, depth);

        if(isWin(board,depth) == 10) return score-depth;
        else if(isWin(board,depth) == -10) return score+depth;

        if(board.checkMovesLeft() == false) return 0;

        if(input == BOT){
            int bestValue = 99999999;
            for(int i = 0 ; i < board.getBoard().length; i++){
                if(board.getBoard()[i] != 'x' && board.getBoard()[i] != 'o'){
                    char before = board.getBoard()[i];
                    board.getBoard()[i] = BOT;

                    int value = minimax(board,depth++,USER);
                    bestValue = Math.min(bestValue, value);

                    board.getBoard()[i] = before;
                }
            }
            return bestValue;
        }else{
            int bestValue = -99999999;
            for(int i = 0 ; i < board.getBoard().length; i++){
                if(board.getBoard()[i] != 'x' && board.getBoard()[i] != 'o'){
                    char before = board.getBoard()[i];
                    board.getBoard()[i] = USER;

                    int value = minimax(board,depth++,BOT);
                    bestValue = Math.max(bestValue, value);

                    board.getBoard()[i] = before;
                }
            }
            return bestValue;
        }

    }

    int findBestMoves(Board board){
        int bestMoveValues =  999999999;
        int bestMove = -1;

        for(int i = 0 ; i < board.getBoard().length; i++){
            if(board.getBoard()[i] != 'x' && board.getBoard()[i] != 'o'){
                char before = board.getBoard()[i];
                board.getBoard()[i] = BOT;

                int bestValue = minimax(board,0,USER);

                board.getBoard()[i] = before;

                if(bestValue < bestMoveValues){
                    bestMoveValues = bestValue;
                    bestMove = i;
                }
            }
        }

        return bestMove + 1;
    }


    @Override
    public void start(Stage primaryStage){

        Rectangle game = new Rectangle(10,10,580,380);
        GradientColor gradientColor = new GradientColor();
        gradientColor.gradientColor(game);
        Pane pane = new Pane();
        Rectangle r1,r2,r3,r4,r5,r6,r7,r8,r9;
        r1 = new Rectangle(175,80,70,80);
        r1.setFill(Color.WHITESMOKE);
        r2 = new Rectangle(275,80,70,80);
        r2.setFill(Color.WHITESMOKE);
        r3 = new Rectangle(375,80,70,80);
        r3.setFill(Color.WHITESMOKE);
        r4 = new Rectangle(175,180,70,80);
        r4.setFill(Color.WHITESMOKE);
        r5 = new Rectangle(275,180,70,80);
        r5.setFill(Color.WHITESMOKE);
        r6 = new Rectangle(375,180,70,80);
        r6.setFill(Color.WHITESMOKE);
        r7 = new Rectangle(175,280,70,80);
        r7.setFill(Color.WHITESMOKE);
        r8 = new Rectangle(275,280,70,80);
        r8.setFill(Color.WHITESMOKE);
        r9 = new Rectangle(375,280,70,80);
        r9.setFill(Color.WHITESMOKE);

        r1.setVisible(false);
        r2.setVisible(false);
        r3.setVisible(false);
        r4.setVisible(false);
        r5.setVisible(false);
        r6.setVisible(false);
        r7.setVisible(false);
        r8.setVisible(false);
        r9.setVisible(false);

        Text computerWon = new Text("Computer Won :)");
        Text playerWon = new Text("You won :)");
        Text equal = new Text("The game is equal :|");
        computerWon.setFill(Color.GRAY);
        playerWon.setFill(Color.GRAY);
        equal.setFill(Color.GRAY);
        computerWon.setX(150);
        computerWon.setY(150);
        playerWon.setY(150);
        playerWon.setX(150);
        equal.setX(130);
        equal.setY(160);
        playerWon.setFont(javafx.scene.text.Font.font("Liberation Serif", FontWeight.BOLD, FontPosture.REGULAR, 40));
        computerWon.setFont(javafx.scene.text.Font.font("Liberation Serif", FontWeight.BOLD, FontPosture.REGULAR, 40));
        equal.setFont(Font.font("Liberation Serif", FontWeight.BOLD, FontPosture.REGULAR, 30));

        playerWon.setVisible(false);
        computerWon.setVisible(false);
        equal.setVisible(false);

        Text text = new Text("Which plays first?");
        text.setX(150);
        text.setY(150);
        text.setFont(Font.font("Liberation Serif", FontWeight.BOLD, FontPosture.REGULAR, 40));
        text.setFill(Color.DARKGRAY);

        Button playerButton,computerButton;

        playerButton = new Button();
        playerButton.setLayoutX(220);
        playerButton.setLayoutY(185);
        playerButton.setText("Player");

        computerButton = new Button();
        computerButton.setLayoutX(300);
        computerButton.setLayoutY(185);
        computerButton.setText("computer");

        playerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                count = 0;
                r1.setVisible(true);
                r2.setVisible(true);
                r3.setVisible(true);
                r4.setVisible(true);
                r5.setVisible(true);
                r6.setVisible(true);
                r7.setVisible(true);
                r8.setVisible(true);
                r9.setVisible(true);
                text.setVisible(false);
                computerButton.setVisible(false);
                playerButton.setVisible(false);
            }
        });

        computerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                count = 1;
                r1.setVisible(true);
                r2.setVisible(true);
                r3.setVisible(true);
                r4.setVisible(true);
                r5.setVisible(true);
                r6.setVisible(true);
                r7.setVisible(true);
                r8.setVisible(true);
                r9.setVisible(true);
                text.setVisible(false);
                computerButton.setVisible(false);
                playerButton.setVisible(false);
                position = findBestMoves(board);
                if (position == 1) {
                    r1.setFill(Color.SPRINGGREEN);
                    count++;
                    r1Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.") ;
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                if (position == 2) {
                    r2.setFill(Color.SPRINGGREEN);
                    count++;
                    r2Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 3) {
                    r3.setFill(Color.SPRINGGREEN);
                    count++;
                    r3Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 4) {
                    r4.setFill(Color.SPRINGGREEN);
                    count++;
                    r4Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 5) {
                    r5.setFill(Color.SPRINGGREEN);
                    count++;
                    r5Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 6) {
                    r6.setFill(Color.SPRINGGREEN);
                    count++;
                    r6Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 7) {
                    r7.setFill(Color.SPRINGGREEN);
                    count++;
                    r7Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 8) {
                    r8.setFill(Color.SPRINGGREEN);
                    count++;
                    r8Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 9) {
                    r9.setFill(Color.SPRINGGREEN);
                    count++;
                    r9Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                        /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                        printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
            }
        });


        r1.setOnMouseClicked(mouseEvent -> {
            if (r1Access) {
                if(count % 2 ==0){
                    r1Access = false;
                    r1.setFill(Color.RED);
                    count++;
                    board.getBoard()[0] = 'x';
                    if (isWin(board, 'x')) {
                        won(playerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was player.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was player");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }

                }
                position = findBestMoves(board);
                if (position == 2) {
                    r2.setFill(Color.SPRINGGREEN);
                    count++;
                    r2Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 3) {
                    r3.setFill(Color.SPRINGGREEN);
                    count++;
                    r3Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 4) {
                    r4.setFill(Color.SPRINGGREEN);
                    count++;
                    r4Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 5) {
                    r5.setFill(Color.SPRINGGREEN);
                    count++;
                    r5Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 6) {
                    r6.setFill(Color.SPRINGGREEN);
                    count++;
                    r6Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 7) {
                    r7.setFill(Color.SPRINGGREEN);
                    count++;
                    r7Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.") ;
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 8) {
                    r8.setFill(Color.SPRINGGREEN);
                    count++;
                    r8Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 9) {
                    r9.setFill(Color.SPRINGGREEN);
                    count++;
                    r9Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
            }
        });
        r2.setOnMouseClicked(mouseEvent -> {
            if(r2Access) {
                if(count % 2 ==0){
                    r2Access = false;
                    r2.setFill(Color.RED);
                    count++;
                    board.getBoard()[1] = 'x';
                    if (isWin(board, 'o')) {
                        won(playerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was player.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was player");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                position = findBestMoves(board);
                if (position == 1) {
                    r1.setFill(Color.SPRINGGREEN);
                    count++;
                    r1Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 3) {
                    r3.setFill(Color.SPRINGGREEN);
                    count++;
                    r3Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 4) {
                    r4.setFill(Color.SPRINGGREEN);
                    count++;
                    r4Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 5) {
                    r5.setFill(Color.SPRINGGREEN);
                    count++;
                    r5Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 6) {
                    r6.setFill(Color.SPRINGGREEN);
                    count++;
                    r6Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 7) {
                    r7.setFill(Color.SPRINGGREEN);
                    count++;
                    r7Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 8) {
                    r8.setFill(Color.SPRINGGREEN);
                    count++;
                    r8Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 9) {
                    r9.setFill(Color.SPRINGGREEN);
                    count++;
                    r9Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
            }

        });
        r3.setOnMouseClicked(mouseEvent -> {
            if(r3Access) {
                if(count % 2 ==0){
                    r3Access = false;
                    r3.setFill(Color.RED);
                    count++;
                    board.getBoard()[2] = 'x';
                    if (isWin(board, 'x')) {
                        won(playerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was player.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was player");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                position = findBestMoves(board);
                if (position == 1) {
                    r1.setFill(Color.SPRINGGREEN);
                    count++;
                    r1Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 2) {
                    r2.setFill(Color.SPRINGGREEN);
                    count++;
                    r2Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 4) {
                    r4.setFill(Color.SPRINGGREEN);
                    count++;
                    r4Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 5) {
                    r5.setFill(Color.SPRINGGREEN);
                    count++;
                    r5Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 6) {
                    r6.setFill(Color.SPRINGGREEN);
                    count++;
                    r6Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 7) {
                    r7.setFill(Color.SPRINGGREEN);
                    count++;
                    r7Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 8) {
                    r8.setFill(Color.SPRINGGREEN);
                    count++;
                    r8Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 9) {
                    r9.setFill(Color.SPRINGGREEN);
                    count++;
                    r9Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
            }

        });
        r4.setOnMouseClicked(mouseEvent -> {
            if(r4Access) {
                if(count % 2 ==0){
                    r4Access = false;
                    r4.setFill(Color.RED);
                    count++;
                    board.getBoard()[3] = 'x';
                    if (isWin(board, 'x')) {
                        won(playerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was player.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was player");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                position = findBestMoves(board);
                if (position == 1) {
                    r1.setFill(Color.SPRINGGREEN);
                    count++;
                    r1Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 2) {
                    r2.setFill(Color.SPRINGGREEN);
                    count++;
                    r2Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 3) {
                    r3.setFill(Color.SPRINGGREEN);
                    count++;
                    r3Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 5) {
                    r5.setFill(Color.SPRINGGREEN);
                    count++;
                    r5Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 6) {
                    r6.setFill(Color.SPRINGGREEN);
                    count++;
                    r6Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 7) {
                    r7.setFill(Color.SPRINGGREEN);
                    count++;
                    r7Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 8) {
                    r8.setFill(Color.SPRINGGREEN);
                    count++;
                    r8Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 9) {
                    r9.setFill(Color.SPRINGGREEN);
                    count++;
                    r9Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }

            }
        });
        r5.setOnMouseClicked(mouseEvent -> {
            if(r5Access) {
                if(count % 2 ==0){
                    r5Access = false;
                    r5.setFill(Color.RED);
                    count++;
                    board.getBoard()[4] = 'x';
                    if (isWin(board, 'x')) {
                        won(playerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was player.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was player");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                position = findBestMoves(board);
                if (position == 1) {
                    r1.setFill(Color.SPRINGGREEN);
                    count++;
                    r1Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 2) {
                    r2.setFill(Color.SPRINGGREEN);
                    count++;
                    r2Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 3) {
                    r3.setFill(Color.SPRINGGREEN);
                    count++;
                    r3Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 4) {
                    r4.setFill(Color.SPRINGGREEN);
                    count++;
                    r4Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 6) {
                    r6.setFill(Color.SPRINGGREEN);
                    count++;
                    r6Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 7) {
                    r7.setFill(Color.SPRINGGREEN);
                    count++;
                    r7Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 8) {
                    r8.setFill(Color.SPRINGGREEN);
                    count++;
                    r8Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.") ;
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 9) {
                    r9.setFill(Color.SPRINGGREEN);
                    count++;
                    r9Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }

            }
        });

        r6.setOnMouseClicked(mouseEvent -> {
            if(r6Access) {
                if(count % 2 ==0){
                    r6Access = false;
                    r6.setFill(Color.RED);
                    count++;
                    board.getBoard()[5] = 'x';
                    if (isWin(board, 'x')) {
                        won(playerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was player.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was player");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                position = findBestMoves(board);
                if (position == 1) {
                    r1.setFill(Color.SPRINGGREEN);
                    count++;
                    r1Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 2) {
                    r2.setFill(Color.SPRINGGREEN);
                    count++;
                    r2Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 3) {
                    r3.setFill(Color.SPRINGGREEN);
                    count++;
                    r3Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 4) {
                    r4.setFill(Color.SPRINGGREEN);
                    count++;
                    r4Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 5) {
                    r5.setFill(Color.SPRINGGREEN);
                    count++;
                    r5Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 7) {
                    r7.setFill(Color.SPRINGGREEN);
                    count++;
                    r7Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 8) {
                    r8.setFill(Color.SPRINGGREEN);
                    count++;
                    r8Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 9) {
                    r9.setFill(Color.SPRINGGREEN);
                    count++;
                    r9Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
            }

        });
        r7.setOnMouseClicked(mouseEvent -> {
            if(r7Access) {
                if(count % 2 ==0){
                    r7Access = false;
                    r7.setFill(Color.RED);
                    count++;
                    board.getBoard()[6] = 'x';
                    if (isWin(board, 'x')) {
                        won(playerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was player.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was player");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                position = findBestMoves(board);
                if (position == 1) {
                    r1.setFill(Color.SPRINGGREEN);
                    count++;
                    r1Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 2) {
                    r2.setFill(Color.SPRINGGREEN);
                    count++;
                    r2Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 3) {
                    r3.setFill(Color.SPRINGGREEN);
                    count++;
                    r3Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 4) {
                    r4.setFill(Color.SPRINGGREEN);
                    count++;
                    r4Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 5) {
                    r5.setFill(Color.SPRINGGREEN);
                    count++;
                    r5Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 6) {
                    r6.setFill(Color.SPRINGGREEN);
                    count++;
                    r6Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 8) {
                    r8.setFill(Color.SPRINGGREEN);
                    count++;
                    r8Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 9) {
                    r9.setFill(Color.SPRINGGREEN);
                    count++;
                    r9Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }

            }
        });

        r8.setOnMouseClicked(mouseEvent -> {
            if(r8Access) {
                if(count % 2 ==0){
                    r8Access = false;
                    r8.setFill(Color.RED);
                    count++;
                    board.getBoard()[7] = 'x';
                    if (isWin(board, 'x')) {
                        won(playerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was player.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was player");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                position = findBestMoves(board);
                if (position == 1) {
                    r1.setFill(Color.SPRINGGREEN);
                    count++;
                    r1Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 2) {
                    r2.setFill(Color.SPRINGGREEN);
                    count++;
                    r2Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 3) {
                    r3.setFill(Color.SPRINGGREEN);
                    count++;
                    r3Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 4) {
                    r4.setFill(Color.SPRINGGREEN);
                    count++;
                    r4Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 5) {
                    r5.setFill(Color.SPRINGGREEN);
                    count++;
                    r5Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 6) {
                    r6.setFill(Color.SPRINGGREEN);
                    count++;
                    r6Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 7) {
                    r7.setFill(Color.SPRINGGREEN);
                    count++;
                    r7Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 9) {
                    r9.setFill(Color.SPRINGGREEN);
                    count++;
                    r9Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                            /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and  and the game was equal.");
                            printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
            }

        });
        r9.setOnMouseClicked(mouseEvent -> {
            if(r9Access) {
                if(count % 2 ==0){
                    r9Access = false;
                    r9.setFill(Color.RED);
                    count++;
                    board.getBoard()[8] = 'x';
                    if (isWin(board, 'x')) {
                        won(playerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was player.");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was player");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the game was equal.");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                position = findBestMoves(board);
                if (position == 1) {
                    r1.setFill(Color.SPRINGGREEN);
                    count++;
                    r1Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the game was equal.");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 2) {
                    r2.setFill(Color.SPRINGGREEN);
                    count++;
                    r2Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the game was equal.");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 3) {
                    r3.setFill(Color.SPRINGGREEN);
                    count++;
                    r3Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the game was equal.");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 4) {
                    r4.setFill(Color.SPRINGGREEN);
                    count++;
                    r4Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the game was equal.");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 5) {
                    r5.setFill(Color.SPRINGGREEN);
                    count++;
                    r5Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the game was equal.");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 6) {
                    r6.setFill(Color.SPRINGGREEN);
                    count++;
                    r6Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the game was equal.");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 7) {
                    r7.setFill(Color.SPRINGGREEN);
                    count++;
                    r7Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the game was equal.");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
                else if (position == 8) {
                    r8.setFill(Color.SPRINGGREEN);
                    count++;
                    r8Access = false;
                    board.getBoard()[position-1] = 'o';
                    if (isWin(board, 'o')) {
                        won(computerWon, r1, r2, r3, r4, r5, r6, r7, r8, r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and the winner was computer");
                    }
                    if (!board.checkMovesLeft()){
                        won(equal,r1,r2,r3,r4,r5,r6,r7,r8,r9);
                                    /*printWriter.write("The game was played at : " + java.time.LocalTime.now() + " and the game was equal.");
                                    printWriter.close();*/
                        writeToFile("The game was played at : " + java.time.LocalTime.now() + " and it was equal.");
                    }
                }
            }
        });





        pane.getChildren().addAll(game,r1,r2,r3,r4,r5,r6,r7,r8,r9,computerWon,playerWon,equal,text,playerButton,computerButton);
        primaryStage.setScene(new Scene(pane,600,400));
        primaryStage.show();


    }

    public void won(Shape player, Shape s1,Shape s2,Shape s3,Shape s4,Shape s5,Shape s6,Shape s7,Shape s8,Shape s9){
        s1.setVisible(false);
        s2.setVisible(false);
        s3.setVisible(false);
        s4.setVisible(false);
        s5.setVisible(false);
        s6.setVisible(false);
        s7.setVisible(false);
        s8.setVisible(false);
        s9.setVisible(false);
        player.setVisible(true);
    }
}

