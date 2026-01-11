module main.hanoi {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.hanoi to javafx.fxml;
    exports main.hanoi;
}