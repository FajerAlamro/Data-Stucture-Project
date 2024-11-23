public class DocumentBST {
    String word;
    
// Its store both Document ID and Ranked : 
    BST <Integer, Integer> merge ;
    
    public DocumentBST() {
        word = "";
        merge = new BST <Integer, Integer> ();
    }

    public DocumentBST(String word) {
        this.word = word;
        merge = new BST <Integer, Integer> (); // New BST
    }
    
    // If the document exists, it increases the rank. If it does not exist, it adds it.
    public void add_docID ( int docID) {
    // If its empty we add it :
        if (merge.empty())
            merge.insert(docID, 1);
        else
        {
        // if document exist, it will increases the rank :
            if (merge.find(docID)) {
                int ranked = merge.retrieve();
                ranked++;
                merge.update(ranked);
            }   
            else
            // Its new, so we add it :
                merge.insert(docID, 1);
        }
    }
    
    // setter
    public void setWord(String word) {
        this. word = word; 
    }
    
    // getter
    public String getWord() {
         return word;
    }
    
    // Method from BTS, it will return all keys in order :
    public LinkedList<Integer> getDocs () {
        return merge.getKeys();
    }

    // Method from BTS, it will return all data in order :
    public LinkedList<Integer> getRanked() {
        return this.merge.getData();
    }
    
   @Override
    public String toString() {
        String document = "";
        LinkedList<Integer> All_ID = getDocs ();
        All_ID.findFirst();
        for (int i = 0, j = 0 ; i < All_ID.size(); i++)
        {
                if ( i == 0)
                    document += " " + String.valueOf(i) ;
                else
                    document += ", " + String.valueOf(i) ;
                    All_ID.findNext();
        }
        return word + "[" + document + ']';
    }
    
}