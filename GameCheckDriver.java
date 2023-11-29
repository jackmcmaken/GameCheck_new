import java.util.*;
import java.util.stream.Collectors;
import java.awt.EventQueue;
import java.io.*;

/**
 * Driver class for GameCheck
 * @author djax1, dinajs
 *
 */

public class GameCheckDriver {

    ArrayList<Game> gameArray = new ArrayList<Game>();
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Game> steamData = fillData("steam_search_beta4.1.txt");
        ArrayList<Game> epicData = fillData("epicGamesDB2.1.txt");
        ArrayList<Game> gogData = fillData("gogDB4.1.txt");
//        System.out.println(steamData.size());
//        System.out.println(gogData.size());
//        System.out.println(gogData.size());
        //      System.out.println(gameData.size());
        //      System.out.println(gameData.get(0).getName() + " " + gameData.get(0).getRating());
        //      ArrayList<String> genreTest = convertGenres(gameData.get(0).getGenres());

        //      for (int i = 0; i < genreTest.size(); i++) {
        //          System.out.println(genreTest.get(i));
        //      }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Display frame = new Display(steamData, epicData, gogData);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Reads data from file one line at a time to add game objects to an arrayList
     * @param fileName string file name containing the data, meant to read from a 3
     * column format
     * @return an arrayList containing all games listed in the file.
     * @throws FileNotFoundException
     */
    /**
     * Reads data from file one line at a time to add game objects to an arrayList
     * @param fileName string file name containing the data, meant to read from a 3
     * column format
     * @return an arrayList containing all games listed in the file.
     * @throws FileNotFoundException
     */
    public static ArrayList<Game> fillData(String fileName) throws FileNotFoundException {
        ArrayList<Game> gameData = new ArrayList<Game>();
        File rawData = new File(fileName);
        Scanner fileReader = new Scanner(rawData);
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] separatedLine = new String[5];
            separatedLine = line.split("\t");
            separatedLine = line.split("\t");
            if (separatedLine.length >= 5) {
                Set<Integer> platforms = new HashSet<Integer>();
                platforms = platformReader(separatedLine[2]);
                int rating = Integer.parseInt(separatedLine[3]);
                float price = Float.parseFloat(separatedLine[1]);
                gameData.add(new Game(separatedLine[0], price, "", separatedLine[4], platforms, rating));
            }
        }
        fileReader.close();
        return gameData;
    }

    /**
     * Converts the text string of 1s and 0s to an int code for the genres
     * @param binaryCode string of 1s and 0s, representing a game's genre
     * @return Int set that represent the genres that correspond to a game
     */
    public static Set<Integer> platformReader(String binaryCode) {
        Set<Integer> genres = new HashSet<Integer>();
        for (int i = 0; i < 3; i++) {
            if (binaryCode.substring(i, i + 1).equals("1")) {
                genres.add(i);
            }
        }
        return genres;
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
        ArrayList<String> platforms = new ArrayList<String>(Arrays.asList("Windows", "Mac", "Linux"));
        for (int i = 0; i < 3; i++) {
            if (valCode.contains(i)) {
                platformList.add(platforms.get(i));
            }
        }
        return platformList;
    }
       
    /**
     * Searches based off of multiple criteria.
     * @param list ArrayList<Integer> list containing numbers corresponding to actions.
     * @param info ArrayList<Object> list continaing extra info that the sub functions may need.
     */
    static Set<Game> totalSearch(ArrayList<Integer> list, ArrayList<Object> info, ArrayList<Game> g) {
        ArrayList<Game> publisherInfo = new ArrayList<Game>();
        ArrayList<Game> genreInfo = new ArrayList<Game>();
        ArrayList<Game> platformInfo = new ArrayList<Game>();
        //TODO by Dina
        if (list.contains(1)) {
            //search publisher
            publisherInfo = searchPublisher((String)info.get(0), g);
        }
        if (list.contains(2)) {
            //search genre  
            genreInfo  = searchGenre((String)info.get(1), g);
        }
        if (list.contains(3)) {
            //search platforms
            platformInfo = searchPlatforms((String)info.get(2), g);
        }

        Set<Game> finalList = new HashSet<Game> ();
        finalList.addAll(publisherInfo);
        finalList.addAll(genreInfo);
        finalList.addAll(platformInfo);

//        Iterator<Game> finalListIterator = finalList.iterator();
//
//        while(finalListIterator.hasNext()) {
//            System.out.println(finalListIterator.next().toString());
//        }

        return finalList;

    }

    /**
     * Exclusively searches for games (e.g. only returns Games that contain ALL entered criteria)
     * Uses same list/info numbering system as totalSearch()
     */
    static Set<Game> exclusiveTotalSearch(ArrayList<Integer> list, ArrayList<Object> info, ArrayList<Game> g) {
        
        ArrayList<Game> finalList = new ArrayList<Game>();
        
        if (list.contains(1) && list.contains(2) && list.contains(3)) {
            // search publisher, genre, and platform
            finalList = searchPublisher((String)info.get(0), g);
            ArrayList<Game> genreList = searchGenre((String)info.get(1), g);
            ArrayList<Game> platformList = searchPlatforms((String)info.get(2), g);
            finalList.retainAll(genreList);
            finalList.retainAll(platformList);
            
        } else if (list.contains(1) && list.contains(2)) {
            //search publisher and genre
            finalList = searchPublisher((String)info.get(0), g);
            ArrayList<Game> genreList = searchGenre((String)info.get(1), g);
            finalList.retainAll(genreList);
            
        } else if (list.contains(2) && list.contains(3)) {
            //search genre and platform
            finalList = searchGenre((String)info.get(1), g);
            ArrayList<Game> platformList = searchPlatforms((String)info.get(2), g);
            finalList.retainAll(platformList);
            
        } else if (list.contains(1) && list.contains(3)) {
            //search publisher and platform
            finalList = searchPublisher((String)info.get(0), g);
            ArrayList<Game> platformList = searchPlatforms((String)info.get(2), g);
            finalList.retainAll(platformList);
            
        } else if (list.contains(1)) {
            // publisher
            finalList = searchPublisher((String)info.get(0), g);
            
        } else if (list.contains(2)) {
            // genre
            finalList  = searchGenre((String)info.get(1), g);
            
        } else {
            // platform
            finalList = searchPlatforms((String)info.get(2), g);
            
        }
                 
        return new HashSet<Game>(finalList);
        
    }

    /**
     * Search algorithm for finding games based on name
     * @param name String representation of user's input
     * @return an arrayList of Games
     */
    static ArrayList<Game> searchName (ArrayList<Game> g, String name) {
        ArrayList<Game> newArray = new ArrayList<Game>();
        name = name.toLowerCase();
        for (int i = 0; i < g.size(); ++i) {
            if (g.get(i).getName().equalsIgnoreCase(name)) {
                newArray.add(g.get(i));
            } else if(g.get(i).getName().toLowerCase().contains(name)) {
                newArray.add(g.get(i)); 
            }
        }

//        for(int i = 0; i < newArray.size(); ++i) {
//            System.out.println(newArray.get(i).toString());
//        }
        return newArray;
    }

    /**
     * Search algorithm for finding games based on publishers
     * @param publisher String representation of user's input
     * @return an arrayList of Games
     */
    static ArrayList<Game> searchPublisher (String publisher, ArrayList<Game> g) {
        ArrayList<Game> newArray = new ArrayList<Game>();

        for (int i = 0; i < g.size(); ++i) {
            if (g.get(i).getPublisher().equalsIgnoreCase(publisher)) {
                newArray.add(g.get(i));
            }
        }
        //for(int i = 0; i < newArray.size(); ++i) {
        //  System.out.println(newArray.get(i).toString());
        //}
        return newArray;
    }

    /**
     * Search algorithm for finding games based on genre
     * @param genre String representation of user's input
     * @return an arrayList of Games
     */
    static ArrayList<Game> searchGenre (String genre, ArrayList<Game> g) {
        ArrayList<Game> newArray = new ArrayList<Game>();
        
        String newGenre = genre.toLowerCase();
        
        for (int i = 0; i < g.size(); ++i) {
            String gameGenres = g.get(i).getGenres().toLowerCase();
            if (gameGenres.contains(newGenre)) {
                newArray.add(g.get(i));
            }
        }
//        for (int i = 0; i < g.size(); i++) {
//            ArrayList<String> genres = GameCheckDriver.convertGenres(g.get(i).getGenres());
//            List<String> lower = genres.stream().map(String::toLowerCase).collect(Collectors.toList());
//            if (lower.contains(genre.toLowerCase())) {
//                newArray.add(g.get(i));
//            }
//        }
        //for(int i = 0; i < newArray.size(); ++i) {
        //  System.out.println(newArray.get(i).toString());
        //}
        return newArray;
    }

    /**
     * Search algorithm for finding games based on platforms
     * @param platforms String representation of user's input
     * @return an arrayList of Games
     */
    static ArrayList<Game> searchPlatforms (String platform, ArrayList<Game> g) {
        ArrayList<Game> newArray = new ArrayList<Game>();

        for (int i = 0; i < g.size(); ++i) {
            ArrayList<String> platforms = convertPlatforms(g.get(i).getPlatforms());
            List<String> lower = platforms.stream().map(String::toLowerCase).collect(Collectors.toList());
            if (lower.contains(platform.toLowerCase())) {
                newArray.add(g.get(i));
            }
        }
        //for(int i = 0; i < newArray.size(); ++i) {
        //  System.out.println(newArray.get(i).toString());
        //}
        return newArray;
    }

    static ArrayList<Game> sortRating(ArrayList<Game> g) {
        //TODO Maybe
        //        for (int i = 1; i < gameArray.size(); ++i) {
        //            int key = gameArray.get(i).getRating();
        //            int j = i - 1;
        //
        //            while (j >= 0 && gameArray.get(j).getRating() > key) {
        //              gameArray.set(j+1, gameArray.get(j));
        //                j = j - 1;
        //            }
        //            gameArray.get(j+1).setRating(key);
        //        }
        // Heapify function is called for the items in
        // the array.
        for (int i = g.size()/2 - 1; i >= 0; --i) {
            heapify(g, g.size(), i, 1);
        }

        // Swaps each item in the array.
        for (int i = g.size()-1; i > 0; --i) {
            swap(g, i, 0);
            heapify(g, i, 0, 1);
        }
        return g;
    }

    static ArrayList<Game> sortPrice (ArrayList<Game> g) {
        //TODO Maybe
        //      for (int i = 1; i < gameArray.size(); ++i) {
        //            float key = gameArray.get(i).getPrice();
        //            int j = i - 1;
        // 
        //            while (j >= 0 && gameArray.get(j).getPrice() > key) {
        //              gameArray.set(j+1, gameArray.get(j));
        //                j = j - 1;
        //            }
        //            gameArray.get(j+1).setPrice(key);
        //        }
        for (int i = g.size()/2 - 1; i >= 0; --i) {
            heapify(g, g.size(), i, 2);
        }

        // Swaps each item in the array.
        for (int i = g.size()-1; i > 0; --i) {
            swap(g, i, 0);
            heapify(g, i, 0, 2);
        }
        return g;

    }

    static ArrayList<Game> sortName(ArrayList<Game> g) {
        g.sort((o1, o2)
                -> o1.getName().compareTo(o2.getName()));
        return g;
    }

    static void swap (ArrayList<Game> gameArray, int next, int previous) {
        Game temp = new Game(gameArray.get(previous));
        gameArray.set(previous, gameArray.get(next));
        gameArray.set(next, temp);
    }

    static void heapify (ArrayList<Game> gameArray, int size, int i, int sortType) {
        // Initializing variables needed for
        // tree creation.
        int root = i;
        int right = 2*i+2;
        int left = 2*i+1;

        if (sortType == 1) {
            // Checks to see if the left or right
            // is larger then the root.
            if ((right < size) && (gameArray.get(right).getRating() < gameArray.get(root).getRating())) {
                root = right;
            }
            if ((left < size) && (gameArray.get(left).getRating() < gameArray.get(root).getRating())) {
                root = left;
            }

            // Checks to make sure the largest is root.
            if (root != i) {
                swap(gameArray, root, i);

                // Heapifies the subtrees.
                heapify(gameArray, size, root, sortType);
            }
        } else if (sortType == 2) {
            // Checks to see if the left or right
            // is larger then the root.
            if ((right < size) && (gameArray.get(right).getPrice() > gameArray.get(root).getPrice())) {
                root = right;
            }
            if ((left < size) && (gameArray.get(left).getPrice() > gameArray.get(root).getPrice())) {
                root = left;
            }

            // Checks to make sure the largest is root.
            if (root != i) {
                swap(gameArray, root, i);

                // Heapifies the subtrees.
                heapify(gameArray, size, root, sortType);
            }
        }

    }
}
