package main.hanoi;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

//peg locations 100, 295, 490 and width 10

//disk sizes 130, 160, 190 (65, 80, 95)

public class HanoiController {
    @FXML public Rectangle disk1, disk2, disk3;

    public static int[][] diskLayout = {
            {1, 0, 0},
            {2, 0, 0},
            {3, 0, 0}
    };

    public void dropDisk(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
    }

    public void dragDisk(MouseEvent mouseEvent) {
        //System.out.println(mouseEvent);

        Rectangle rectangle = (Rectangle) mouseEvent.getSource();

        rectangle.setX(rectangle.getX() + mouseEvent.getX() - rectangle.getX() - rectangle.getWidth() / 2);
        rectangle.setY(rectangle.getY() + mouseEvent.getY() - rectangle.getY()  - rectangle.getHeight() / 2);

    }
}
