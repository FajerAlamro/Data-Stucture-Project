// Index is mapping between document IDs and list of words in thr document
public class Index {

// Repeating words in the document
    class frequency  {
        int docID = 0;
        int Frequency = 0; // Number of times the word appears
        String msg = "Document ";
    } // end of frequency class

    class Document {
            int docID;
            LinkedList <String> DocArray; 

            public Document() {
                docID = 0;
                DocArray = new LinkedList <String>();
            }

            // insert new word in document after the current
            public void addNew (String word) {
                DocArray.insert(word);
            }

           // to search about word
           public boolean found(String word) {
           // if its empty that means its not there
               if (DocArray.empty())
                   return false;

               DocArray.findFirst();
               for ( int i = 0 ; i < DocArray.size ; i++)  {
                   if ( DocArray.retrieve().compareTo(word) == 0) // to check if they are exactly same or not
                       return true;
                 // If not, it will proceed with the rest of the document
                 DocArray.findNext();
               }
               return false;
           }
    } // end of Document class  


// attributes for index class    
    Document [] Docs;
    frequency [] Freqs;

    
    public Index() {
        Freqs = new frequency [50]; // from 0 to 49
        Docs = new Document [50]; // from 0 to 49

        for ( int i = 0 ; i < Docs.length ; i++) {
            Docs [i] = new Document();
            Docs [i].docID = i ;
        }
    }
        
    // add the given word into document
    public void addDocument ( int docID, String data) {
            Docs[docID].addNew(data);
    }
    
    // print the info for document
    public void printDocment (int docID) {
    
        // if list is empty
        if ( Docs[docID].DocArray.empty())
            System.out.println("The Document it emoty!");
        else {
            Docs[docID].DocArray.findFirst(); // find first of the list
            
            for ( int i = 0; i< Docs[docID].DocArray.size ; i++) {
                System.out.println("The Document ID: "+Docs[docID].DocArray.retrieve());
                Docs[docID].DocArray.findNext();
            }
        }
    }
    
    
// return the documents that contains given word
public  boolean [] getDocs (String str) {

    boolean [] result = new boolean [50];
   // Give an initial value indicating that the word does not exist
    for (int i = 0 ; i < result.length ; i++)
        result[i] = false;
    // If word exist, the document will be true
    for (int i = 0 ; i < result.length ; i++)
        if (Docs[i].found(str))
            result[i] = true;

    return result;
}


        public LinkedList<Integer> AND_OR_Function (String str ) {
        
            if (! str.contains(" OR ") && ! str.contains(" AND "))   { // if there are both not exist
                str = str.toLowerCase().trim();
                
                LinkedList<Integer> result = new LinkedList<Integer>();
                boolean [] docs = getDocs(str); // get the documents that have this word
                
                for ( int i = 0 ; i < docs.length ; i++)
                    if (docs[i]) 
                        result.insert(i); // if document true (word exist into it), it will be added to the result linked list
                        
                return result;
            } else if (str.contains(" OR ") && str.contains(" AND ")) { // if its contain both of them
            
                String [] AND_ORs = str.split(" OR "); // less prioroty than "AND"
                
                LinkedList<Integer> result = AND_Function (AND_ORs[0]);
               
                for ( int i = 1 ; i < AND_ORs.length ; i++  )  {  // Moving on to the other words  
                    LinkedList<Integer> result2 =AND_Function (AND_ORs[i]);

                    result2.findFirst();
                    for ( int j = 0 ; j < result2.size() ; j++)  {
                        boolean found = false; 
                        
                        result.findFirst();
                        
                        while (! result.last())  {
                            if (result.retrieve().compareTo(result2.retrieve()) == 0 )  { // comparing between 2 words
                                found = true;
                                break;
                            }
                            result.findNext();
                        }
                        
                        if (result.retrieve().compareTo(result2.retrieve()) == 0 ) { // for last node!
                            found = true;
                            break;
                        }

                        if (!found )
                            result.insert(result2.retrieve()); // if does not exist we added to result linked list

                        result2.findNext();
                    }
                }
                return result;
            }
            
            else  if (str.contains(" AND "))
                return AND_Function (str); // if its contain only "AND"
            
            return OR_Function (str); // if its contain only "OR"
        }
        
        

        // find the documets  that have both words
        public LinkedList<Integer> AND_Function (String str) {
            String [] ANDs = str.split(" AND ");  // it will remove the "AND" of them
 
            LinkedList<Integer> result = new LinkedList<Integer>(); // Contains document numbers that contain the words
            
            boolean [] docs = getDocs(ANDs[0].toLowerCase().trim()); // Send the first word in a small letter and removing the spaces
            
            for ( int i = 0 ; i < docs.length ; i++) // docs indicate if the document contain this word or not
                if (docs[i]) 
                    result.insert(i); // if document true (word exist into it), it will be added to the result linked list
                            
            for ( int i = 1 ; i< ANDs.length ; i++) { // Moving on to the other words

                LinkedList<Integer> RemainWords = result;
                result = new LinkedList<Integer> ();

                docs = getDocs(ANDs[i].toLowerCase().trim()); // Send the word in a small letter and removing the spaces
                for ( int j = 0 ; j < docs.length ; j++)  {  
                    if (docs[j] )  { // if document true (word exist into it), added to the result linked list
                    
                        RemainWords.findFirst();
                        boolean found =  false;
                        while ( ! RemainWords.last()) {
                            if ( RemainWords.retrieve()== j )  {
                                found = true;
                                break;
                            }
                            RemainWords.findNext(); // it will move in the list until it found the word
                        }
                        
                        if ( RemainWords.retrieve()== j) // for last node
                            found = true;
                            
                        if (found)
                            result.insert(j); // if its exist, it will be added to the result linked list
                    }
                }
            }
            return result;
        }

        // find the documets that have eaither both words or one of them
        public LinkedList<Integer> OR_Function (String str)
        {
            String [] ORs = str.split(" OR "); // it will remove the "Or" of them

            LinkedList<Integer> result = new LinkedList<Integer> (); // Contains document numbers that contain the words

            boolean [] docs = getDocs(ORs[0].toLowerCase().trim()); // Send the first word in a small letter and removing the spaces

            for ( int i = 0 ; i < docs.length ; i++) // docs indicate if the document contain this word or not
                if (docs[i]) 
                    result.insert(i); // if document true (word exist into it), it will be added to the result linked list

            for ( int i = 1 ; i< ORs.length ; i++) { // Moving on to the other words
            
                docs = getDocs(ORs[i].toLowerCase().trim()); // Send the word in a small letter and removing the spaces
                
                 for ( int j = 0 ; j < docs.length ; j++)   {  
                  if (docs[j] )  {  // if document true (word exist into it), added to the result linked list

                        result.findFirst();
                        boolean found =  false;

                        while (! result.last() )
                        {
                            if ( result.retrieve() == j)
                            {
                                found = true;
                                break;
                            }
                            result.findNext(); // it will move in the list until it found the word
                        }
                        if ( result.retrieve() == j )
                        {
                            found = true;
                            break;
                        }

                        if (! found) // if does not exist we added to result linked list
                            result.insert(j);
                    } 
                }
            }
            return result;
        }
        
        // will print querying word with their  scores
        public void TF(String str)  {
        
            str = str.toLowerCase().trim(); // store it in small letter and removing the spaces 
            String [] words = str.split(" "); // We split the words from each other with a space
            
            Freqs = new frequency[50]; // from 0 to 49
            
            for ( int i = 0 ; i < 50 ; i++ )   { // Initialize
                Freqs[i] = new frequency();
                Freqs[i].docID = i;
                Freqs[i].Frequency = 0;
                Freqs[i].msg = "Document: " + i + " : ";
            }
            
            for ( int docs = 0 ; docs <50 ; docs++) {
            
                for ( int i = 0 ; i < words.length ; i++) {
                
                    Docs[docs].DocArray.findFirst();
                    int score = 0;
                    for ( int x = 0 ; x < Docs[docs].DocArray.size() ; x++ ) { // search for word in a document
                    
                        if (Docs[docs].DocArray.retrieve().compareTo(words[i]) == 0) // if word is found
                            score ++; // increment score for word
                        Docs[docs].DocArray.findNext();
                    }
                    
                    Freqs[docs].Frequency += score; // added to the Freqs array
                    Freqs[docs].msg +=" ( " + words[i] + ", " + score + " ) +"; 
                    
                } // end iner for loop
            } // end outer for loop
            
            
            for ( int i = 0 ; i < Freqs.length ; i ++) { 
                Freqs[i].msg = Freqs[i].msg.substring(0, Freqs[i].msg.length()-1); // removing the "+" in the end
                Freqs[i].msg += " = " + Freqs[i].Frequency;
            }
            
            mergesort(Freqs, 0 , Freqs.length-1 ); // sort the data from bigger to smaller
            
            System.out.println("The Results: ");
            
            for ( int i = 0 ;  Freqs[i].Frequency != 0 ; i++)
                System.out.println(Freqs[i].msg);
            
            System.out.println("\nDocIDt\tTotal Frequency");
            for ( int i = 0 ;  Freqs[i].Frequency != 0 ; i++)
                System.out.println(Freqs[i].docID + "\t\t" + Freqs[i].Frequency);
        }

    //This method must be O(nlog(n))!
    public static void mergesort ( frequency [] freqArray , int left , int right )  {
        if ( left >= right )
            return;
            
        int center = (right + left) / 2;
        
        mergesort (freqArray , left , center ) ;      // Sort first half
        mergesort (freqArray , center+1 , right ) ;  // Sort second half
        merge (freqArray , left , center , right ) ;        // Merge
    }
    

    private static void merge ( frequency [] freqArray , int left , int center , int right ) 
    {
        frequency [] Array = new frequency [ right - left + 1];
        int i = left , j = center + 1 , k = 0;
    
        while ( i <= center && j <= right )
        {
            if ( freqArray [i].Frequency >= freqArray [j].Frequency)
                Array [k++] = freqArray [ i++];
            else
                Array [k++] = freqArray [ j++];
        }
        
        if ( i > center )
            while ( j <= right )
                Array [ k ++] = freqArray[ j ++];
        else
            while ( i <= center )
                Array [ k ++] = freqArray[ i ++];
        
        for ( k = 0; k < Array.length ; k ++)
            freqArray [ k + left ] = Array [ k ];
    }
    
}