package fu.alp2.strassenverwaltung.list;

public class MyNode<T> {
	// next Node
	private MyNode<T> next;
	// privious Node
	private MyNode<T> prev;
	
	private T value;
	
	public MyNode(T value, MyNode<T> prev, MyNode<T> next){
		this.value=value;
		this.prev=prev;
		this.next=next;
	}

	public MyNode<T> getNext() {
		return next;
	}

	public void setNext(MyNode<T> next) {
		this.next = next;
	}

	public MyNode<T> getPrev() {
		return prev;
	}

	public void setPrev(MyNode<T> prev) {
		this.prev = prev;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	
}
