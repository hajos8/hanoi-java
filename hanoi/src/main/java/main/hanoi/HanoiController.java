package main.hanoi;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

//peg locations 100, 295, 490 and width 10

//disk sizes 130, 160, 190

public class HanoiController {

    public Rectangle disk1, disk2, disk3;

    public void selected(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
    }

    public void dropped(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
    }

}
