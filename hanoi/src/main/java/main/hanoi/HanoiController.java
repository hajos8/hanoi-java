package main.hanoi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class HanoiController implements Initializable {
    public static boolean isDraggable = false;

    public static double disk1Width;
    public static double disk2Width;
    public static double disk3Width;

    public static double startingX;
    public static double startingY;

    public static final double[] PegXLocations = {100, 295, 490};
    public static final int[] territories = {100 + (295 - 100) / 2, 295 + (490 - 295) / 2, 600};

    @FXML public Rectangle disk1, disk2, disk3;
    @FXML public Button restart_button;
    @FXML public Label win_label;

    public static HashMap<Integer, Double> widthToNumberMap = new HashMap<>();

    public static final int[][] defaultDiskLayout = {
            {3, 2, 1}, //peg 1
            {0, 0, 0}, //peg 2
            {0, 0, 0}  //peg 3
    };

    public static int[][] diskLayout = {
            {3, 2, 1}, //peg 1
            {0, 0, 0}, //peg 2
            {0, 0, 0}  //peg 3
    };

    public static int[][] winningDiskLayout = {
            {0, 0, 0}, //peg 1
            {0, 0, 0}, //peg 2
            {3, 2, 1}  //peg 3
    };

    public void selectDisk(MouseEvent mouseEvent) {
        int diskNumber = getDiskNumber((Rectangle) mouseEvent.getSource());
        int pegNumber = getDisksCurrentPegNumber(diskNumber);


        int min = diskLayout[pegNumber][0];
        for(int i = 0; i < diskLayout[pegNumber].length; i++){
            if(diskLayout[pegNumber][i] < min && diskLayout[pegNumber][i] > 0){
                min = diskLayout[pegNumber][i];
            }
        }

        if(min == diskNumber){
            isDraggable = true;

            Rectangle rectangle = (Rectangle) mouseEvent.getSource();
            startingX = rectangle.getX();
            startingY = rectangle.getY();
        }
        //System.out.println(isDraggable);
    }

    public void dropDisk(MouseEvent mouseEvent) {
        //System.out.println(mouseEvent);

        if(!isDraggable) return;
        isDraggable = false;

        double x = mouseEvent.getX();

        int newPegNumber = 0;

        for (int terrtiory : territories) {
            if (x > terrtiory) {
                newPegNumber++;
            } else {
                break;
            }
        }

        //System.out.println("Dropped " + newPegNumber + " from territories");

        //put on new peg if it possible
        Rectangle rectangle = (Rectangle) mouseEvent.getSource();

        int diskNumber = getDiskNumber(rectangle);

        if(newPegNumber == getDisksCurrentPegNumber(diskNumber)){
            //put back
            rectangle.setX(startingX);
            rectangle.setY(startingY);

            return;
        }

        boolean canBeDroppable = false;
        int newLocation = 0;

        for(int i = 0; i < diskLayout[newPegNumber].length; i++){
            if(diskLayout[newPegNumber][i] == 0 || diskLayout[newPegNumber][i] > diskNumber){
                canBeDroppable = true;
            }
            else{
                break;
            }
        }
        for(int i = 0; i < diskLayout[newPegNumber].length; i++){
            if(diskLayout[newPegNumber][i] != 0){
                newLocation++;
            }
        }

        //System.out.println("Can be droppable: " + canBeDroppable);
        //System.out.println("New peg number: " + newPegNumber);
        //System.out.println("New location: " + newLocation);

        if(!canBeDroppable){
            //put back
            rectangle.setX(startingX);
            rectangle.setY(startingY);
        }
        else{
            //put on another peg

            for(int i = 0; i < diskLayout.length; i++){
                for(int j = 0; j < diskLayout[i].length; j++){
                    if(diskLayout[i][j] == diskNumber){
                        diskLayout[i][j] = 0;
                        break;
                    }
                }
            }

            diskLayout[newPegNumber][newLocation] = diskNumber;

            for (int[] ints : diskLayout) {
                for (int anInt : ints) {
                    System.out.print(anInt + " ");
                }
                System.out.println();
            }
            System.out.println();

            //move
            int defaultX = 0;
            int defaultY = 0;

            for(int i = 0; i < defaultDiskLayout.length; i++){
                for(int j = 0; j < defaultDiskLayout[i].length; j++){
                    if(defaultDiskLayout[i][j] == diskNumber){
                        defaultX = i;
                        defaultY = j;
                        break;
                    }
                }
            }

            rectangle.setX((newPegNumber - defaultX) * 195.0);
            rectangle.setY((newLocation - defaultY) * -20.0);
        }

        CheckIfTheGameEnded();
    }

    public void dragDisk(MouseEvent mouseEvent) {
        //System.out.println(mouseEvent);

        if(isDraggable) {
            Rectangle rectangle = (Rectangle) mouseEvent.getSource();

            rectangle.setX(rectangle.getX() + mouseEvent.getX() - rectangle.getX() - rectangle.getWidth() / 2);
            rectangle.setY(rectangle.getY() + mouseEvent.getY() - rectangle.getY() - rectangle.getHeight() / 2);
        }
    }

    public int getDiskNumber(Rectangle rectangle) {
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

    public int getDisksCurrentPegNumber(int diskNumber) {
        for(int i = 0; i < diskLayout.length; i++){
            for(int j = 0; j < diskLayout[i].length; j++){
                if(diskLayout[i][j] == diskNumber){
                    return i;
                }
            }
        }
        return -1;
    }

    public void CheckIfTheGameEnded(){
        int matches = 0;
        for(int i = 0; i < diskLayout.length; i++){
            for(int j = 0; j < diskLayout[i].length; j++){
                if(diskLayout[i][j] == winningDiskLayout[i][j]){
                    matches++;
                }
            }
        }

        if(matches == 9){
            win_label.setVisible(true);
            restart_button.setVisible(true);
        }
    }

    @FXML
    public void restartGame(ActionEvent actionEvent) {
        win_label.setVisible(false);
        restart_button.setVisible(false);

        // restore diskLayout from defaultDiskLayout (deep copy)
        for (int i = 0; i < defaultDiskLayout.length; i++) {
            for (int j = 0; j < defaultDiskLayout[i].length; j++) {
                diskLayout[i][j] = defaultDiskLayout[i][j];
            }
        }

        // move each rectangle back to its default position using the same relative logic
        moveRectangleToDefault(disk1, 1);
        moveRectangleToDefault(disk2, 2);
        moveRectangleToDefault(disk3, 3);

        CheckIfTheGameEnded();
    }

    private void moveRectangleToDefault(Rectangle rectangle, int diskNumber) {
        int defaultPeg = 0;
        int defaultStack = 0;

        outer:
        for (int i = 0; i < defaultDiskLayout.length; i++) {
            for (int j = 0; j < defaultDiskLayout[i].length; j++) {
                if (defaultDiskLayout[i][j] == diskNumber) {
                    defaultPeg = i;
                    defaultStack = j;
                    break outer;
                }
            }
        }
        rectangle.setX((defaultPeg - defaultPeg) * 195.0);
        rectangle.setY((defaultStack - defaultStack) * -20.0);
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
