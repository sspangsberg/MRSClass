package easv.mrs.GUI.Model;

import easv.mrs.BE.Movie;

public class MRSModel {

    private MovieModel movieModel;

    public MRSModel() throws Exception {
        movieModel = new MovieModel();
    }

    public MovieModel getMovieModel() {
        return movieModel;
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }
}
