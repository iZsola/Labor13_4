package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application{


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage){

        ArrayList<Integer> temp=displayLoginPage(primaryStage);
        int n=temp.get(0);
        int m=temp.get(1);
        if (n >20 || m > n || m<2)
            System.exit(1);

        Controller b=new Controller(n, m);
        while (true)
        {
            if (b.isGameOver())
            {
                System.out.println("IDE ERT\n");
                Alert a= new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.CONFIRMATION);
                a.setContentText(String.format("Winner is player %d!", 1));
                a.showAndWait();
                break;
            }
        }
    }

    public static void declareWinner(char winner)
    {
        Platform.setImplicitExit(false);
        /*Platform.runLater(() -> {
            System.out.println("IDE ERT\n");
            Alert a= new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setContentText(String.format("Winner is player %d!", winner=='X' ? 1 : 2));
            a.showAndWait();
        });*/
        Alert a= new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText(String.format("Winner is player %d!", winner=='X' ? 1 : 2));
        a.showAndWait();

    }

    public ArrayList<Integer> displayLoginPage(final Stage owner) {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.setTitle("Set up your game!");
        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 10, 10, 25));

        //Welcome title
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        //Enter size of game field
        Label gameSize = new Label("Enter the size of the game field: ");
        gameSize.setFont(new Font("Arial", 20));
        grid.add(gameSize, 0, 1);
        TextField gameSizeField = new TextField();
        grid.add(gameSizeField, 0, 2);

        Label winningSymbols=new Label("Number of symbols required to win: ");
        winningSymbols.setFont(new Font("Arial", 20));
        grid.add(winningSymbols, 0, 3);
        TextField winningField=new TextField();
        grid.add(winningField, 0, 4);

        Button btn = new Button("Start");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 5);

        final AtomicInteger res=new AtomicInteger();
        final AtomicInteger res2=new AtomicInteger();
        res.set(0);
        res2.set(0);

        //Handle button press
        btn.setOnAction((ActionEvent e) -> {
            try {
                int temp=Integer.parseInt(gameSizeField.getText());
                res.set(temp);
                temp=Integer.parseInt(winningField.getText());
                res2.set(temp);
                if (res.get()<2 || res.get()>20 || res2.get() > res.get() || res2.get()<2) {
                    Alert a = new Alert(Alert.AlertType.NONE);
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("Number must be between 2 and 20!");
                    a.showAndWait();
                }
                stage.close();
            }
            catch (NumberFormatException ex)
            {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Input must be a NUMBER between 2 and 20!");
                a.show();
            }
        });

        //Display
        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.showAndWait();

        ArrayList<Integer> result=new ArrayList<>();
        result.add(res.get());
        result.add(res2.get());
        return result;
    }

}
