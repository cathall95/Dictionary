import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
public class Project3
{
  public static int position = 0; //declaring a global variable
  public static void main(String [] args) throws IOException
  {
   String [] options = new String[7]; 
   String temporary,temps,word;
   int choices;
   boolean found;
   	options[0] = "search the dictionary"; //setting up the menu
  	 options[1] = "add to the dictionary";
  		 options[2] = "delete an entry in the dictionary";
  			 options[3] = "output the dictionary";
   				options[4] = "Add the lexicographer approved words to the dictionary";
   					options[5] = "Output a summary of the dictionary";
   						options[6] = "Enter a filename to produce a unique list of words";
   String filename = "";
   filename = JOptionPane.showInputDialog(null,"What would you like to call the dictionary file?","Naming dictionary file",1); //getting a filename off of the user
   if (!(filename == null)) //checking that the user entered something
   {
   ArrayList<String> dictionaryWords = new ArrayList<String>();
    File dictionary = new File(filename); //setting up the file
    if(!dictionary.exists())
    dictionary.createNewFile(); //creating the file if it doesn't exist
  Scanner input = new Scanner(dictionary);
  	while(input.hasNext()) //reading the file
  	{
  		temporary = input.nextLine();
  		dictionaryWords.add(temporary); //putting the file into an arraylist
  	}
  	input.close();
  ArrayList<String> tempWords = new ArrayList<String>();
  File TemporaryFile = new File("TemporaryFile.txt"); //setting up the temporary file
   if(!TemporaryFile.exists())
   TemporaryFile.createNewFile(); //creating it if it doesn't exist
   Scanner keyboard = new Scanner(TemporaryFile);
  	while(keyboard.hasNext())
  		{
  		temps = keyboard.nextLine();
  		tempWords.add(temps); //adding it into an arraylist
  		}
  	  keyboard.close();
  String choice = (String) JOptionPane.showInputDialog(null,"Choose one.","Input",1,null,options,options[0]); //displaying the menu to the user
   if(!(choice == null))
   { 
   	if (choice.contentEquals("search the dictionary")) //deciding which part of the program to run
   	{
  	word = JOptionPane.showInputDialog(null,"Enter the word you would like to search for","Search for a word",1); //getting the word to search for
  	validate(word); //validating the word
   	   int choicee = 2;
   	    found = Search(dictionaryWords,word); //checking the dictionary for the word
   	     if(found)
   	      JOptionPane.showMessageDialog(null,word + " was found  in the dictionary.","Found in dictionary",1);
   	      else{
	   	      found = Search(tempWords,word); //checking the temporary file for the word
	   	      if(found)
	   	      JOptionPane.showMessageDialog(null,word + " was found  in the temporary file.","Found in temporary file",1);
   	          }
   	        if(!found)
   	        {
   	        Add(tempWords,dictionaryWords,choicee,word); //adding the word to the temporary file if not found
	          }
            }
   	  		else if (choice.contentEquals("add to the dictionary"))
   	  		{
   	  		int choicee = 1;
   	  		String words = "";
  			 Add(tempWords,dictionaryWords,choicee,words); //adding a word to the dictionary 
			 }
  				 else if (choice.contentEquals("delete an entry in the dictionary"))
 		 			 Delete(tempWords,dictionaryWords,filename);
		 			  else if (choice.contentEquals("output the dictionary"))
  						 Output(dictionaryWords); //displaying the dictionary
  						 	else if (choice.contentEquals("Add the lexicographer approved words to the dictionary"))
  							 	Lexicographer(tempWords,dictionaryWords,filename);
   									else if (choice.contentEquals("Output a summary of the dictionary")) //outputting a summary of the dictionary
   									{
	   							  String results = "";
  							 		results = LongestAndShortest(dictionaryWords,results);
  							 		WordsPerLetter(dictionaryWords,results);
   									  Palindrome(dictionaryWords); 
									   }
  											 else
   												UniqueWords(dictionaryWords,tempWords); 
   }
  }
  }
  
  public static void validate(String word) //validating user input
  {
  boolean valid = true;
  if(word == null)
  valid = false;
  if(word.contains(" "))
  valid = false;
  for(int counter = 0;counter < 10;counter++) //checking if there numbers in user input
  if(word.contains(Integer.toString(counter)))
  valid = false;
  if(!valid)
  JOptionPane.showMessageDialog(null,"That is not a valid word","invalid word",2);	
  if(!valid)
  return;  
  }
  
  public static void Add(ArrayList<String> tempWords,ArrayList<String> dictionaryWords,int choice,String word) throws IOException //adding words to the dictionary
  {
  boolean founds = false;
  if(choice == 1)
  {
  word = JOptionPane.showInputDialog(null,"Enter the word you would like to enter into the dictionary","Adding to dictionary",1); //getting user input
  if(word == null)
  return;
  validate(word); //validating it
  founds = Search(dictionaryWords,word); //searching for the word
  if(founds) 
  JOptionPane.showMessageDialog(null,word + " already exists in the dictionary.","Found in dictionary",1);
  else
  {
  founds = Search(tempWords,word);
  if(founds)
   JOptionPane.showMessageDialog(null,word + " already exists in the temporary file awaiting lexiocgrapher approval.","Found in temp file",1);
  }
  }
 if(founds == false)
  {
  tempWords.add(word); 
  Collections.sort(tempWords); //putting the temporary file in alphabetical order
   FileWriter aFileWriter = new FileWriter("TemporaryFile.txt",false);
   PrintWriter out = new PrintWriter(aFileWriter); 
    for(int counter = 0;counter<tempWords.size();counter++)
   	 out.println(tempWords.get(counter)); //adding the word to the temporary file
   	 out.close();
    aFileWriter.close();
   JOptionPane.showMessageDialog(null,word + " has been added to the temporary file pending lexicographer approval","Added to temp file",1);
  }
  }
  
  public static boolean Search(ArrayList<String> ArrayToSearch,String word) //searching for a word
  {
  String temp;
  for(int counter = 0;counter < ArrayToSearch.size();counter++)
  {
  temp = ArrayToSearch.get(counter);
  if(temp.contentEquals(word.toLowerCase())) //checking if two words are equal
  {
  position = counter; //giving the position of the word
  return true;
  }
  }
  return false;
  }
  
  public static void Delete(ArrayList<String> tempWords,ArrayList<String> dictionaryWords,String filename) throws IOException //deleting a word from the dictionary 
  {
  String word = JOptionPane.showInputDialog(null,"Enter the word you would like to delete from the dictionary","Deleting from the dictionary",1); //getting user input
  if(word == null)
  return;
  validate(word);
  boolean found = false;
  found = Search(dictionaryWords,word); //checking if it exists
  if(found)
  {
  dictionaryWords.remove(position);   //removing it from dictionary
  FileWriter aFileWriter = new FileWriter(filename,false);
   PrintWriter out = new PrintWriter(aFileWriter); 
    for(int counter = 0;counter<dictionaryWords.size();counter++)
   	 out.println(dictionaryWords.get(counter));
   	 out.close();
    aFileWriter.close();
   JOptionPane.showMessageDialog(null,word + " has been deleted from the dictionary","deleted from dictionary",1);
  }
  else
  {
  found = Search(tempWords,word);
  tempWords.remove(position); //removing it from temporary file
  FileWriter afilewrite = new FileWriter(filename,false);
   PrintWriter outer = new PrintWriter(afilewrite); 
    for(int counter = 0;counter<tempWords.size();counter++)
   	 outer.println(tempWords.get(counter));
   	 outer.close();
    afilewrite.close();
   JOptionPane.showMessageDialog(null,word + " has been deleted from the temporary file","deleted from temporary file",1);
  }
  if(found == false)
     JOptionPane.showMessageDialog(null,word + " wasn't found in either the dictionary or the temporary file","Word not found",1); //telling the user it doesn't exist

  }
  
  public static void Output(ArrayList<String> dictionaryWords) throws IOException //displaying the dictionary
  {
  String[] dictionaryAsArray = dictionaryWords.toArray(new String[dictionaryWords.size()]);
     JScrollPane jpane = new JScrollPane(new JList<String>(dictionaryAsArray)) { //setting up the dialog box
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(200, 250);
                    }
                };;
             JOptionPane.showMessageDialog(null,jpane,"Dictionary Words",1); //displaying the dialog box
  }
  
  public static void Lexicographer(ArrayList<String> tempWords,ArrayList<String> dictionaryWords,String filename) throws IOException //adding the temporary file to the dictionary
  {
  ArrayList<String> addToDictionary = new ArrayList<String>();
  for(int counter = 0;counter<tempWords.size();counter++)
  addToDictionary.add(tempWords.get(counter)); //merging the files together
  for(int counter = 0;counter<dictionaryWords.size();counter++)
  addToDictionary.add(dictionaryWords.get(counter));
  Collections.sort(addToDictionary); //putting them in alphabetical order
   FileWriter aFileWriter = new FileWriter(filename,false);
   PrintWriter out = new PrintWriter(aFileWriter);  //writing to the dictionary
    for(int counter = 0;counter<addToDictionary.size();counter++)
   	 out.println(addToDictionary.get(counter));
   	 out.close();
    aFileWriter.close();
   JOptionPane.showMessageDialog(null,"All words in the temporary file have been added to the dictionary","Added to dictionary",1);
  }
  
  public static void WordsPerLetter(ArrayList<String> dictionaryWords,String results) //checking for what letter every word start with 
	{
	char [] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}; //an array with every letter in it
	int [] letter = new int[26];
	boolean found = false;
	String temp = "";
	for(int counter = 0;counter < dictionaryWords.size();counter++)
	{
	for(int count = 0;count<letters.length;count++)
	{
	temp = Character.toString(letters[count]);
	if((dictionaryWords.get(counter)).startsWith(temp)) //checking what it starts with
	{
	letter[count]++; //incrementing it
	found = true;
	}
	}
	}
	for(int counter = 0;counter<letter.length;counter++)
	results += letters[counter] + " was the first letter " + letter[counter] + " times.\n";	
	results += "\nThe palindromes in the dictionary are:";
	JOptionPane.showMessageDialog(null,results,"Letter frequency",1); //outputting the results 
	}
  
   public static String LongestAndShortest(ArrayList<String> dictionaryWords,String results)throws IOException //checking for the longest and shortest file in the dictionary
	{
   int max = 0;
    int min = 1000;
    String ray,temp;
    ArrayList<String> longestWords = new ArrayList<String>();
     ArrayList<String> shortestWords = new ArrayList<String>(); //setting up array lists to hold the longest and shortest words
	    	for(int counter = 0;counter<dictionaryWords.size();counter++)
	    	{
        	ray = dictionaryWords.get(counter);
        	if (ray.length() > max) 
            	max = ray.length(); //getting the length of the longest word
       		if(ray.length() < min)
       		min = ray.length(); //getting the length of the shortest word
		}
		for(int counter = 0;counter<dictionaryWords.size();counter++)
		{
		temp = dictionaryWords.get(counter);
		if(temp.length() == max)
		longestWords.add(temp); //checking what words match these lengths and adding them if they do
		else if(temp.length() == min)
		shortestWords.add(temp);	
		}       
    int wordCount = dictionaryWords.size();
  	results = "There are " + wordCount + " words in the dictionary.\n\n";
    results += "The longest word/words are "; 
    results += longestWords.get(0);
    for(int counter = 1;counter<longestWords.size();counter++)
    results += " and " + longestWords.get(counter);
    results += "\n";
    results += "The shortest word/words are ";
    results += shortestWords.get(0);
     for(int counter = 1;counter<shortestWords.size();counter++)
    results += " and " + shortestWords.get(counter); 
    results += "\n\n";
    return results; //outputting the results
   }

   public static void Palindrome(ArrayList<String> dictionaryWords) //checking what palindromes are in the dictionary
  {
	  String aLineFromFile, reverse = "";
	  String line;
	  boolean palindrome;
      ArrayList<String> phrase = new ArrayList<String>();
	  for(int counter = 0;counter<dictionaryWords.size();counter++)
	  { 
	  palindrome = true;   
      aLineFromFile = dictionaryWords.get(counter); 
      char[] wordArr = aLineFromFile.toLowerCase().toCharArray();
     for(int i = 0, j = wordArr.length - 1; i < wordArr.length / 2; i++, j--)
      if(wordArr[i] != wordArr[j]) 
       palindrome = false;
	  if(palindrome)  
      phrase.add(aLineFromFile); //adding palindromes to an array list
      }
      String[] phraseAsArray = phrase.toArray(new String[phrase.size()]); //setting up the dialog box
     JScrollPane jpane = new JScrollPane(new JList<String>(phraseAsArray)) {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(200, 250);
                    }
                };;
             JOptionPane.showMessageDialog(null,jpane,"Palindromes",1);
   }
  
  public static void UniqueWords(ArrayList<String> dictionaryWords,ArrayList<String> tempWords) throws IOException //checking a file for unique words
  {
  String filename = "";
  String temp;
  boolean unique = true,found = false,duplicate = true,tempunique = true;
  String [] words;
  ArrayList<String> passageWords = new ArrayList<String>();
  ArrayList<String> noDuplicateWords = new ArrayList<String>();
  ArrayList<String> uniqueWords = new ArrayList<String>();
  filename = JOptionPane.showInputDialog(null,"Enter the name of the file you would like to display a unique list of words for","Filename input",1); //getting the name of the file
  if(filename != null)
  {
  File Passage = new File(filename);
  Scanner in = new Scanner(Passage);
  	while(in.hasNext())
  	{
  		temp = in.nextLine(); //getting rid of any unwanted parts from the file
  		temp = temp.toLowerCase();
  		temp = temp.replace("\\s+"," ");
  		temp = temp.replace(".","");
  		temp = temp.replace("\"","");
  		temp = temp.replace("!","");
  		temp = temp.replace("?","");
  		temp = temp.replace(",","");
  		temp = temp.replace(";","");
  		temp = temp.replace(":","");
  		temp = temp.replace("/","");
  		temp = temp.replace("\\","");
  		temp = temp.replace("--","-");
  		temp = temp.trim();
  		words = temp.split(" ");
  		for(int c = 0;c <words.length;c++)
  		passageWords.add(words[c]); //adding each word in the file to an arraylist
  	}
  	in.close();
  	for(int counter = 0;counter<passageWords.size();counter++)
  	{
  		unique = true;	
    		for(int count = 0;count<dictionaryWords.size();count++)
    			{
					if((passageWords.get(counter).contentEquals(dictionaryWords.get(count)))) //checking if the words are in the dictionary
						 unique = false;    
    			}
   		 if(unique)
    	 uniqueWords.add(passageWords.get(counter));
  	}
	Set<String> set = new HashSet<String>();  
        set.addAll(uniqueWords);   //getting rid of duplicates
  	String[] SetAsArray = set.toArray(new String[set.size()]);
     JScrollPane jpane = new JScrollPane(new JList<String>(SetAsArray)) { //setting up the dialog box
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(200, 250);
                    }
                };;
             JOptionPane.showMessageDialog(null,jpane,"Unique Words",1);
  	for(int counter = 0;counter<SetAsArray.length;counter++) //adding unique words to the temporary file
  		{
  		tempunique = true;	
    		for(int count = 0;count<tempWords.size();count++)
    			{
				if((SetAsArray[counter].contentEquals(tempWords.get(count))))
				 tempunique = false;   
    			}
    		if(tempunique)
    		noDuplicateWords.add(SetAsArray[counter]);
  		}
   FileWriter aFileWriter = new FileWriter("TemporaryFile.txt",false);
   	PrintWriter out = new PrintWriter(aFileWriter);
   for(int counter = 0;counter<tempWords.size();counter++)
   noDuplicateWords.add(tempWords.get(counter));
   Collections.sort(noDuplicateWords);
  	 for(int counter = 0;counter<noDuplicateWords.size();counter++)
   	 out.println(noDuplicateWords.get(counter));
   	 out.close();
    aFileWriter.close();
   }	  
  }
}