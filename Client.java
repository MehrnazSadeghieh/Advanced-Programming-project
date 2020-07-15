import com.sun.javafx.scene.control.behavior.TwoLevelFocusBehavior;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Client extends Application {

    public void sendToServer(){
        int count = 0;
        while (true) {
            try {
                System.out.println(string);
                Socket connection = new Socket("127.0.0.1", 7773);
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
                dataOutputStream.writeUTF("This is client:" + string);
                dataOutputStream.flush();
                if(count==0){
                    break;
                }
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
                System.out.println(dataInputStream.readUTF());
                count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static String string;
    public static void main(String[] args) {
        launch(args);
        /*int count = 0;
        while (true) {
            try {
                System.out.println(string);
                Socket connection = new Socket("127.0.0.1", 7771);
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
                dataOutputStream.writeUTF("This is client:" + string);
                dataOutputStream.flush();
                if(count==1){
                    break;
                }
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
                System.out.println(dataInputStream.readUTF());
                count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane signUpIn = new Pane();
        GradientColor gradientColor = new GradientColor();
        Rectangle rectangle = new Rectangle(10, 10, 280, 380);
        ////gradientColor.gradientColor(rectangle);
        FileInputStream input = new FileInputStream("/Users/macos/Desktop/image.png");
        Image image = new Image(input);
        rectangle.setFill(new ImagePattern(image));
        Button signIn,signUp;
        signIn = new Button();
        signUp = new Button();
        signIn.setText("Sign in");
        signUp.setText("Sign Up");
        Text signInUpText = new Text("Press sign in if you already have an account else press sign up ");
        signInUpText.setFont(javafx.scene.text.Font.font("Liberation Serif", FontWeight.BOLD, FontPosture.REGULAR, 11));
        signInUpText.setX(12);
        signInUpText.setY(250);
        signIn.setLayoutX(80);
        signIn.setLayoutY(280);
        signIn.setScaleX(1);
        signIn.setScaleY(1);
        signUp.setLayoutX(160);
        signUp.setLayoutY(280);
        signUp.setScaleX(1);
        signUp.setScaleY(1);
        TextField username,password,emailAdress;
        username = new TextField();
        password = new TextField();
        emailAdress = new TextField();
        username.setLayoutX(90);
        username.setLayoutY(230);
        username.setScaleX(1);
        username.setScaleY(1);
        Text user = new Text("Username:");
        user.setX(25);
        user.setY(250);
        password.setLayoutX(90);
        password.setLayoutY(310);
        password.setScaleX(1);
        password.setScaleY(1);
        Text pass = new Text("Password:");
        pass.setX(25);
        pass.setY(330);
        emailAdress.setLayoutX(90);
        emailAdress.setLayoutY(270);
        emailAdress.setScaleX(1);
        emailAdress.setScaleY(1);
        Text email = new Text("Email:");
        email.setX(25);
        email.setY(290);
        Button confirm = new Button("Confirm");
        ////button for sign in
        confirm.setLayoutX(200);
        confirm.setLayoutY(350);
        Button confirm2 = new Button("Confirm");
        ////button for sign up
        confirm2.setLayoutX(200);
        confirm2.setLayoutY(350);
        Button forgotPassword = new Button("Forgot Password");
        forgotPassword.setLayoutX(70);
        forgotPassword.setLayoutY(350);
        forgotPassword.setScaleX(1);
        forgotPassword.setScaleY(1);
        TextField question = new TextField();
        question.setLayoutX(90);
        question.setLayoutY(190);
        question.setScaleX(1);
        question.setScaleY(1);
        Text questionText = new Text("Question");
        questionText.setX(25);
        questionText.setY(210);
        Text questions = new Text("Question : What is the name of your best friend?");
        questions.setX(20);
        questions.setY(170);
        Text error1,error2,error3;
        error1 = new Text("!");
        error2 = new Text("!");
        error3 = new Text("!");
        error1.setFill(Color.RED);
        error2.setFill(Color.RED);
        error3.setFill(Color.RED);
        error1.setX(265);
        error1.setY(250);
        error2.setX(265);
        error2.setY(330);
        error3.setX(265);
        error3.setY(290);
        username.setVisible(false);
        password.setVisible(false);
        user.setVisible(false);
        pass.setVisible(false);
        emailAdress.setVisible(false);
        email.setVisible(false);
        question.setVisible(false);
        questionText.setVisible(false);
        confirm.setVisible(false);
        confirm2.setVisible(false);
        forgotPassword.setVisible(false);
        question.setVisible(false);
        questions.setVisible(false);
        questionText.setVisible(false);
        error1.setVisible(false);
        error2.setVisible(false);
        error3.setVisible(false);

        signIn.setOnAction(new EventHandler< ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                signIn.setVisible(false);
                signUp.setVisible(false);
                signInUpText.setVisible(false);
                username.setVisible(true);
                confirm2.setVisible(false);
                user.setVisible(true);
                password.setVisible(true);
                pass.setVisible(true);
                email.setVisible(true);
                emailAdress.setVisible(true);
                confirm.setVisible(true);
                forgotPassword.setVisible(true);
            }
        });

        forgotPassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                password.setVisible(false);
                pass.setVisible(false);
                question.setVisible(true);
                questions.setVisible(true);
                questionText.setVisible(true);
                forgotPassword.setVisible(false);

            }
        });

        signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                signIn.setVisible(false);
                signUp.setVisible(false);
                signInUpText.setVisible(false);
                username.setVisible(true);
                user.setVisible(true);
                password.setVisible(true);
                pass.setVisible(true);
                email.setVisible(true);
                emailAdress.setVisible(true);
                confirm.setVisible(false);
                confirm2.setVisible(true);
                question.setVisible(true);
                questions.setVisible(true);
                questionText.setVisible(true);
                forgotPassword.setVisible(false);
            }
        });

        confirm2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean validProfileData = false;
                boolean invalidUsername = false;
                boolean invalidPassword = false;
                boolean invalidEmail = false;
                ///check that if the information that we enter is correct
                for (int i = 0; i < Server.testHandles.size(); i++) {
                    String data = Server.testHandles.get(i).string;
                    String[] profile;
                    profile = data.split("/,]");
                    invalidUsername = username.getText().equals(profile[0]);
                    if (invalidUsername) {
                        error1.setVisible(true);
                        System.out.println("This username has been already used!!! \n please enter another one :) ");
                        i = -1;
                        username.deleteText(0, username.getText().length() );
                        continue;
                    }
                }
                if(password.getText().length() < 8){
                    error2.setVisible(true);
                    password.deleteText(0,password.getText().length() );
                    System.out.println("Invalid password your password should be at least 8 characters!!");
                    invalidPassword = true;
                }
                String email1 = emailAdress.getText();
                boolean duplicateEmail = false;
                for (int i = 0; i < Server.testHandles.size(); i++){
                    if(Server.testHandles.get(i).string.contains(email1)) duplicateEmail = true;
                }
                if(!email1.contains("@") || duplicateEmail){
                    error3.setVisible(true);
                    invalidEmail = true;
                    System.out.println("Invalid email address!");
                    emailAdress.deleteText(0,emailAdress.getText().length() );
                }
                if(!username.getText().isEmpty() && !password.getText().isEmpty() && !emailAdress.getText().isEmpty() && !question.getText().isEmpty()) {
                    if (!invalidEmail && !invalidPassword && !invalidUsername) {
                        MainGame mainGame = new MainGame();
                        mainGame.start(primaryStage);
                        String userPass;
                        userPass = username.getText() + "/" + password.getText() + "," + emailAdress.getText() + "]" + question.getText();
                        string = userPass;
                        sendToServer();
                    }
                }
            }
        });
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(question.isVisible()){
                    for (int i = 0; i < Server.testHandles.size(); i++){
                        String profileData = Server.testHandles.get(i).string;
                        String [] data = profileData.split("/,]");
                        if(username.getText().equals(data[0]) && emailAdress.getText().equals(data[2]) && password.getText().equals(data[1])){
                            MainGame mainGame = new MainGame();
                            mainGame.start(primaryStage);
                            string = "" + i;
                            sendToServer();
                        }
                        else System.out.println("Invalid entry");
                    }
                }
                else if(!question.isVisible()){
                    for (int i = 0; i < Server.testHandles.size(); i++){
                        String profileData = Server.testHandles.get(i).string;
                        String [] data = profileData.split("/,]");
                        if(username.getText().equals(data[0]) && emailAdress.getText().equals(data[2]) && question.getText().equals(data[3])){
                            MainGame mainGame = new MainGame();
                            mainGame.start(primaryStage);
                            string = "" + i;
                            sendToServer();
                        }
                        else System.out.println("Invalid entry");
                    }
                }
            }
        });
        signUpIn.getChildren().addAll(rectangle,signIn,signUp,signInUpText,username,password,user,pass,emailAdress,email,confirm,confirm2,forgotPassword,question,questionText,questions,error1,error2,error3);
        primaryStage.setScene(new Scene(signUpIn, 300, 400, Color.NAVY));
        primaryStage.show();
    }

}
