import java.util.*;

/*
 * Constructor for the game object for the GameCheck project.
 * Written by Andrew Jackson for CSE201, jacks338.
 */

public class Game {

    String name;
    float price;
    String publisher;
    String genres;
    Set<Integer> platforms;
    int rating;

    /**
     * Constructor for the game object.
     * @param n Name string.
     * @param pr Price float.
     * @param pb Publisher string.
     * @param String of genres.
     * @param pl Set of integers representing platforms.
     * @param r Rating integer.
     */
    public Game(String n, float pr, String pb, String g, Set<Integer> pl,
    int r) {
        name = n;
        price = pr;
        genres = g;
        platforms = pl;
        rating = r;
    }
    
    public Game(Game game) {
        this.name = game.name;
        this.price = game.price;
        this.genres = game.genres;
        this.platforms = game.platforms;
        this.rating = game.rating;
    }

    /**
     * This list converts the int code for platforms into their respective platform strings
     * @param valCode Set of integers representing platform in the list
     * @return an arrayList of platform strings
     */
    public static ArrayList<String> convertPlatforms(Set<Integer> valCode) {
        // Empty list to be filed with valid platforms
        ArrayList<String> platformList = new ArrayList<String>();
        // Reference list containing all platforms in order according to their code
        ArrayList<String> platforms = new ArrayList<String>(Arrays.asList(" Windows", "Mac", " Linux"));
        for (int i = 0; i < 3; i++) {
            if (valCode.contains(i)) {
                platformList.add(platforms.get(i));
            }
        }
        return platformList;
    }
    
   public String toString() {
       String fullObj = name + "\nRating: " + rating + "%\nPrice: $" + price + "\n" + "Platforms:";
       ArrayList<String> platformList = convertPlatforms(platforms);
       for (int i = 0; i < platformList.size() - 1; i++) {
           fullObj += " " + platformList.get(i) + ",";
       }
       fullObj +=  platformList.get(platformList.size() - 1) + "\nGenre Tags: " + genres + "\n";  
       fullObj += "__________________________";
       return fullObj;
   }
    
//    public String toStringNoTitle() {
//        String fullObj = "\n Rating: " + rating + "\n Genre Tags: ";
//        ArrayList<String> genreList = convertGenres(genres);
//        for (int i = 0; i < genreList.size() - 1; i++) {
//            fullObj += " " + genreList.get(i) + ",";
//        }
//        fullObj += " " + genreList.get(genreList.size() - 1) + "\n";        
//        return fullObj;
//    }
    
    
    // Auto generated getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public String getGenres() {
        return genres;
    }
    
//    /**
//     * Used for table formatting in UI output, subject to change
//     * @return deeznuts
//     */
//    public String getStringGenres() {
//        ArrayList<String> genres = convertGenres(this.genres);
//        String ret = "<html>";
//        for (String s : genres) {
//            ret += s + "<br>";
//        }
//        ret += "</html>";
//        return ret;
//    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Set<Integer> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Set<Integer> platforms) {
        this.platforms = platforms;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Game)) {
            return false;
        }
        
        Game g = (Game)o;
        return this.name.equals(g.getName());
    }

}
