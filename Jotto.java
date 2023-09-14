package jotto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Jotto extends JFrame {

	private JPanel contentPane;
	private JTextField guessesSoFar;
	private JTextField guessInput;
	private static List<String> wordList;
	private static String secretWord;
	private static String guess;
	private static int guessCount = 0;
	private static Scanner scanner;
	private static int hintChar = -1;
	private static String hintLetters = "";
	private static String blank = "_ ";
	private JTextField hintBox;
	private JTextField invalidInputField;
	private static List<String> guessWordList;


	/**
	 * Launch the application.
	 * @return 
	 * @return 
	 * @return 
	 * @return 
	 * @throws FileNotFoundException 
	 */
	
	/* This method reads in the text file and generates a random word from it */
	public static String wordGenerate(String fileName) throws FileNotFoundException {
	       File inputFile = new File(fileName); 
	       scanner = new Scanner(inputFile);
	       wordList = new ArrayList<String>();
			while(scanner.hasNextLine())
				wordList.add(scanner.nextLine());
		    
			Random r = new Random();
			
		    int randomNum = r.nextInt(wordList.size());
		    String randomWord = wordList.get(randomNum);
		    scanner.close();
		    return randomWord;
	}
	
	/* This method takes the user's guess, compares it to the random word, and returns the letters which are the same*/
	public static String compare(){
		String same = ""; /* empty string which will contain the letters which are same between the guess and the secret word */
		for(int i = 0; i<guess.length();i++) {		/* nested loop, for the length of the guess */
			for(int j = 0; j<secretWord.length();j++) { 
				if(guess.charAt(i) == secretWord.charAt(j)){  
					same = same + guess.charAt(i);	/* same will equal the guess at index i if the letter is in the word*/
					
				}
				
				
			}
				
		}
		return same;
		
		
		
	}
	
	public static void main(String[] args) throws IOException {
			
		secretWord = wordGenerate("5-LetterWords.txt"); /* calls the method and generates a random word*/
		System.out.println(secretWord);
        try {
            File file = new File("jottoWords.txt");		/* creates a word list from all possible 5 letter words from a file*/
            Scanner scanner = new Scanner(file);
            guessWordList = new ArrayList<String>();
            while (scanner.hasNext()) {
                guessWordList.add(scanner.nextLine());		
            }
            scanner.close();
        } catch (FileNotFoundException e) {
    }

                    
              
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Jotto frame = new Jotto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		});
	}

	/**
	 * Create the frame.
	 */
	public Jotto() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 754, 464);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Title = new JLabel("Jotto");
		Title.setForeground(Color.RED);
		Title.setBounds(0, 11, 738, 33);
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setFont(new Font("News701 BT", Font.PLAIN, 22));
		contentPane.add(Title);
		
		JLabel NextGuess = new JLabel("Next Guess:");
		NextGuess.setFont(new Font("Humnst777 Cn BT", Font.PLAIN, 15));
		NextGuess.setHorizontalAlignment(SwingConstants.CENTER);
		NextGuess.setBounds(563, 55, 111, 14);
		contentPane.add(NextGuess);
		JTextArea wordsGuessed = new JTextArea();
		wordsGuessed.setBounds(0, 74, 301, 234);
		contentPane.add(wordsGuessed);
		wordsGuessed.setEditable(false);
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("Humnst777 Cn BT", Font.PLAIN, 15));
		quitButton.setForeground(Color.WHITE);
		quitButton.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		quitButton.setBackground(Color.RED);
		quitButton.setBounds(558, 392, 116, 23);
		contentPane.add(quitButton);
		
		JTextArea sameLetters = new JTextArea();
		sameLetters.setEditable(false);
		sameLetters.setBounds(358, 74, 106, 234);
		contentPane.add(sameLetters);
		
		JLabel lblNewLabel_1 = new JLabel("Same Letters");
		lblNewLabel_1.setFont(new Font("Humnst777 Cn BT", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(358, 54, 106, 14);
		contentPane.add(lblNewLabel_1);
		

	
	
		JButton GuessButton = new JButton("Guess Word");
		GuessButton.setFont(new Font("Humnst777 Cn BT", Font.PLAIN, 15));
		GuessButton.setForeground(Color.WHITE);
		GuessButton.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				/* when guess button is clicked, the word is validated */
				guess = guessInput.getText();
				/* if the guess equals the word */
				if(guess.equals(secretWord)){
					invalidInputField.setText("");
					guessCount ++;
					sameLetters.append(secretWord + "\n");
					wordsGuessed.append(secretWord + "\n");
					
					/* displays victory message */
					if(guessCount == 1) {
						guessesSoFar.setText("You win! The secret word was " + secretWord + ". You won in 1 guess. Are you sure you didn't cheat?");
					}
					else {
					invalidInputField.setText("");
					guessesSoFar.setText("You win! The secret word was " + secretWord + ". You won in " + Integer.toString(guessCount)+ " guesses.");
					}
					
				}
				/* if guess is valid but not the word*/
				else if(guess.length() == 5 & guessWordList.contains(guess)) {
					invalidInputField.setText("");
					wordsGuessed.append(guess + "\n");
					compare(); /* runs compare method */
					sameLetters.append(compare() + "\n"); /* returns the same string and appends in to the text box */
					guessCount ++;
					guessesSoFar.setText("Number of guesses: "+ Integer.toString(guessCount)); /* appends the guess count to the box */
					guessInput.setText("");}
					
				/* if the guess is invalid */
					else {
				invalidInputField.setText("Invalid Input. Please enter a 5 letter word. \n");}
				guessInput.setText("");
					}
				}
				
				
			
		);

		GuessButton.setBackground(Color.BLUE);
		GuessButton.setBounds(558, 109, 123, 23);
		contentPane.add(GuessButton);
		
		/* Give up button which quits the game and outputs the secret word */
		JButton GiveUpButton = new JButton("Give Up :(");
		GiveUpButton.setFont(new Font("Humnst777 Cn BT", Font.PLAIN, 15));
		GiveUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GiveUpButton.addMouseListener(new MouseAdapter() {
	
			
			
			public void mouseClicked(MouseEvent e) {
				guessesSoFar.setText("HAHA! You lose. The secret word was " + secretWord + ".");
			}
		});
		GiveUpButton.setBounds(558, 285, 116, 23);
		contentPane.add(GiveUpButton);
		/* Play again button which resets all the variables and text boxes */
		JButton PlayAgainButton = new JButton("Play Again ");
		PlayAgainButton.setFont(new Font("Humnst777 Cn BT", Font.PLAIN, 15));
		PlayAgainButton.setForeground(new Color(255, 255, 255));
		PlayAgainButton.setBackground(new Color(34, 139, 34));
		PlayAgainButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				wordsGuessed.setText("");
				guessesSoFar.setText("");
				sameLetters.setText("");
				guessCount = 0;
				hintBox.setText("");
				hintChar = -1;
				hintLetters = "";
				try {
					secretWord = wordGenerate("5-LetterWords.txt");
					System.out.println(secretWord);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
			}
		});
		
		PlayAgainButton.setBounds(558, 359, 116, 23);
		contentPane.add(PlayAgainButton);
		
		JLabel lblNewLabel = new JLabel("Words Guessed:");
		lblNewLabel.setFont(new Font("Humnst777 Cn BT", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 54, 301, 14);
		contentPane.add(lblNewLabel);
		
		JButton hint = new JButton("Hint");
		hint.setFont(new Font("Humnst777 Cn BT", Font.PLAIN, 15));
		hint.setBackground(new Color(0, 0, 0));
		hint.setForeground(new Color(255, 255, 0));
		
		guessesSoFar = new JTextField();
		guessesSoFar.setBounds(0, 378, 464, 31);
		contentPane.add(guessesSoFar);
		guessesSoFar.setColumns(10);
		guessesSoFar.setEditable(false);
		
		hintBox = new JTextField();
		hintBox.setBounds(546, 229, 150, 33);
		contentPane.add(hintBox);
		hintBox.setColumns(10);
		hintBox.setEditable(false);
		
		invalidInputField = new JTextField();
		invalidInputField.setHorizontalAlignment(SwingConstants.CENTER);
		invalidInputField.setEditable(false);
		invalidInputField.setForeground(Color.RED);
		invalidInputField.setBackground(Color.LIGHT_GRAY);
		invalidInputField.setBounds(489, 142, 241, 19);
		contentPane.add(invalidInputField);
		invalidInputField.setColumns(10);
		
		guessInput = new JTextField();

		guessInput.setBounds(558, 78, 116, 20);
		contentPane.add(guessInput);
		guessInput.setColumns(10);
		
		
		hint.addMouseListener(new MouseAdapter() {
			@Override
			/* this is a hint button which helps the user guess the word*/
			public void mouseClicked(MouseEvent e) {
				hintChar++; 
				hintLetters = hintLetters + secretWord.charAt(hintChar);
				hintBox.setText(hintLetters + blank.repeat(4-hintChar));/* outputs the number of letters hintChar is*/
				
				if(hintChar==4) {
					guessesSoFar.setText("HAHA! You lose. Did you really need that many hints?");
				}
				
			}
		});
		hint.setBounds(572, 197, 96, 21);
		contentPane.add(hint);
		

		

		

}
}