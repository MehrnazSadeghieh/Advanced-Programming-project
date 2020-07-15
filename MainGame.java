import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class MainGame extends Client {

    public static void main(String [] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        GradientColor gradientColor = new GradientColor();
        Rectangle rectangle = new Rectangle(10, 10, 580, 380);
        gradientColor.gradientColor(rectangle);
        rectangle.setArcWidth(23);
        rectangle.setArcHeight(23);
        primaryStage.setTitle("Tic Tac Toe");
        Text text = new Text();
        text.setText("Welcome to Unbeatable TicTacToe!!!");
        text.setFont(Font.font("Liberation Serif", FontWeight.BOLD, FontPosture.REGULAR, 30));
        text.setY(160);
        text.setX(60);
        text.setFill(Color.GRAY);
        Button start = new Button("start");
        start.setLayoutY(220);
        start.setLayoutX(280);
        start.setScaleX(2.5);
        start.setScaleY(1.5);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Game game = new Game();
                game.start(primaryStage);

            }
        });
        start.setFont(Font.font("Times Roman"));
        pane.getChildren().addAll(rectangle, text, start);
        primaryStage.setScene(new Scene(pane, 600, 400, Color.NAVY));
        primaryStage.show();
    }
}

