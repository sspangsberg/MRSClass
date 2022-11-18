package easv.mrs.DAL;

// Project imports
import easv.mrs.BE.Movie;

// Java Imports
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;


public class MovieDAO implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private Path pathToFile = Path.of(MOVIES_FILE);

    /**
     * Retrieve all movies from the data source
     * @return
     * @throws IOException
     */
    public List<Movie> getAllMovies() throws Exception {

        try {
            // Read all lines from file
            List<String> lines = Files.readAllLines(pathToFile);
            List<Movie> movies = new ArrayList<>();

            // Parse each line
            for (String line : lines) {
                String[] separatedLine = line.split(",");

                // Map each separated line to Movie object
                int id = Integer.parseInt(separatedLine[0]);
                int year = Integer.parseInt(separatedLine[1]);
                String title = separatedLine[2];

                // Create Movie object
                Movie newMovie = new Movie(id, year, title);
                movies.add(newMovie);

                //System.out.println(separatedLine);
            }
            movies.sort(Comparator.comparingInt(Movie::getId));

            return movies;
        }
        catch (IOException e) {
            // Log specifics about IOException and re-throw up the layers...
            throw e;
        }
    }

    /**
     * Create a new movie
     * @param title
     * @param year
     * @return
     * @throws Exception
     */
    @Override
    public Movie createMovie(String title, int year) throws Exception {

        int nextId = getNextID();
        String newLine = nextId + "," + year + "," + title;

        // Append new line using Java NIO
        Files.write(pathToFile, ("\r\n" + newLine).getBytes(), APPEND);

        return new Movie(nextId, year, title);
    }

    /**
     * Update a movie with param movie
     * @param movie
     * @throws Exception
     */
    @Override
    public void updateMovie(Movie movie) throws Exception {

    }

    /**
     * Delete a movie from the collection
     * @param movie
     * @throws Exception
     */
    @Override
    public void deleteMovie(Movie movie) throws Exception {

    }


    /**
     * Get the next ID in our collection
     * @return
     */
    private int getNextID() throws Exception {
        List<Movie> movies = getAllMovies();

        Movie lastMovie = movies.get(movies.size()- 1);
        return lastMovie.getId() + 1;
    }





    /*
    public List<Movie> getAllMovies() {
        List<Movie> allMovieList = new ArrayList<>();

        File moviesFile = new File(MOVIES_FILE);


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(moviesFile))) {
            boolean hasLines = true;
            while (hasLines) {
                String line = bufferedReader.readLine();
                hasLines = (line != null);
                if (hasLines && !line.isBlank())
                {
                    String[] separatedLine = line.split(",");

                    int id = Integer.parseInt(separatedLine[0]);
                    int year = Integer.parseInt(separatedLine[1]);
                    String title = separatedLine[2];
                    if(separatedLine.length > 3)
                    {
                        for(int i = 3; i < separatedLine.length; i++)
                        {
                            title += "," + separatedLine[i];
                        }
                    }
                    Movie movie = new Movie(id, title, year);
                    allMovieList.add(movie);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allMovieList;
    }
    */


}