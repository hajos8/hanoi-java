package main.hanoi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

//peg locations 100, 295, 490 and width 10

//disk sizes 130, 160, 190 (65, 80, 95)

public class HanoiController implements Initializable {
    public static boolean isDraggable = false;

    public static double disk1Width;
    public static double disk2Width;
    public static double disk3Width;

    public static final int[] territories = {100 + (295 - 100) / 2, 295 + (490 - 295) / 2, 600};

    @FXML public Rectangle disk1, disk2, disk3;

    public static HashMap<Integer, Double> widthToNumberMap = new HashMap<>();

    public static int[][] diskLayout = {
            {3, 2, 1}, //peg 1
            {0, 0, 0}, //peg 2
            {0, 0, 0}  //peg 3
    };

    public void selectDisk(MouseEvent mouseEvent) {
        int diskNumber = getDiskNumber(mouseEvent);

        int pegNumber = 0;

        for(int i = 0; i < diskLayout.length; i++){
            for(int j = 0; j < diskLayout[i].length; j++){
                if(diskLayout[i][j] == diskNumber){
                    pegNumber = j;
                    break;
                }
            }
            if(pegNumber != 0){
                break;
            }
        }

        boolean canBeDraggable = true;

        for(int i = 0; i < diskLayout[pegNumber].length; i++){
            if(diskLayout[pegNumber][i] > diskNumber){
                canBeDraggable = false;
            }
        }

        isDraggable = canBeDraggable;
        System.out.println(isDraggable);
    }

    public void dropDisk(MouseEvent mouseEvent) {
        //System.out.println(mouseEvent);

        isDraggable = false;

        double x = mouseEvent.getX();

        int newSpot = 0;

        for (int terrtiory : territories) {
            //System.out.println("x: "  + x + " - " + terrtiory);
            if (x > terrtiory) {
                newSpot++;
            } else {
                break;
            }
        }

        //System.out.println("Dropped " + newSpot + " from territories");


        //put on new peg if it possible
        int diskNumber = getDiskNumber(mouseEvent);


    }

    public void dragDisk(MouseEvent mouseEvent) {
        //System.out.println(mouseEvent);

        if(isDraggable) {
            Rectangle rectangle = (Rectangle) mouseEvent.getSource();

            rectangle.setX(rectangle.getX() + mouseEvent.getX() - rectangle.getX() - rectangle.getWidth() / 2);
            rectangle.setY(rectangle.getY() + mouseEvent.getY() - rectangle.getY() - rectangle.getHeight() / 2);
        }
    }

    public int getDiskNumber(MouseEvent mouseEvent) {
        Rectangle rectangle = (Rectangle) mouseEvent.getSource();

        double width = rectangle.getWidth();

        int diskNumber = 0;

        for(int i = 1; i < widthToNumberMap.size() + 1; i++){
            if(widthToNumberMap.get(i) == width){
                diskNumber = i;
                break;
            }
        }

        return diskNumber;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disk1Width = disk1.getWidth();
        disk2Width = disk2.getWidth();
        disk3Width = disk3.getWidth();

        widthToNumberMap.put(1, disk1Width);
        widthToNumberMap.put(2, disk2Width);
        widthToNumberMap.put(3, disk3Width);
    }
}
