package easv.mrs.DAL.db;

import easv.mrs.BE.Movie;
import easv.mrs.DAL.IMovieDataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

    private MyDatabaseConnector databaseConnector;

    public MovieDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    public List<Movie> getAllMovies() throws Exception {

        ArrayList<Movie> allMovies = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection())
        {
            String sql = "SELECT * FROM Movie;";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Movie object
                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                int year = rs.getInt("year");

                Movie movie = new Movie(id, year, title);
                allMovies.add(movie);
            }
            return allMovies;

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not get movies from database", ex);
        }
    }

    public Movie createMovie(String title, int year) throws Exception {
        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public void updateMovie(Movie movie) throws Exception {
        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public void deleteMovie(Movie movie) throws Exception {
        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public List<Movie> searchMovies(String query) throws Exception {

        //TODO Do this
        throw new UnsupportedOperationException();
    }

}
