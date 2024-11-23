public class AVLTree<K extends Comparable<K>, T>{

// comparable is predefined interface in java that has compareTo function
   class AVLNode<K extends Comparable<K>, T> {
                public K key;
                public T data;
                AVLNode<K,T> parent;  // pointer to the parent
                AVLNode<K,T> left;  // pointer to left child
                AVLNode<K,T> right;  // pointer to right child
                int balance;  // balance of the node


                    public AVLNode() {
                            this.key = null;  
                            this.data = null;
                            this.parent = this.left = this.right = null;
                            this.balance = 0;
                    }


                    public AVLNode(K key, T data) {
                            this.key = key  ;  
                            this.data = data;
                            this.parent = this.left = this.right = null;
                            this.balance = 0;
                    }

                    public AVLNode(K key, T data, AVLNode<K,T> par, AVLNode<K,T> Left, AVLNode<K,T> Right){
                            this.key = key  ;  
                            this.data = data;
                            left = Left;
                            right = Right;
                            parent = par;
                            balance =0;
                    }

                    // get the left nide
                    public AVLNode<K,T> getLeft() {
                        return left;
                    }

                    // get the right nide
                    public AVLNode<K,T> getRight() {
                        return right;
                    }

                    // get the data
                    public T getData() {
                        return data;
                    }
                    
                    @Override
                    public String toString() {
                        return "The AVL Node is :" + "Key =" + key + ", Data = " + data;
                    }
            }


        private AVLNode<K,T> root;
        private AVLNode<K,T>  current;
        private int count;
        
        public AVLTree() {
                root = current = null;
                count = 0;
        }

        public boolean empty() {
            return root == null;
        }

        public int size() {
            return count;
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


        // Update the data of current
        public void update(T value) {
            if (current != null)
                current.data = value;
        }


        //returns the data for the key in the AVL
        private T searchAVL(AVLNode<K,T> node, K key) {
                    if (node == null)
                        return null;
                        
                    else if (node.key.compareTo(key) ==0) {
                        current = node;
                        return node.data;
                    } 
                    else if (node.key.compareTo(key) > 0)
                         return searchAVL(node.left, key); //recursion (left side)
                    else
                         return searchAVL(node.right, key); //recursion (right side)
        }
        
        
        
        // update the balance
        private void updateBalance(AVLNode<K,T> node) {
                if (node.balance < -1 || node.balance > 1) {
                        editBalance(node);
                        return;
                }

                if (node.parent != null) {
                        if (node == node.parent.left) {
                                node.parent.balance -= 1;
                        } 

                        if (node == node.parent.right) {
                                node.parent.balance += 1;
                        }

                        if (node.parent.balance != 0) {
                                updateBalance(node.parent);
                        }
                }
        }

        // Re balance the tree
        void editBalance(AVLNode<K,T> node) {
                if (node.balance > 0) {
                        if (node.right.balance < 0) {
                                ToRight(node.right);
                                ToLeft(node);
                        } else {
                                ToLeft(node);
                        }
                } else if (node.balance < 0) {
                        if (node.left.balance > 0) {
                                ToLeft(node.left);
                                ToRight(node);
                        } else {
                                ToRight(node);
                        }
                }
        }

        
       // Search for element 
        public boolean find(K key) {
                T data = searchAVL(this.root, key);
                if ( data != null)
                    return true;
                return false;
        }


        // rotate left at node 
        void ToLeft(AVLNode<K,T> First_value) {
            AVLNode<K,T> Second_value = First_value.right;
            First_value.right = Second_value.left;
            if (Second_value.left != null) {
                    Second_value.left.parent = First_value;
            }
            
            Second_value.parent = First_value.parent;
            if (First_value.parent == null) {
                    this.root = Second_value;
            } else if (First_value == First_value.parent.left) {
                    First_value.parent.left = Second_value;
            } else {
                    First_value.parent.right = Second_value;
            }
            Second_value.left = First_value;
            First_value.parent = Second_value;

            // update the balance 
            First_value.balance = First_value.balance - 1 - Math.max(0, Second_value.balance);
            Second_value.balance = Second_value.balance - 1 + Math.min(0, First_value.balance);
        }

        // rotate right at node 
        void ToRight(AVLNode<K,T> First_value) {
                AVLNode<K,T> Second_value = First_value.left;
                
                First_value.left = Second_value.right;
                if (Second_value.right != null) {
                
                        Second_value.right.parent = First_value;
                }
                Second_value.parent = First_value.parent;
                if (First_value.parent == null) {
                        this.root = Second_value;
                } else if (First_value == First_value.parent.right) {
                        First_value.parent.right = Second_value;
                } else {
                        First_value.parent.left = Second_value;
                }
                Second_value.right = First_value;
                First_value.parent = Second_value;

                // update the balance 
                First_value.balance = First_value.balance + 1 - Math.min(0, Second_value.balance);
                Second_value.balance = Second_value.balance + 1 + Math.max(0, First_value.balance);
        }

        
        
        public boolean insert(K key, T data) {
            AVLNode<K,T> node = new AVLNode<K,T>(key, data);

            AVLNode<K,T> Indicators = null;
            AVLNode<K,T> current = this.root;

            while (current != null) {
                    Indicators = current;
                    if (node.key.compareTo(current.key) == 0) { // It is already exist
                            return false;
                    }else if (node.key.compareTo(current.key) < 0 ) { // insert at left
                            current = current.left;
                    } else {
                            current = current.right; // insert at right

                    }
            } // end while
            
            
            // Indicators  is parent 
              node.parent = Indicators;
            if (Indicators == null) { // empty
                    root = node;
                    current = node;
            } else if (node.key.compareTo(Indicators.key) < 0 ) {
                    Indicators.left = node;
            } else {
                    Indicators.right = node;
            }
            count ++;

            //  re balance the node if necessary
            updateBalance(node);
            return true;        
        }
        
        
       public void InOrder()
        {
            if (root != null)
                InOrderTree(root);
        }
        
        // To print the AVL data
        private void InOrderTree (AVLNode<K,T> node  )
        {
            if (node == null)
                return;
            InOrderTree( node.left);
            System.out.println(node.data);
            InOrderTree( node.right);
            
        }


       public void InsideAVL()
        {
            if (root != null)
                PrintInsideAVL(root);
        }
        
        // When I want to print the data for AVL that its inside another AVL 
        private void PrintInsideAVL (AVLNode<K,T> node)
        {
            if (node == null)
                return;
                
            PrintInsideAVL( node.left );
            if (node.getData() instanceof AVLTree ) { // to make sure does the data for AVL node has another AVL?
            
                System.out.println( "The key "+ node.key);
                ((AVLTree <String, TermDocument >) node.getData()).InOrder();
            }
            else
                System.out.println(node.data);
            
            PrintInsideAVL( node.right);
            
        }


        public LinkedList <T> getData() {
            LinkedList <T> data = new LinkedList <T>();
            if (root != null)
                AvlgetData(root, data);
            return data;
        }
        
        
        private void AvlgetData (AVLNode<K,T> node , LinkedList <T> data ) {
            if (node == null)
                return;
                
           // recursively in order
            AvlgetData( node.left ,data );
            data.insert(node.data); // insert the new node into AVL
            AvlgetData( node.right, data);
        }
       
       

        public LinkedList <K> getKeys() {
            LinkedList <K> All_Keys = new LinkedList <K>();
            if (root != null)
                getAllKeys(root, All_Keys);
                
            return All_Keys;
        }
        
        // it returns the keys
        private void getAllKeys (AVLNode<K,T> node , LinkedList <K> keys ) {
            if (node == null)
                return;
                
            // recursively in order
            getAllKeys( node.left ,keys );
            keys.insert(node.key); // insert the givin key
            getAllKeys( node.right, keys);
        }
        
        
        public void DisplayDataAVL()  {
            // if AVL is not empty
            if (root != null)
                DisplayDataAVLRec(root);
        }
        
        // print the data for AVL
        private void DisplayDataAVLRec (AVLNode<K,T> node)  {
            // if the node not empty
            if (node == null)
                return;
                
            // display the info in order (LNR) 
            DisplayDataAVLRec( node.left );
           
           System.out.print(node.key);
            if (node.getData() instanceof DocumentAVL ) {
            
                System.out.print("   docs: ");
                ((DocumentAVL) node.data).getDocs().print(); // down casting
            }
                
            
            DisplayDataAVLRec( node.right);
            
        }
        
  }