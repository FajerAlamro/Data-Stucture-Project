public class BST<K extends Comparable< K >, T>{

// comparable is predefined interface in java that has compareTo function
   class BSTNode<K extends Comparable< K >, T> {
   
        public K key;
        public T data;
        public BSTNode<K,T> left, right;

        // Creates a new instance of BSTNode
        public BSTNode(K Key, T value) {
                key = Key;
                data = value;
                left = right = null;
        }
        

        public BSTNode(K k, T value, BSTNode<K,T> Left, BSTNode<K,T> Right) {
                key = k;
                data = value;
                left = Left;
                right = Right;
        }
    }
    
    // BST Class 
        BSTNode<K, T> root; 
        BSTNode<K, T > current;
        int count ;
                
        public BST() {
            root = current = null;
            count = 0;
        }
        
        // Returns the number of elements 
           public int size() {
            return count;
        }

        // Return true if the tree is empty
            public boolean empty() {
            return root == null;
        }

        // Removes all elements 
            public void clear() {
            root = current = null;
            count = 0;
        }

        // Return the key and data of the current element
        public T retrieve() {
            T data =null;
            if (current != null)
                data = current.data;
            return data;
        }

        // Update the data of current element
        public void update(T value) {
            if (current != null)
                current.data = value; // key will not change!
        }


  // Search for element 
 // This method must be O(log(n))!
        public boolean find(K key) {
            BSTNode<K,T> Indicators = root;
            
         // If the element does not exist the current is unchanged and false is returned
            if(empty())
                    return false;

            while(Indicators != null) {
                    if(Indicators.key.compareTo(key) == 0) {
                            current = Indicators;
                            return true;
                    }
                    else if(key.compareTo(Indicators.key) < 0)
                            Indicators = Indicators.left;
                    else
                            Indicators = Indicators.right;
            }
            return false;
        }


        // Insert a new element if does not exist 
        // This method must be O(log(n))!
        public boolean insert(K key, T data) {

            if(empty()) {
                current = root = new BSTNode <K, T> ( key, data);
                count ++;
                return true;
            }
            
            BSTNode<K,T> parent = null;
            BSTNode<K,T> child  = root;
            
            while(child != null) {
                    if(child.key.compareTo(key) == 0) {
                            return false;
                    }
                    else if(key.compareTo(child.key) < 0) {
                        parent = child;
                        child = child.left;
                    }
                    else {
                        parent = child;
                        child = child.right;
                    }
            }
           
            if(key.compareTo(parent.key) < 0) {
                parent.left = new BSTNode <K, T> ( key, data);
                current = parent.left;
            }
            
            else {
                parent.right = new BSTNode <K, T> ( key, data);
                current = parent.right;
            }
            count ++;
            return true;
        }


        // Remove the element with key k if it exists, if it does not exist return false
        //This method must be O(log(n))!
        public boolean remove(K key) {
            Boolean removed = false;
            BSTNode<K,T> Indicators;
            
            Indicators = Remove(key, root, removed); // calling method
            root = Indicators;
            
            if (current.key.compareTo(key) == 0)
                current = root;
            if (removed)
                count -- ;
            
            return removed;
        }
    
    
        private BSTNode<K,T> Remove(K key, BSTNode<K,T> Indicators, boolean flag)  {
        
            BSTNode<K,T> temp , child = null;
            
            if(Indicators == null)
                    return null;

            if(key.compareTo(Indicators.key ) < 0)
                    Indicators.left = Remove(key, Indicators.left, flag); //recursion (left side)
            else if(key.compareTo(Indicators.key) > 0)
                    Indicators.right = Remove(key, Indicators.right, flag); //recursion (right side)
            else {
                    
                    flag = true;
                    if (Indicators.left != null && Indicators.right != null) { //It has two children
                            temp = findLeft(Indicators.right);
                            Indicators.key = temp.key;
                            Indicators.data = temp.data;
                            Indicators.right = Remove(temp.key, Indicators.right, flag);
                    }
                    else 
                    {
                            if (Indicators.right == null) //It has one child
                                    child = Indicators.left;
                            else if (Indicators.left == null) //It has one child
                                    child = Indicators.right;
                            return child;
                    }
                }
            return Indicators;
        }
        
        
        private BSTNode<K,T> findLeft(BSTNode<K,T> Indicators) { // left mean min 
            if(Indicators == null)
                    return null;

            while(Indicators != null){
                    Indicators = Indicators.left; // min is always to left
            }
            return Indicators;
        }
        
        
        
        public void InOrder()  {
            if (root != null)
                InOrderTree(root);
        }
        
        // To print the BST data
        private void InOrderTree (BSTNode<K,T> node  ) {
            if (node == null)
                return;
                
            InOrderTree( node.left);
            System.out.println(node.data);
            InOrderTree( node.right);
            
        }
        
        

        public LinkedList <T> getData() {
            LinkedList <T> data = new LinkedList <T>();
            if (root != null)
                BSTData(root, data);
            return data;
        }
        
        
        private void BSTData (BSTNode<K,T> node , LinkedList <T> data ) {
            if (node == null)
                return;
            
            // recursively in order
            BSTData( node.left ,data );
            data.insert(node.data); // insert the new node into BST
            BSTData( node.right, data);
        }
       


        public LinkedList <K> getKeys() {
            LinkedList <K> All_Keys = new LinkedList <K>();
            if (root != null)
                getAllKeys(root, All_Keys);
                
            return All_Keys;
        }
        
        // it returns the keys
        private void getAllKeys (BSTNode<K,T> node , LinkedList <K> keys )  {
            if (node == null)
                return;
                
            // recursively in order
            getAllKeys( node.left ,keys );
            keys.insert(node.key); // insert the givin key
            getAllKeys( node.right, keys);
        }
        
        public void DisplayDataBST()  {
            // if BST is not empty
            if (root != null)
                DisplayDataBSTRec(root);
        }
        
        // print the data for BST
        private void DisplayDataBSTRec (BSTNode<K,T> node) {
            // if the node not empty
            if (node == null)
                return;
                
            // display the info in order (LNR)    
            DisplayDataBSTRec( node.left );
           
           System.out.print(node.key);
           
            if (node.data instanceof DocumentBST ) {
            
                System.out.print("   docs: ");
                ((DocumentBST) node.data).getDocs().print(); // down casting
            }
                
            
            DisplayDataBSTRec( node.right);
            
        }

        
     
}