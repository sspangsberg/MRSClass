package easv.mrs.GUI.Controller;

import easv.mrs.BE.Movie;
import easv.mrs.GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MovieDetailsViewController extends BaseController {

    @FXML
    private TextField txtTitle, txtYear;

    private MovieModel model;

    /**
     *
     * @param actionEvent
     * @throws Exception
     */
    public void handleUpdate(ActionEvent actionEvent) throws Exception {

        String updatedTitle = txtTitle.getText();
        int updatedYear = Integer.parseInt(txtYear.getText());
        Movie updatedMovie = new Movie(model.getSelectedMovie().getId(), updatedYear, updatedTitle);

        model.updateMovie(updatedMovie);

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     *
     */
    @Override
    public void setup() {

        model = getModel().getMovieModel();

        txtTitle.setText(model.getSelectedMovie().getTitle());
        txtYear.setText(String.valueOf(model.getSelectedMovie().getYear()));
    }
}
