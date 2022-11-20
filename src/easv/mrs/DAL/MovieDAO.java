package easv.mrs.DAL;

// Project imports
import easv.mrs.BE.Movie;

// Java Imports
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
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
        //Files.write(pathToFile, ("\r\n" + newLine).getBytes(), APPEND);

        // Append new line using BufferedWriter
        try (BufferedWriter bw = Files.newBufferedWriter(pathToFile, StandardOpenOption.SYNC, StandardOpenOption.APPEND, StandardOpenOption.WRITE)) {
            bw.newLine();
            bw.write(nextId + "," + year + "," + title);
        }

        return new Movie(nextId, year, title);
    }

    /**
     * Update a movie with param movie
     * @param movie
     * @throws Exception
     */
    @Override
    public void updateMovie(Movie movie) throws Exception {
        try {
            File tmp = new File(movie.hashCode() + ".txt"); //Creates a temp file for writing to.
            List<Movie> allMovies = getAllMovies();
            allMovies.removeIf((Movie t) -> t.getId() == movie.getId());
            allMovies.add(movie);

            //I'll sort the movies by their ID's
            allMovies.sort(Comparator.comparingInt(Movie::getId));

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(tmp))) {
                for (Movie mov : allMovies) {
                    bw.write(mov.getId() + "," + mov.getYear() + "," + mov.getTitle());
                    bw.newLine();
                }
            }

            //Overwrite the movie file wit the tmp one.
            Files.copy(tmp.toPath(), new File(MOVIES_FILE).toPath(), StandardCopyOption.REPLACE_EXISTING);
            //Clean up after the operation is done (Remove tmp)
            Files.delete(tmp.toPath());

        } catch (IOException ex) {
            throw new Exception("Could not update movie.", ex);
        }
    }

    /**
     * Delete a movie from the collection
     * @param movie
     * @throws Exception
     */
    @Override
    public void deleteMovie(Movie movie) throws Exception {

        try {
            File file = new File(MOVIES_FILE);
            if (!file.canWrite()) {
                throw new Exception("Can't write to movie storage");
            }
            List<Movie> movies = getAllMovies();
            movies.remove(movie);
            OutputStream os = Files.newOutputStream(file.toPath());
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
                for (Movie mov : movies) {
                    String line = mov.getId() + "," + mov.getYear() + "," + mov.getTitle();
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException ex) {
            throw new Exception("Could not delete movie.", ex);
        }
    }


    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Movie getMovie(int id) throws Exception {
        List<Movie> all = getAllMovies();

        int index = Collections.binarySearch(all, new Movie(id, 0, ""), Comparator.comparingInt(Movie::getId));

        if (index >= 0) {
            return all.get(index);
        } else {
            throw new IllegalArgumentException("No movie with ID: " + id + " is found.");
        }
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