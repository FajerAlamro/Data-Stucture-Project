public class Inverted_Index {
        class frequency
        {
            int docID = 0;
            int Frequency = 0;
            String msg = "Document ";
        }
        
        
 // attributes for inverted index class    
        frequency [] Freqs;
        LinkedList <TermDocument> invertedindex; 

        public Inverted_Index() { // each node indicate a word, and each word contains word, document IDs and rank
            invertedindex = new LinkedList <TermDocument>();
            Freqs = new frequency [50];    
        }
            
        public int size() {
            return invertedindex.size();
        }

        public boolean addNew (int docID, String word) {
            if (invertedindex.empty()) { // if its empty, we initialize a new node 
               TermDocument TDoc = new TermDocument ();
                TDoc.setWord(word);
                TDoc.add_docID(docID);
                invertedindex.insert(TDoc);
                return true;
           }
           else {
                invertedindex.findFirst();
               while ( ! invertedindex.last()) { // it will move in all nodes except the last one
                    if ( invertedindex.retrieve().word.compareTo(word) == 0) { // if it was the same word
                        TermDocument TDoc2 = invertedindex.retrieve();
                        TDoc2.add_docID(docID);
                        invertedindex.update(TDoc2);
                        
                        return false;  // no new node added  
                    }
                   invertedindex.findNext();
                }
                if ( invertedindex.retrieve().word.compareTo(word) == 0) { // for the last node 
                    TermDocument TDoc2 = invertedindex.retrieve();
                    TDoc2.add_docID(docID);
                    invertedindex.update(TDoc2);
                    return false;   // no new node added   
                }
                
                TermDocument TDoc2 = new TermDocument (); // added the new node
                TDoc2.setWord(word);
                TDoc2.add_docID(docID);
                invertedindex.insert(TDoc2);
            }
            return true;
       }

        public boolean found(String word) {
               if (invertedindex.empty()) // the word does not exist
                   return false;

               invertedindex.findFirst();
               for ( int i = 0 ; i < invertedindex.size ; i++) {
                   if ( invertedindex.retrieve().word.compareTo(word) == 0)// if it founded
                       return true;
                       
                  invertedindex.findNext();// else it has to move to next node
               }
               
               return false; // not found
        }

        public LinkedList<Integer> AND_OR_Function (String str )  {
        
            if (! str.contains(" OR ") && ! str.contains(" AND ")) { // if there are both not exist
                str = str.toLowerCase().trim();
                
                LinkedList<Integer> result = new LinkedList<Integer>();
                
                if (this.found (str))  {
                    boolean [] docs = invertedindex.retrieve().getDocs(); // get the documents that have this word
                    for ( int i = 0 ; i < docs.length ; i++)
                        if (docs[i]) 
                            result.insert(i);
                }
                
                return result;
            } // end outer if
            
            else if (str.contains(" OR ") && str.contains(" AND ")) // if its contain both of them 
             {
                String [] AND_ORs = str.split(" OR "); // less prioroty than "AND"
                
                LinkedList<Integer> result = AND_Function (AND_ORs[0]);
               
                for ( int i = 1 ; i < AND_ORs.length ; i++  ){ // Moving on to the other words  
                   
                    LinkedList<Integer> result2 =AND_Function (AND_ORs[i]);

                    result2.findFirst();
                    for ( int j = 0 ; j < result2.size() ; j++) {
                        boolean found = false;
                        result.findFirst();
                        while (! result.last()){
                        
                            if (result.retrieve().compareTo(result2.retrieve()) == 0 ) { // comparing between 2 words
                            
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
                    } // end iner for loop
                } // end outer for loop

                return result;
            }
            
            else  if (str.contains(" AND "))  // if its contain only "AND"
                return AND_Function (str);
            
            return OR_Function (str); // if its contain only "OR"
        }
        

        public LinkedList<Integer> AND_Function (String str) {
            String [] ANDs = str.split(" AND "); // it will remove the "AND" of them
 
            LinkedList<Integer> result = new LinkedList<Integer>(); // Contains document IDs that contain the words

            if (this.found (ANDs[0].toLowerCase().trim())) { // Send the first word in a small letter and removing the spaces

                boolean [] docs = invertedindex.retrieve().getDocs(); // docs indicate if the document contain this word or not
                for ( int i = 0 ; i < docs.length ; i++)
                    if (docs[i]) 
                        result.insert(i); // if document true (word exist into it), it will be added to the result linked list
            }
            
            for ( int i = 1 ; i< ANDs.length ; i++) { // Moving on to the other words
            
                LinkedList<Integer> RemainWords = result;
                result = new LinkedList<Integer> ();

                if (this.found (ANDs[i].toLowerCase().trim())) { //Check whether the next word exists or not
                     
                boolean [] docs = invertedindex.retrieve().getDocs();
                    
                    for ( int j = 0 ; j < docs.length ; j++) {  
                        if (docs[j] )  { // if document true (word exist into it), added to the result linked list
                            RemainWords.findFirst();
                            boolean found =  false;
                            
                            while ( ! RemainWords.last()) {
                                if ( RemainWords.retrieve()==j) {
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
            }
            return result;
        }

        public LinkedList<Integer> OR_Function (String str)
        {
            String [] ORs = str.split(" OR "); // it will remove the "Or" of them

            LinkedList<Integer> result = new LinkedList<Integer> (); // Contains document IDs that contain the words
            
            if (this.found (ORs[0].toLowerCase().trim()))  {  // Send the word in a small letter and removing the spaces 
            
                boolean [] docs = invertedindex.retrieve().getDocs();
                
                for ( int i = 0 ; i < docs.length ; i++) // docs indicate if the document contain this word or not
                    if (docs[i]) 
                        result.insert(i);// if document true (word exist into it), it will be added to the result linked list
            } // end if
            
            for ( int i = 1 ; i< ORs.length ; i++) {
            
                if (this.found (ORs[i].toLowerCase().trim()))  { // Send the word in a small letter and removing the spaces
                
                    boolean [] docs = invertedindex.retrieve().getDocs();
                    
                    for ( int j = 0 ; j < docs.length ; j++) {  
                        if (docs[j] )  { 
                            
                            result.findFirst();
                            boolean found =  false;
                            
                            while (! result.last() )  {
                                if ( result.retrieve() == j) {
                                    found = true;
                                    break;
                                }
                                result.findNext(); // it will move in the list until it found the word
                            }
                            if ( result.retrieve() == j) {
                                found = true;
                                break;
                            }
                            
                            if (! found) // if does not exist we added to result linked list
                                result.insert(j);
                        } 
                    }
                }
            }
            return result;
        }


        public void DiaplayDoc() {
        
            if (this.invertedindex.empty())
                System.out.println("The Inverted Index is empty!");
            else {
                this.invertedindex.findFirst();
                while ( ! this.invertedindex.last())  {
                    System.out.println(invertedindex.retrieve());
                    this.invertedindex.findNext();
                }
                System.out.println(invertedindex.retrieve()); // print the last one
            }
        }
        
        
        public void TF(String str) {
            str = str.toLowerCase().trim(); // store it in small letter and removing the spaces 

            String [] words = str.split(" "); // We split the words from each other with a space

            Freqs = new frequency[50]; // from 0 to 49

            for ( int i = 0 ; i < 50 ; i++ )  { // Initialize

                Freqs[i] = new frequency();
                Freqs[i].docID = i;
                Freqs[i].Frequency = 0;
                Freqs[i].msg = "Document " + i + " : ";
            }
            
            for ( int i = 0 ; i < words.length ; i++) {
            
                if (found (words[i])) { // search about word
                
                    boolean [] docs = invertedindex.retrieve().getDocs(); // Does it exist in that document or not
                    int [] rank = invertedindex.retrieve().getRanked(); // Represents the number of times a word occurs in the document

                    
                    for ( int j = 0 ; j < docs.length ; j ++) {
                        if (docs[j] == true) {
                            int Docs = j;
                            Freqs[Docs].docID = Docs;
                            Freqs[Docs].Frequency += rank[j];
                            Freqs[Docs].msg +=" ( " + words[i] + ", " + rank[j] + " ) +"; 
                        }
                    } // iner for loop
                }
            } // outer for loop
            
            for ( int i = 0 ; i < Freqs.length ; i ++)  {
                Freqs[i].msg = Freqs[i].msg.substring(0, Freqs[i].msg.length()-1); // removing the "+" in the end
                Freqs[i].msg += " = " + Freqs[i].Frequency;
            }
            
            mergesort(Freqs, 0, Freqs.length-1 ); // sort the data from bigger to smaller
            
            System.out.println("Results: ");
            
            for ( int i = 0 ;  Freqs[i].Frequency != 0 ; i++)
                System.out.println(Freqs[i].msg);
            
            System.out.println("\nDocIDt\tScore");
            for ( int i = 0 ;  Freqs[i].Frequency != 0 ; i++)
                System.out.println(Freqs[i].docID + "\t\t" + Freqs[i].Frequency);
        }


    //This method must be O(nlog(n))!
    public static void mergesort ( frequency [] freqArray , int left , int right ) {
        if ( left >= right )
            return;
            
         int center = (right + left) / 2;
        mergesort (freqArray , left , center ) ;      // Sort first half
        mergesort (freqArray , center+1 , right ) ;  // Sort second half
        merge (freqArray , left , center , right ) ;        // Merge

    }

    private static void merge ( frequency [] freqArray , int left , int center , int right ) {
        frequency [] Array = new frequency [ right - left + 1];
        int i = left , j = center + 1 , k = 0;
    
        while ( i <= center && j <= right ) {
            if ( freqArray[ i ].Frequency >= freqArray [ j ].Frequency)
                Array [ k ++] = freqArray [ i ++];
            else
                Array [ k ++] = freqArray [ j ++];
        }
        
        if ( i > center )
            while ( j <= right )
                Array [ k ++] = freqArray [ j ++];
        else
            while ( i <= center )
                Array [ k ++] =  freqArray[ i ++];
        
        for ( k = 0; k < Array.length ; k ++)
            freqArray [ k + left ] = Array [ k ];
    }
        
        
        
}