package fu.alp2.strassenverwaltung.list;

// FIFO Queue
public class MyList<T> {

	private MyNode<T> last;
	private MyNode<T> first;
	private int size;
	
	public MyList() {
		super();
		this.last = null;
		this.first = null;
		size=0;
	}
	
	public boolean isEmpty(){
		if(size>0){
			return false;
		}
		return true;
	}
	// put new node == last
	public void put(T value){
		// create new node
		MyNode<T> newNode=new MyNode<T>(value,last,null);
		// add the new as last.next
		if(last!=null){
		last.setNext(newNode);
		}
		if(first==null){
			first=newNode;
		}
		// set new Node as the last one
		last=newNode;
		size++;
	}
	
	// pop the first node
	public T pop(){
		// chache old first
		MyNode<T> pop=first;
		// null check
		if(first!=null && first.getNext()!=null){
			first=first.getNext();
		}else if(first.getNext()==null){
			// last== first
			last=null;
		}

		// delete reference to first 
		first.setPrev(null);
		size--;
		// return
		return pop.getValue();
	}
	
	public boolean contains(T value){
		MyNode<T> current=first;
		while(current.getNext()!=null){
			if(current.getValue().equals(value)){
				return true;
			}
			current=current.getNext();
		}
		return current.getValue().equals(value);
	}
	
	public T getValue(int id){
		if(id<size){
		MyNode<T> current=first;
		while(id!=0){
			current=current.getNext();
			id--;
		}
		return current.getValue();
	}
		return null;
		}
}
