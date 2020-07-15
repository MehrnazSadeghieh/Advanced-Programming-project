import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Shape;
////this class is for background of the rectangles that uses this class
public class GradientColor {
    void gradientColor(Shape shape) {
        Stop[] stops = new Stop[]{new Stop(0, Color.SKYBLUE), new Stop(1, Color.DEEPSKYBLUE)};
        LinearGradient linear = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        shape.setFill(linear);
    }
}