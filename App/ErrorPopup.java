package App;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Shows up when incorrect starting settings are applied
public class ErrorPopup {

    public static void main(String errorText) {

        Pane layout = new Pane();
        Scene scene = new Scene(layout, 300, 100);
        Stage stage = new Stage();
        stage.setScene(scene);

        Text error = new Text(errorText);
        error.setX(20);
        error.setY(40);
        layout.getChildren().add(error);
        stage.show();
    }

}
