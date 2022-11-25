package easv.mrs.GUI.Controller;

import easv.mrs.BE.Movie;
import easv.mrs.GUI.Main;
import easv.mrs.GUI.Model.MovieModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController extends BaseController implements Initializable {


    public TextField txtMovieSearch;
    public ListView<Movie> lstMovies;
    public Button btnEdit;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtYear;

    private MovieModel movieModel;

    public MovieViewController()  {

        try {
            //movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            //e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }

    @Override
    public void setup() {

        movieModel = getModel().getMovieModel();

        btnEdit.setDisable(true);

        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                //e.printStackTrace();
            }
        });


        lstMovies.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Movie>() {
            @Override
            public void changed(ObservableValue<? extends Movie> observable, Movie oldValue, Movie newValue) {

                if (newValue != null) {
                    btnEdit.setDisable(false);
                    txtTitle.setText(newValue.getTitle());
                    txtYear.setText(String.valueOf(newValue.getYear()));
                }
                else
                    btnEdit.setDisable(true);
            }
        });
    }



    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }


    public void handleAddNewMovie(ActionEvent actionEvent) {

        String title = txtTitle.getText();
        int year = Integer.parseInt(txtYear.getText());

        try {
            movieModel.createNewMovie(title, year);
        } catch (Exception e) {

        }
    }

    public void handleEdit(ActionEvent actionEvent) throws IOException {

        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();
        movieModel.setSelectedMovie(selectedMovie);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/easv/mrs/GUI/View/MovieDetailsView.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();

        MovieDetailsViewController controller = loader.getController();
        controller.setModel(super.getModel());
        controller.setup();

        // Create the dialog Stage.
        Stage dialogWindow = new Stage();
        dialogWindow.setTitle("Edit Movie");
        dialogWindow.initModality(Modality.WINDOW_MODAL);
        dialogWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        Scene scene = new Scene(pane);
        dialogWindow.setScene(scene);


        // Show the dialog and wait until the user closes it
        dialogWindow.showAndWait();
/*



        */
    }
}
