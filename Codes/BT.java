public interface BT <K extends Comparable<K>, T>{
        public boolean empty();
        public int size();
        public void clear();
        public T retrieve();
        public void update(T e);
        public boolean find(K key);
        public boolean insert(K key, T data) ;
        public boolean remove(K key);
        public LinkedList <T> getData();
        public LinkedList <K> getKeys();
        public void Traverse();
        public void TraverseT();

}