public class LinkedList<T>{
    
            // class Node
            class Node<T> {
                public T data;
                public Node<T> next;
                public Node () {
                    data = null;
                    next = null;
                }
                
                public Node (T value) {
                    data = value;
                    next = null;
                }
                
                // Getter
                 public T getData() {
                 return data;
                }
                // Setter
                public void setData(T data) {
                    this.data = data;
                }

                public Node<T> getNext() {
                    return next;
                }

                public void setNext(Node<T> next) {
                    this.next = next;
                }

            }


    // class Linked List
    private Node<T> head;
    private Node<T> current;
    int size;
    
    public LinkedList () {
        head = current = null;
        size = 0 ;
    }
    public boolean empty () {
        return head == null;
    }
    public int size ()
    {
        return size;
    }
    
    public boolean last () {
        return current.next == null;
    }
    public boolean full () {
            return false; // Linked List will never be full !
    }
    public void findFirst () {
            current = head;
    }
    public void findNext () {
            current = current.next;
    }
    public T retrieve () {
            return current.data;
    }
    // update current node to a given data 
    public void update (T value) {
            current.data = value;
    }
    
    // insert new node to linked list :
    public void insert (T value) {
            Node<T> tmp;
            if (empty()) {
                    current = head = new Node<T> (value);
            }
            else {
            // insert new node after current
                    tmp = current.next;
                    current.next = new Node<T> (value);
                    current = current.next;
                    current.next = tmp;
            }
            size++ ;
    }
    
    // remove the current
    public void remove () {
  
            if (current == head) {
                    head = head.next;
            }
            else {
                    Node<T> tmp = head;

                  // it will stop before current
                    while (tmp.next != current)
                            tmp = tmp.next;

                    tmp.next = current.next;
            }

            if (current.next == null)
                    current = head;
            else
                    current = current.next;
            size--;
    }
    
    
    public void print() {
        if ( head == null)
            System.out.println("It is empty!");
        else
        {
            Node<T> tmp = head;
            while ( tmp != null)
            {
                System.out.print(tmp.data + "  ");
                tmp = tmp.next;
            }
            
        }
        System.out.println("");
    }
    
}
