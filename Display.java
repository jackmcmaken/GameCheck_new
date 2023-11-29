import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * GUI for game catalog
 * 
 * @author Jack McMaken
 * 
 */
public class Display extends JFrame {

    /**
     * Default serial UID
     */
    private static final long serialVersionUID = 1L;

    private ArrayList<Game> games;
    private ArrayList<Game> epicGames;
    private ArrayList<Game> gogGames;
    private ArrayList<Game> liveArray;
    private ArrayList<Game> epicLiveArray;
    private ArrayList<Game> gogLiveArray;
    private JPanel contentPane;
    private String input;
    private JPanel submitQuestionsPanel;
    private JPanel searchResultPanel;
    private JPanel epicSearchResultPanel;
    private JPanel gogSearchResultPanel;
    private Font defaultFont = new Font("Helvetica", Font.BOLD, 12);
    private int sortByCode = 0;
    private Color textColor = new Color(189, 187, 187);

    /**
     * Generates JFrame with all necessary components
     * 
     * @param The array of games
     */
    public Display(ArrayList<Game> games, ArrayList<Game> epicGames, ArrayList<Game> gogGames) {
        this.games = games;
        this.epicGames = epicGames;
        this.gogGames = gogGames;
        this.liveArray = games;
        this.epicLiveArray = epicGames;
        this.gogLiveArray = gogGames;

        // Content pane setup
        setTitle("GameCheck");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1050, 550);
        contentPane = new JPanel();
        contentPane.setDoubleBuffered(true);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        setLayout(new GridLayout(1, 2, 0, 0));

        // Setup and show panel for search query
        submitQuestionsPanel = new JPanel();
        submitQuestionsPanel.setBackground(new Color(73, 73, 255));

        JLabel searchQuestion = new JLabel("Search for a game by name: ");
        searchQuestion.setFont(new Font("Helvetica", Font.BOLD, 15));
        searchQuestion.setForeground(textColor);
        JLabel genreSearchOption = new JLabel("Genre?");
        JLabel platformSearchOption = new JLabel("Platform?");
        platformSearchOption.setFont(defaultFont);
        genreSearchOption.setFont(defaultFont);
        genreSearchOption.setForeground(textColor);
        platformSearchOption.setForeground(textColor);       


        JTextField search = new JTextField(20);
        JTextField genre = new JTextField(15);
        JTextField platform = new JTextField(15);
        Font fieldFont = new Font("Helvetica", Font.PLAIN, 15);
        search.setFont(fieldFont); genre.setFont(fieldFont); platform.setFont(fieldFont);
        JButton submitQuestion = new JButton("Search");
        submitQuestion.setFont(defaultFont);
        JButton submitParams = new JButton("Search by params (inclusive)");
        submitParams.setFont(defaultFont);
        JButton submitExParams = new JButton("Search by params (exclusive)");
        submitExParams.setFont(defaultFont);
        JButton sortRating = new JButton("Sort by rating");
        sortRating.setFont(defaultFont);
        JButton sortName = new JButton("Sort by name");
        sortName.setFont(defaultFont);
        JButton sortPrice = new JButton("Sort by price");
        sortPrice.setFont(defaultFont);       

        // Enables use of enter key to press submit button
        getRootPane().setDefaultButton(submitQuestion);

        // Add actionlisteners to buttons
        submitQuestion.addActionListener(e -> showResults(search.getText()));
        submitParams.addActionListener(e -> displayParamSearch(genre.getText(), platform.getText(), 0));
        submitExParams.addActionListener(e -> displayParamSearch(genre.getText(), platform.getText(), 1));
        sortRating.addActionListener(e -> sortBy(0));
        sortName.addActionListener(e -> sortBy(1));
        sortPrice.addActionListener(e -> sortBy(2));

        submitQuestionsPanel.setLayout(new BoxLayout(submitQuestionsPanel, BoxLayout.PAGE_AXIS));

        submitQuestionsPanel.add(searchQuestion);
        submitQuestionsPanel.add(search);
        submitQuestionsPanel.add(submitQuestion);
        JLabel prompt = new JLabel("Or, filter games by these parameters:");
        prompt.setFont(new Font("Helvetica", Font.BOLD, 14));
        prompt.setText("<html>"+ "Or, filter games by these parameters:" +"</html>");
        submitQuestionsPanel.add(prompt);
        prompt.setForeground(textColor);

        submitQuestionsPanel.add(genreSearchOption);
        submitQuestionsPanel.add(genre);
        submitQuestionsPanel.add(platformSearchOption);
        submitQuestionsPanel.add(platform);        
        submitQuestionsPanel.add(submitParams);
        submitQuestionsPanel.add(submitExParams);
        JLabel sortInfo = new JLabel("Sort the list:");
        sortInfo.setFont(new Font("Helvetica", Font.BOLD, 14));
        sortInfo.setForeground(textColor);
        submitQuestionsPanel.add(sortInfo);
        submitQuestionsPanel.add(sortName);
        submitQuestionsPanel.add(sortRating);
        submitQuestionsPanel.add(sortPrice);

        contentPane.add(submitQuestionsPanel, BorderLayout.NORTH);

        // Prepare search result panel
        searchResultPanel = new JPanel();
        contentPane.add(searchResultPanel, BorderLayout.AFTER_LAST_LINE);
        searchResultPanel.setBackground(new Color(120, 121, 255)); 

        epicSearchResultPanel = new JPanel();
        epicSearchResultPanel.setBackground(new Color(163, 163, 255));
        contentPane.add(epicSearchResultPanel);

        gogSearchResultPanel = new JPanel();
        gogSearchResultPanel.setBackground(new Color(191, 191, 255));
        contentPane.add(gogSearchResultPanel);


    }

    /*
     * Generates and displays search results
     */
    private void showResults(String s) {
        input = s;

        searchResultPanel.removeAll();
        searchResultPanel.revalidate();
        searchResultPanel.repaint();
        epicSearchResultPanel.removeAll();
        epicSearchResultPanel.revalidate();
        epicSearchResultPanel.repaint();
        gogSearchResultPanel.removeAll();
        gogSearchResultPanel.revalidate();
        gogSearchResultPanel.repaint();

        // Set text based on result of search
        JTextArea displayGame = new JTextArea(23, 20);
        JTextArea epicDisplayGame = new JTextArea(23, 20);
        JTextArea gogDisplayGame = new JTextArea(23, 20);

        if (!input.isEmpty()) {
            // Search for game
            ArrayList<Game> selectedGames = GameCheckDriver.searchName(liveArray, input);
            ArrayList<Game> epicSelectedGames = GameCheckDriver.searchName(epicLiveArray, input); 
            ArrayList<Game> gogSelectedGames = GameCheckDriver.searchName(gogLiveArray, input);


            if (!selectedGames.isEmpty()) {    
                for (Game g : selectedGames) {
                    displayGame.append(g.toString() + "\n");
                }
            } else {
                displayGame.setText("Your search couldn't be found.");
            }

            if (!epicSelectedGames.isEmpty()) {
                for (Game g : epicSelectedGames) {
                    epicDisplayGame.append(g.toString() + "\n");
                }
            } else {
                epicDisplayGame.setText("Your search couldn't be found.");
            }

            if (!gogSelectedGames.isEmpty()) {
                for (Game g : gogSelectedGames) {
                    gogDisplayGame.append(g.toString() + "\n");
                }
            } else {
                gogDisplayGame.setText("Your search couldn't be found.");
            }           

        } else {
            displayGame.setText("Please enter a search query.");
        }

        displayGame.setFont(new Font("Helvetica", Font.PLAIN, 14));
        displayGame.setEditable(false);
        displayGame.setLineWrap(true);
        displayGame.setWrapStyleWord(true);
        displayGame.setCaretPosition(0);

        epicDisplayGame.setFont(new Font("Helvetica", Font.PLAIN, 14));
        epicDisplayGame.setEditable(false);
        epicDisplayGame.setLineWrap(true);
        epicDisplayGame.setWrapStyleWord(true);
        epicDisplayGame.setCaretPosition(0);

        gogDisplayGame.setFont(new Font("Helvetica", Font.PLAIN, 14));
        gogDisplayGame.setEditable(false);
        gogDisplayGame.setLineWrap(true);
        gogDisplayGame.setWrapStyleWord(true);
        gogDisplayGame.setCaretPosition(0);

        JLabel steam = new JLabel("Steam");
        steam.setForeground(Color.black);
        steam.setFont(new Font("Helvetica", Font.BOLD, 16));
        JLabel epic = new JLabel("Epic");
        epic.setForeground(Color.black);
        epic.setFont(new Font("Helvetica", Font.BOLD, 16));
        JLabel gog = new JLabel("GOG");
        gog.setForeground(Color.black);
        gog.setFont(new Font("Helvetica", Font.BOLD, 16));


        searchResultPanel.add(new JScrollPane(displayGame, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        searchResultPanel.add(steam);
        epicSearchResultPanel.add(new JScrollPane(epicDisplayGame, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        epicSearchResultPanel.add(epic);
        gogSearchResultPanel.add(new JScrollPane(gogDisplayGame, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        gogSearchResultPanel.add(gog);


        searchResultPanel.revalidate();
        searchResultPanel.repaint();
        epicSearchResultPanel.revalidate();
        epicSearchResultPanel.repaint();
        gogSearchResultPanel.revalidate();
        gogSearchResultPanel.repaint();

        System.out.println(input);            
    }

    /*
     * Generates and displays results of param search
     */
    private void displayParamSearch(String genre, String platform, int searchCode) {
        searchResultPanel.removeAll();
        searchResultPanel.revalidate();
        searchResultPanel.repaint();
        epicSearchResultPanel.removeAll();
        epicSearchResultPanel.revalidate();
        epicSearchResultPanel.repaint();
        gogSearchResultPanel.removeAll();
        gogSearchResultPanel.revalidate();
        gogSearchResultPanel.repaint();

        JLabel steam = new JLabel("Steam");
        steam.setForeground(Color.black);
        steam.setFont(new Font("Helvetica", Font.BOLD, 16));
        JLabel epic = new JLabel("Epic");
        epic.setForeground(Color.black);
        epic.setFont(new Font("Helvetica", Font.BOLD, 16));
        JLabel gog = new JLabel("GOG");
        gog.setForeground(Color.black);
        gog.setFont(new Font("Helvetica", Font.BOLD, 16));

        JTextArea textArea = new JTextArea(23, 20);
        textArea.setFont(new Font("Helvetica", Font.PLAIN, 14));
        JTextArea epicTextArea = new JTextArea(23, 20);
        epicTextArea.setFont(new Font("Helvetica", Font.PLAIN, 14));
        JTextArea gogTextArea = new JTextArea(23, 20);
        gogTextArea.setFont(new Font("Helvetica", Font.PLAIN, 14));         

        boolean entered = false;
        Set<Game> finalList = new HashSet<Game>();
        Set<Game> epicFinalList = new HashSet<Game>();
        Set<Game> gogFinalList = new HashSet<Game>();


        if (searchCode == 0) {           
            ArrayList<Object> inputArr = new ArrayList<Object>(Arrays.asList(null, genre, platform));
            if (genre.isEmpty() && platform.isEmpty()) { // both empty
                textArea.setText("You didn't enter anything.");
            } else if (platform.isEmpty()) {  // find only genre
                entered = true;
                finalList = GameCheckDriver.totalSearch(new ArrayList<Integer>(Arrays.asList(null, 2, null)), inputArr, liveArray);
                epicFinalList = GameCheckDriver.totalSearch(new ArrayList<Integer>(Arrays.asList(null, 2, null)), inputArr, epicLiveArray);
                gogFinalList = GameCheckDriver.totalSearch(new ArrayList<Integer>(Arrays.asList(null, 2, null)), inputArr, gogLiveArray);
            } else if (genre.isEmpty()) { // find only platform
                entered = true;
                finalList = GameCheckDriver.totalSearch(new ArrayList<Integer>(Arrays.asList(null, null, 3)), inputArr, liveArray);
                epicFinalList = GameCheckDriver.totalSearch(new ArrayList<Integer>(Arrays.asList(null, null, 3)), inputArr, epicLiveArray);
                gogFinalList = GameCheckDriver.totalSearch(new ArrayList<Integer>(Arrays.asList(null, null, 3)), inputArr, gogLiveArray);
            } else { // find genre and platform
                entered = true;
                finalList = GameCheckDriver.totalSearch(new ArrayList<Integer>(Arrays.asList(null, 2, 3)), inputArr, liveArray);
                epicFinalList = GameCheckDriver.totalSearch(new ArrayList<Integer>(Arrays.asList(null, 2, 3)), inputArr, epicLiveArray);
                gogFinalList = GameCheckDriver.totalSearch(new ArrayList<Integer>(Arrays.asList(null, 2, 3)), inputArr, gogLiveArray);

            }
        } else {
            ArrayList<Object> inputArr = new ArrayList<Object>(Arrays.asList(null, genre, platform));
            if (genre.isEmpty() && platform.isEmpty()) { // both empty
                textArea.setText("You didn't enter anything.");
            } else if (platform.isEmpty()) {  // find only genre
                entered = true;
                finalList = GameCheckDriver.exclusiveTotalSearch(new ArrayList<Integer>(Arrays.asList(null, 2, null)), inputArr, liveArray);
                epicFinalList = GameCheckDriver.exclusiveTotalSearch(new ArrayList<Integer>(Arrays.asList(null, 2, null)), inputArr, epicLiveArray);
                gogFinalList = GameCheckDriver.exclusiveTotalSearch(new ArrayList<Integer>(Arrays.asList(null, 2, null)), inputArr, gogLiveArray);
            } else if (genre.isEmpty()) { // find only platform
                entered = true;
                finalList = GameCheckDriver.exclusiveTotalSearch(new ArrayList<Integer>(Arrays.asList(null, null, 3)), inputArr, liveArray);
                epicFinalList = GameCheckDriver.exclusiveTotalSearch(new ArrayList<Integer>(Arrays.asList(null, null, 3)), inputArr, epicLiveArray);
                gogFinalList = GameCheckDriver.exclusiveTotalSearch(new ArrayList<Integer>(Arrays.asList(null, null, 3)), inputArr, gogLiveArray);
            } else { // find genre and platform
                entered = true;
                finalList = GameCheckDriver.exclusiveTotalSearch(new ArrayList<Integer>(Arrays.asList(null, 2, 3)), inputArr, liveArray);
                epicFinalList = GameCheckDriver.exclusiveTotalSearch(new ArrayList<Integer>(Arrays.asList(null, 2, 3)), inputArr, epicLiveArray);
                gogFinalList = GameCheckDriver.exclusiveTotalSearch(new ArrayList<Integer>(Arrays.asList(null, 2, 3)), inputArr, gogLiveArray);
            }
        }

        if (entered && finalList.isEmpty()) {
            textArea.setText("No results found.");
        } else if (entered) {
            ArrayList<Game> list = new ArrayList<Game>();

            list.addAll(finalList);

            if (sortByCode == 0) {
                list = GameCheckDriver.sortRating(list);
            } else if (sortByCode == 1) {
                list = GameCheckDriver.sortName(list);
            } else {
                list = GameCheckDriver.sortPrice(list);
            }
            for (Game g : list) {
                textArea.append(g.toString() + "\n");
            }
        }

        if (entered && epicFinalList.isEmpty()) {  
            epicTextArea.setText("No results found.");
        } else if (entered) {
            ArrayList<Game> epicList = new ArrayList<Game>();
            epicList.addAll(epicFinalList);

            if (sortByCode == 0) {
                epicList = GameCheckDriver.sortRating(epicList);
            } else if (sortByCode == 1) {
                epicList = GameCheckDriver.sortName(epicList);
            } else {
                epicList = GameCheckDriver.sortPrice(epicList);
            }

            for (Game g : epicList) {
                epicTextArea.append(g.toString() + "\n");
            }
        }

        if (entered && gogFinalList.isEmpty()) {
            gogTextArea.setText("No results found.");
        } else if (entered) {
            ArrayList<Game> gogList = new ArrayList<Game>();
            gogList.addAll(gogFinalList);

            if (sortByCode == 0) {
                gogList = GameCheckDriver.sortRating(gogList);
            } else if (sortByCode == 1) {
                gogList = GameCheckDriver.sortName(gogList);
            } else {
                gogList = GameCheckDriver.sortPrice(gogList);
            }

            for (Game g : gogList) {
                gogTextArea.append(g.toString() + "\n");
            }
        }

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);

        epicTextArea.setEditable(false);
        epicTextArea.setLineWrap(true);
        epicTextArea.setWrapStyleWord(true);
        epicTextArea.setCaretPosition(0);

        gogTextArea.setEditable(false);
        gogTextArea.setLineWrap(true);
        gogTextArea.setWrapStyleWord(true);
        gogTextArea.setCaretPosition(0);   

        searchResultPanel.add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        searchResultPanel.add(steam);
        searchResultPanel.revalidate();
        searchResultPanel.repaint();
        epicSearchResultPanel.add(new JScrollPane(epicTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        epicSearchResultPanel.add(epic);
        epicSearchResultPanel.revalidate();
        epicSearchResultPanel.repaint();
        gogSearchResultPanel.add(new JScrollPane(gogTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        gogSearchResultPanel.add(gog);
        gogSearchResultPanel.revalidate();
        gogSearchResultPanel.repaint();

    }

    /*
     * Sorts and displays games given a code to determine which parameter to sort by
     */
    private void sortBy(int code) {
        searchResultPanel.removeAll();
        searchResultPanel.revalidate();
        searchResultPanel.repaint();
        epicSearchResultPanel.removeAll();
        epicSearchResultPanel.revalidate();
        epicSearchResultPanel.repaint();
        gogSearchResultPanel.removeAll();
        gogSearchResultPanel.revalidate();
        gogSearchResultPanel.repaint();    

        JLabel steam = new JLabel("Steam");
        steam.setForeground(Color.black);
        steam.setFont(new Font("Helvetica", Font.BOLD, 16));
        JLabel epic = new JLabel("Epic");
        epic.setForeground(Color.black);
        epic.setFont(new Font("Helvetica", Font.BOLD, 16));
        JLabel gog = new JLabel("GOG");
        gog.setForeground(Color.black);
        gog.setFont(new Font("Helvetica", Font.BOLD, 16));        

        JTextArea textArea = new JTextArea(23, 20);
        JTextArea epicTextArea = new JTextArea(23, 20);
        JTextArea gogTextArea = new JTextArea(23, 20);

        textArea.setFont(new Font("Helvetica", Font.PLAIN, 14));
        epicTextArea.setFont(new Font("Helvetica", Font.PLAIN, 14));
        gogTextArea.setFont(new Font("Helvetica", Font.PLAIN, 14));
        if (code == 0) {
            liveArray = GameCheckDriver.sortRating(games);
            epicLiveArray = GameCheckDriver.sortRating(epicGames);
            gogLiveArray = GameCheckDriver.sortRating(gogGames);
            sortByCode = 0;
        } else if (code == 1) {
            liveArray = GameCheckDriver.sortName(games);
            epicLiveArray = GameCheckDriver.sortName(epicGames);
            gogLiveArray = GameCheckDriver.sortName(gogGames);
            sortByCode = 1;
        } else {
            liveArray = GameCheckDriver.sortPrice(games);
            epicLiveArray = GameCheckDriver.sortPrice(epicGames);
            gogLiveArray = GameCheckDriver.sortPrice(gogGames);
            sortByCode = 2;
        }

        for (int i = 0; i < liveArray.size(); i++) {
            textArea.append(i + 1 + ". " + liveArray.get(i).toString() +"\n");
        }
        for (int i = 0; i < epicLiveArray.size(); i++) {
            epicTextArea.append(i + 1 + ". " + epicLiveArray.get(i).toString() +"\n");
        }
        for (int i = 0; i < gogLiveArray.size(); i++) {
            gogTextArea.append(i + 1 + ". " + gogLiveArray.get(i).toString() +"\n");
        }


        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);

        epicTextArea.setEditable(false);
        epicTextArea.setLineWrap(true);
        epicTextArea.setWrapStyleWord(true);
        epicTextArea.setCaretPosition(0);

        gogTextArea.setEditable(false);
        gogTextArea.setLineWrap(true);
        gogTextArea.setWrapStyleWord(true);
        gogTextArea.setCaretPosition(0);               

        searchResultPanel.add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        searchResultPanel.add(steam);
        searchResultPanel.revalidate();
        searchResultPanel.repaint();
        epicSearchResultPanel.add(new JScrollPane(epicTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        epicSearchResultPanel.revalidate();
        epicSearchResultPanel.repaint();
        epicSearchResultPanel.add(epic);
        gogSearchResultPanel.add(new JScrollPane(gogTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        gogSearchResultPanel.revalidate();
        gogSearchResultPanel.repaint(); 
        gogSearchResultPanel.add(gog);

    }

}

