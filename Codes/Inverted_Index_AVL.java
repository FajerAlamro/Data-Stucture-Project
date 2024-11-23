public class Inverted_Index_AVL {
        class frequency {
            int docID = 0;
            int Frequency = 0;
            String msg ="";
        }
 
        // attributes for Inverted_Index_AVL class
        AVLTree <String, DocumentAVL> invertedindexAVL; 
        frequency [] Freqs;
        
        public Inverted_Index_AVL() {
            invertedindexAVL = new AVLTree <String, DocumentAVL>();
            Freqs = new frequency[50];
        }

        public int size() {
           return invertedindexAVL.size();
        }


        public boolean addNew (int docID, String word) {
            // if its empty, we initialize a new node 
           if (invertedindexAVL.empty()) {
               DocumentAVL DocAVL = new DocumentAVL ();
               DocAVL.setWord(word);
               DocAVL.add_docID(docID);
               invertedindexAVL.insert(word, DocAVL);
               return true;
           }
           else {
                if (invertedindexAVL.find(word)) {
                   // if its found, we update the data with the given inputs
                    DocumentAVL DocAVL = invertedindexAVL.retrieve();
                    DocAVL.add_docID(docID);
                    invertedindexAVL.update(DocAVL);
                    
                    return false;

                }
                
               // if it was not found, we insert it
               DocumentAVL DocAVL = new DocumentAVL ();
               DocAVL.setWord(word);
               DocAVL.add_docID(docID);
               invertedindexAVL.insert(word, DocAVL);
               
                return true;
            }
        }

         // searching for the word in the AVL
        public boolean found(String word) {
               return invertedindexAVL.find(word);
        }
        
        // print the information for AVL
        public void printDocument() {
            invertedindexAVL.InOrder();
        }


        public LinkedList<Integer> AND_OR_Function (String str ) {
        
            if (! str.contains(" OR ") && ! str.contains(" AND ")) { // if there are both not exist
                str = str.toLowerCase().trim();
                
                LinkedList<Integer> result = new LinkedList<Integer>();
                
                if (this.found (str))
                    result = invertedindexAVL.retrieve().merge.getKeys();// Check if the word exists; if found, retrieve and return the list of documents containing it

                return result;
            }
            
            else if (str.contains(" OR ") && str.contains(" AND ")) { // if its contain both of them 
         
                String [] AND_ORs = str.split(" OR "); // less prioroty than "AND"
                LinkedList<Integer> result = AND_Function (AND_ORs[0]);
               
                for ( int i = 1 ; i < AND_ORs.length ; i++  ) {   
                    LinkedList<Integer> result2 =AND_Function (AND_ORs[i]);

                    result2.findFirst();
                    for ( int j = 0 ; j < result2.size() ; j++) {
                        boolean found = false;
                        result.findFirst();
                        
                        while ( ! result.last()) {
                            if (result.retrieve()== result2.retrieve()) {
                                found = true;
                                break;
                            }
                            
                            result.findNext();
                        }
                        if (result.retrieve() == result2.retrieve())
                            found = true;
                        
                        if (!found ) // if does not exist we added to result linked list
                            result.insert(result2.retrieve());
 
                        result2.findNext();
                    }
                }
                return result;
            }
            
            else  if (str.contains(" AND "))  // if its contain only "AND"
                return AND_Function (str);
            
            return OR_Function (str); // if its contain only "OR"
        }
        
        
        public LinkedList<Integer> AND_Function (String str) {
           String [] ANDs = str.split(" AND "); // it will remove the "AND" of them
 
            LinkedList<Integer> result = new LinkedList<Integer>();
            
            if (this.found (ANDs[0].toLowerCase().trim()))
                result = invertedindexAVL.retrieve().merge.getKeys(); // Contains the document IDs in which the word appeared

            
            for ( int i = 0 ; i< ANDs.length ; i++) { // Moving on to the other words

                LinkedList<Integer> b1 = result;
                result = new LinkedList<Integer> ();

                if (this.found (ANDs[i].toLowerCase().trim())) { //Check whether the next word exists or not
                                   
                     // Retrieve a sorted list of document IDs from the inverted index and store it in a LinkedList
                    LinkedList<Integer> docs = invertedindexAVL.retrieve().merge.getKeys();
                    
                    docs.findFirst();
                    for ( int j = 0 ; j < docs.size ; j++) { // It will go through all the documents
                      
                        b1.findFirst();
                        boolean found =  false;
                        while ( ! b1.last()) {
                        
                            if ( b1.retrieve()==docs.retrieve()) {
                                found = true;
                                break;
                            }
                            
                            b1.findNext(); // it will move in the list until it found the word
                        }
                        
                        if ( b1.retrieve()== docs.retrieve()) // for last node
                            found = true;

                        if (found)
                            result.insert(docs.retrieve());// if its exist, it will be added to the result linked list

                        docs.findNext();
                    }
                }
            }
            return result;
    }

        public LinkedList<Integer> OR_Function (String str) {
        
            String [] ORs = str.split(" OR "); // it will remove the "Or" of them

            LinkedList<Integer> result =  new LinkedList<Integer> ();
            
            if (this.found (ORs[0].toLowerCase().trim()))
                result = invertedindexAVL.retrieve().merge.getKeys();// Contains the document IDs in which the word appeared
            
            for ( int i = 1 ; i< ORs.length ; i++) { // Moving on to the other words
            
                if (this.found (ORs[i].toLowerCase().trim())) {
                    LinkedList<Integer> docs = invertedindexAVL.retrieve().merge.getKeys();
                    docs.findFirst();
                    
                    for ( int j = 0 ; j < docs.size ; j++) { // It will go through all the documents    
                            result.findFirst();
                            boolean found =  false;
                            
                            while (! result.last())  {
                            
                                if ( result.retrieve()== docs.retrieve()) { 
                                    found = true;
                                    break;
                                }
                                result.findNext();
                            }
                            
                            if ( result.retrieve() == docs.retrieve())
                                found = true;

                            if (! found)
                                result.insert(j);// if does not exist we added to result linked list

                            docs.findNext();// complete the remaining documents
                    } 
                 }
              }
            return result;
        }


        public void TF(String str){
        
            str = str.toLowerCase().trim(); // store it in small letter and removing the spaces
            
            String [] words = str.split(" "); // We split the words from each other with a space
            
            Freqs = new frequency[50];  // from 0 to 49
            
            for ( int i = 0 ; i < 50 ; i++ ) { // Initialize
            
                Freqs[i] = new frequency() ;
                Freqs[i].docID = i;
                Freqs[i].Frequency = 0;
                Freqs[i].msg = "Document " + i + " : ";
            }
            
            
            for ( int i = 0 ; i < words.length ; i++) { //It will go through all the word
            
                if (invertedindexAVL.find(words[i])) { // search about word
                
                    LinkedList<Integer> docs = invertedindexAVL.retrieve().getDocs(); // The document that word appear in them
                    LinkedList<Integer> rank = invertedindexAVL.retrieve().getRanked();// Frequency for each word
                    
                    docs.findFirst();
                    rank.findFirst();
                    
                    for ( int j = 0 ; j < docs.size() ; j ++){
                    
                        int value = docs.retrieve();
                        Freqs[value].docID = value;
                        Freqs[value].Frequency += rank.retrieve();
                        Freqs[value].msg +=" ( " + words[i] + ", " + rank.retrieve() + " ) +"; 
                        docs.findNext();
                        rank.findNext();
                    } // iner for loop
                }
            } // outer for loop
            
            
            for ( int i = 0 ; i < Freqs.length ; i ++)  {
            
                Freqs[i].msg = Freqs[i].msg.substring(0, Freqs[i].msg.length()-1);// removing the "+" in the end
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
    public static void mergesort ( frequency [] freqArray , int left , int right )  {
        if ( left >= right )
            return;
            
        int center = ( left + right ) / 2;
        mergesort (freqArray , left , center ) ; // Sort first half
        mergesort (freqArray , center + 1 , right ) ; // Sort second half
        merge (freqArray , left , center , right ) ;  // Merge
    }
    

        private static void merge ( frequency [] freqArray , int left , int center , int right )  {
        frequency [] Array = new frequency [ right - left + 1];
        int i = left , j = center + 1 , k = 0;
    
        while ( i <= center && j <= right ) {
        
            if ( freqArray [ i ].Frequency >= freqArray [ j ].Frequency)
                Array [ k ++] = freqArray [ i ++];
            else
                Array [ k ++] = freqArray [ j ++];
        }
        
        if ( i > center )
            while ( j <= right )
                Array [ k ++] = freqArray [ j ++];
        else
            while ( i <= center )
                Array [ k ++] = freqArray [ i ++];
        
        for ( k = 0; k < Array. length ; k ++)
            freqArray [ k + left ] = Array [ k ];
    }
        
}