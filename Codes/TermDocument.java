public class TermDocument {

    String word;
    // Array that indicate if Document exist or not
    boolean [] docIDS ;
    // Represents the number of times a word occurs in the document
    int [] ranked;

    public TermDocument() {
        word = "";
        docIDS = new boolean [50]; // From 0 to 50
        ranked = new int [50]; // From 0 to 50
        
        for (int i = 0 ; i < docIDS.length ; i++)
        {
        // The defaults values for them :
            docIDS [i] = false;
            ranked[i] = 0;
        }
    }

    public TermDocument(String word, int [] rank) {
        this.word = word;
        docIDS = new boolean [50];
        ranked = new int [50];
    }
    
    // Method to add the doc into Document array and increment the rank :
    public void add_docID ( int docID) {
        docIDS[docID] = true;
        ranked[docID] ++;
    }

    // setter
    public void setWord(String word) {
        this. word = word; 
    }
    // getter
    public String getWord() {
         return word;
    }
    
    // Retrieve the Document array :
    public boolean [] getDocs() {
        boolean [] Array = new boolean [ranked.length];
        
        for ( int i = 0 ; i < Array.length ; i++)
            Array[i] = docIDS[i];
        
        return Array;
    }

    // Retrieve the Ranked array :
    public int [] getRanked() {
        int[] Array = new int [ranked.length];
        
        for ( int i = 0 ; i < Array.length ; i++)
            Array[i] = ranked[i];
            
        return Array;
    }
    
   @Override
    public String toString() {
        String document = "";
        
        for (int i = 0, j = 0 ; i < docIDS.length; i++)
            if ( docIDS[i] != false)
            {
                if ( j == 0)
                {
                    document += " " + String.valueOf(i) ;
                    j++;
                }
                else
                {
                    document += ", " + String.valueOf(i) ;
                    j++;
                }
            }
        return word + "[" + document + ']';
    }
    
}