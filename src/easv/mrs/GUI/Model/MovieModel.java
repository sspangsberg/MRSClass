package easv.mrs.GUI.Model;

// Java imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

// Project imports
import easv.mrs.BE.Movie;
import easv.mrs.BLL.MovieManager;

/**
 * @author smsj
 */
public class MovieModel {

    private ObservableList<Movie> moviesToBeViewed;
    private MovieManager movieManager;
    private Movie selectedMovie;

    /**
     * Constructor
     * @throws Exception
     */
    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }

    public ObservableList<Movie> getObservableMovies() {
        return moviesToBeViewed;
    }

    public void searchMovie(String query) throws Exception {
        List<Movie> searchResults = movieManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }


    public void createNewMovie(String title, int year) throws Exception{
        // Create movie in data storage
        Movie m = movieManager.createNewMovie(title, year);

        // Add movie to observable list (gui)
        moviesToBeViewed.add(m);
    }

    public void updateMovie(Movie updatedMovie) throws Exception {
        // Call BLL
        // update movie in DB
        movieManager.updateMovie(updatedMovie);

        // update ListView
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }

    public Movie getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }
}
