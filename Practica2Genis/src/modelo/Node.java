package modelo;

public class Node<E> {
	E data;
	Node<E> next;
	Node<E> previous;
	
	Node() {
	}

	Node(Node<E> next, Node<E> previous, E data) {
		this.next = next;
		this.previous = previous;
		this.data = data;
	}

	public String toString() {
		return data + "";
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}
}