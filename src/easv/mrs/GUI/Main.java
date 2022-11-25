package easv.mrs.GUI;

import easv.mrs.GUI.Controller.MovieViewController;
import easv.mrs.GUI.Model.MRSModel;
import easv.mrs.GUI.Model.MovieModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/MovieView.fxml"));
        Parent root = loader.load();

        MovieViewController controller = loader.getController();
        controller.setModel(new MRSModel());
        controller.setup();

        primaryStage.setTitle("MRS2022");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
