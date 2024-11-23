import java.util.Scanner;

public class Main {
      
   public static Scanner input = new Scanner (System.in);
    // create obj from serach engine class
   public static Search_Engine search = new Search_Engine();
    
   
  
    
   //main menu function
   public static int menu()
   {  
   
      System.out.println("1. Retrieve term. ");
      System.out.println("2. Boolean Retrieval. ");
      System.out.println("3. Ranked Retrieval.");
      System.out.println("4. Indexed Documents.");
      System.out.println("5. Indexed Tokens.");
      System.out.println("6. Exit.");
      
      System.out.println("Choose an option: ");
      int option = input.nextInt();
      return option;
   }
    //This method displays a menu for retrieval options,accepts a user query&option,
    //retrieves and prints the document IDs based on the selected retrieval method.
   public static void Retrieval_Term()
   {
      int firstOption ;
      System.out.println("############ Retrieval Term #############");
      
     //display retrieval menu option
      System.out.println("1. index");
      System.out.println("2. inverted index");
      System.out.println("3. inverted index using BST");
      System.out.println("4. inverted index using AVL");
      System.out.println("Choose an Option: ");
      firstOption = input.nextInt();
      
      // Prompts the user to enter a search term.
      System.out.println("Enter Search Keyword:");
      String str = ""; //initalize string
      str = input.next();
      
      System.out.print("Documents IDs Result: ");
      
   //Calls the 'Term_Retrieval' method on the 'search' object with the (trimmed&lowercase) version of 'str'  
   //prints the retrieved document IDs based on the user's option  
      search.Term_Retrieval(str.trim().toLowerCase(), firstOption ).print();
      //returns linked list and use the print function in the LinkedList class 
      System.out.println("\n");
   
   }
  
//Displays a menu for Boolean retrieval options, accepts a user query of [AND/OR]
//retrieves and prints the document IDs based on the selected retrieval method.
   public static void Boolean_Retrieval_menu()
   {
      int option ;
      System.out.println("############ Boolean Retrieval ############");
      //retrieval menu 
      System.out.println("1. index");
      System.out.println("2. inverted index");
      System.out.println("3. inverted index using BST");
      System.out.println("4. inverted index using AVL");
      System.out.println("enter your choice");
      option = input.nextInt();
   
      System.out.println("Enter Search Keyword using Only[AND/OR] : ");
      System.out.println("");
      
      String str = input.nextLine(); 
       str = input.nextLine();
      
      
      System.out.println("Q#: " + str.trim()); //trim the string
      
      System.out.print("Result doc IDs: ");
   //Calls the 'Boolean_Retrieval' method on the search object with 'str' converted to (uppercase)  
   //prints the resulting document IDs based on the selected retrieval method  
      search.Boolean_Retrieval(str.trim().toUpperCase(), option ).print();
      System.out.println("\n");
   //Uppercase: makes the output of and,or always AND,OR 
   //by using trim it takes the words in between and lowercase them.
   }


//displays a menu for ranked retrieval options, accepts a user search query and choice, 
//and retrieves documents ranked by relevance based on the selected method.
   public static void Ranked_Retrieval_menu()
   {
      System.out.println("############ Ranked Retrieval ############");
      System.out.println("1. index");
      System.out.println("2. inverted index");
      System.out.println("3. inverted index using BST");
      System.out.println("4. inverted index using AVL");
      System.out.println("enter your choice");
      int secOption = input.nextInt();
   
      System.out.println("Enter a Search Keyword");
      String str = input.nextLine();
   //Reads another line to handle cases where the first 'nextInt()' leaves a newline in the input buffer.
      str = input.nextLine();
      
      System.out.println("# Q: " + str);
   //Calls the 'Ranked_Retrieval' method on the search object,with 'str', trimmed and lowercased,
   //retrieves based on the option chosen and ranks the documents based on relevance.
      search.Ranked_Retrieval(str.trim().toLowerCase(), secOption);
      System.out.println("\n");
   }
   
//calls the Indexed_Documents method on the search object to list all indexed documents.
   public static void Indexed_Documents_menu()
   {
      System.out.println("############ Indexed Documents ############");
      System.out.println("Indexed Documents " );
      //calls method in search engine class
      search.Indexed_Documents();
      System.out.println("");
   }
   
   //calls the Indexed_Tokens method on the search object to list all indexed tokens
   public static void Indexed_Tokens_menu()
   {
      System.out.println("############ Indexed Tokens ############");
      System.out.println("Tokens " );
      //calls method in search engine class
      search.Indexed_Tokens();
      System.out.println("");
   }
   
   public static void main(String[] args) {
       //object from serach engine class 
       //call load data on the object and the fucntions for stop and documents
      search.LoadData("stop.txt", "dataset.CSV");
   
     
      int selection;
      
      do { 
            //shows user the menu 
         selection = menu();
        
         switch (selection)
         {
                //Retreive the document based on the word  
            case 1:
               Retrieval_Term();
               break;
                        
                //to input a Boolean query that retrieves a collection of unranked documents.  
            case 2:
               Boolean_Retrieval_menu();
               break;
                        
                 //to input a query that gives a list of documents sorted by their ranked scores     
            case 3:
               Ranked_Retrieval_menu();
               break;
                
                //Shows number of documents in the index 
            case 4:
               Indexed_Documents_menu();
               break;
                
                //Shows number of vocabulary and tokens in the index  
            case 5:
               Indexed_Tokens_menu();
               break;
                        
                 //Exit option 
            case 6:
               break;
                        
            default:       
               System.out.println("Enter a valid Option.");
         }
      } while (selection != 6);
   }
   
}