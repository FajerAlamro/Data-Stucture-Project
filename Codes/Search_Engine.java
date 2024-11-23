import java.io.File;
import java.util.Scanner;

public class Search_Engine {
    int tokens = 0;
    int vocab = 0;
    
    Index index;
    Inverted_Index invIndex;
    Inverted_Index_AVL invIndexAVL;
    Inverted_Index_BST invIndexBST;
    Inverted_Index_BST test;
    
    
    public Search_Engine ()
    {
        index = new Index();
        invIndex = new Inverted_Index();
        invIndexAVL = new Inverted_Index_AVL();
        invIndexBST = new Inverted_Index_BST();
        test = new Inverted_Index_BST();
    }
    
     
     /*
     processes the CSV documents and stopword files,
    split the text into a list base on whitespace, covert all to lowercase,
    removes stop words,punctuations,and non-alphanumerical characters
    and prints the count of tokens and vocabulary size.
     */
    public void LoadData (String stopWordsFile, String fileName)
    {
            try{
     //Create a File object for the stopword file, use a Scanner to read the entire content
                File stopwordsFile = new File (stopWordsFile);
                Scanner read = new Scanner (stopwordsFile).useDelimiter("\\Z");
             //into a single string stopWords
                String stopWords = read.next();
            //Replace newline characters in the stopwords string with spaces
                stopWords = stopWords.replaceAll("\n", " ");
             
   //Create a File object for the document file and use a Scanner to read the line.      
                File documentFile = new File(fileName);
                Scanner read1 = new Scanner (documentFile);
                String line = read1.nextLine();
                
                for ( int lineID = 0 ; lineID <50 ; lineID ++ ) 
                {
                    line = read1.nextLine().toLowerCase();
                    
                    int pos = line.indexOf(',');
                    int docID = Integer.parseInt( line .substring(0, pos));

                    String data = line.substring(pos+1, line.length() - pos).trim();
                    data = data.substring(0, data.length() -1);

                    data = data.toLowerCase();
                    
                    data = data.replaceAll("[\']", " ");
                    data = data.replaceAll("-", " ");
                    data = data.replaceAll("[^a-zA-Z0-9 ]", "").trim() ;

                    String [] words = data.split(" "); // --1

                    for (int i = 0; i < words.length ; i++)
                    {
                        String word = words[i].trim(); //--2
                    
                        if ( word.compareToIgnoreCase("") != 0)
                            tokens ++;
                        
                        this.test.addNew(docID, word);
                                
                        if ( ! stopWords.contains(word + " ")) //--3
                         {
                            this.index.addDocument(docID, word);
                            this.invIndex.addNew(docID, word);
                            this.invIndexAVL.addNew(docID, word);
                            this.invIndexBST.addNew(docID, word); 
                        }
                    }

                  
                }
                

                vocab = test.size();
      
                System.out.println("Tokens " + tokens);
                System.out.println("Vocab " + vocab);
                
                read.close();
                read1.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
    }
    
    public LinkedList<Integer> Boolean_Retrieval(String str , int DSType)
    {
     //Create a new LinkedList to store the document IDs that match the search query
        LinkedList<Integer> document = new LinkedList<Integer> ();
        switch (DSType)
        {
            case 1 :
                document = index.AND_OR_Function(str);
                break;
            case 2 :
                document = invIndex.AND_OR_Function(str);
                break;
            case 3:
                document = invIndexBST.AND_OR_Function(str);
                break;
            case 4:
                document = invIndexAVL.AND_OR_Function(str);
                break;
            default :
                System.out.println("Incorrect Data Structure");
                
        }
        //Return the list of document IDs that match the Boolean query.
        return document;
    }
        
    public void Ranked_Retrieval(String str , int DSType)
    {
        switch (DSType)
        {
            case 1 :
                index.TF(str);
                break;
            case 2 :
                invIndex.TF(str);
                break;
            case 3:
                invIndexBST.TF(str);
                break;
            case 4:
                invIndexAVL.TF(str);
                break;
            default :
                System.out.println("Incorrect Data Structure");
        }
    }
    
    
    
    public LinkedList<Integer> Term_Retrieval(String str , int DSType)
    {
        //create an object to store document IDs that match 
        LinkedList<Integer> document = new LinkedList<Integer> ();
        switch (DSType)
        {
            case 1 :
            {
             //boolean array to store each documentt that matches for the keyword.
                boolean [] document1 = index.getDocs(str);//call method in index class
                
       //Iterate through 50 documents and insert matching document IDs into the LinkedList.
                for ( int i = 0 ; i < 50 ; i++)
                    if ( document1[i] == true) //If the document matches, add it to the list.
                        document.insert(i);
            }
            break;
            
            case 2 :
                if (invIndex.found(str))//call method in inverted index class 
                {
                    boolean [] document1 = invIndex.invertedindex.retrieve().getDocs();
                    for ( int i = 0 ; i < 50 ; i++)
                        if ( document1[i] == true)
                            document.insert(i);
                }
                break;
                
            case 3:
                if (invIndexBST.found(str))
                    document = invIndexBST.invertedindexBST.retrieve().getDocs();//returns linked list
                break;
                
            case 4:
                if (invIndexAVL.found(str))
                    document = invIndexAVL.invertedindexAVL.retrieve().getDocs();
                break;
            default :
                System.out.println("Incorrect Data Structure");
                
        }
        return document;
    }
    
    
    //for each document it returns number of keywords in it 
    public void Indexed_Documents()
    {
        System.out.println(" All Documents Word Counts");
        //Iterates over the 50 documents
        for ( int i = 0 ; i < 50 ; i++ )
        {
            //gets the size of the index for the current document
            int size = index.Docs[i].DocArray.size();
            //prints the document number and its size
            System.out.println("Document# " + i +"  with size(" +  size + ")"  );
        }
        
    }
    
    
    public void Indexed_Tokens()
    {
        System.out.println("All Tokens and the Documents They Appear In ");
        invIndexBST.invertedindexBST.DisplayDataBST();//calls fucntion in BST class
        //or from AVL both correct 
    }
    
}