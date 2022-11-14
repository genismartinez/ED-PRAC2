package modelo;

import java.util.Iterator;

import excepcion.ElementNotFoundException;
import excepcion.IndexException;

public class Llista<E> implements LlistaInterface<E>, Iterable<E>{

	public Node<E> inici;
	
	public Llista() {
		inici = new Node<E>();	
	}

	@Override
	public void inserir(E e) {
		Node<E> fin = getNode(longitud() - 1);
		Node<E> nodeIns = new Node<E>(null, fin, e);
		
		//Si la lista esta vacia, insertamos en la cabecera
		if (inici.data == null) {
			inici = nodeIns;
		}
		else {
			//Asignamos el elemento como siguiente del ultimo si la lista no está vacia
			fin.next = nodeIns;	
		}		
	}

	@Override
	public void inserirPos(int posicio, Object data) {
		if (posicio >= longitud() || posicio < 0) {
			throw new IndexException(posicio);
		}

		Node<E> nodeIns = null;

		if (data != null) {
			Node<E> node = inici;

			if (node.next != null) {
				for (int i = 0; i < posicio; i++) {
					node = node.next;
				}
			}
			
			nodeIns = new Node(node.next, node, data);
			
			//Insertamos en el primero
			if (posicio == 0) {
				nodeIns.next = inici;
				inici = nodeIns;
			}
			else {
				if (node.next != null) {
					node.next.previous = nodeIns;
				}
			}
			node.next = nodeIns;
		}
		// return nodeIns;
		
	}

	@Override
	public E obtenir(int posicio) {
		
		if (posicio >= longitud() || posicio < 0) {
			throw new IndexException(posicio);
		}
		
		E element = null;
		Node<E> node = inici.next;
		
		//Desplazarme hasta el nodo que quiero obtener
		if (node.next != null) {
			for (int i = 0; i <= posicio; i++) {
				element = (E) node.getData();
				node = node.next;
			}
		}
		
		return element;
	}

	@Override
	public int longitud() {
		int count = 0;
		Node<E> node = inici.next;
		while (node != null) {
			++count;
			node = node.next;
		}
		return count;
	}

	@Override
	public void esborrar(int posicio) {
		
		if (posicio >= longitud() || posicio < 0) {
			throw new IndexException(posicio);
		}
		
		Node<E> nodeAct = inici;
		Node<E> nodoAnt = null;
		
		//Desplazarme hasta el nodo que quiero borrar
		if (nodeAct.next != null) {
			for (int i = 0; i <= posicio; i++) {
				nodoAnt =  nodeAct;
				nodeAct = nodeAct.next;
			}
		}
		
		//Hemos llegado a la posicion
		nodoAnt.next = nodeAct.next;
	}

	@Override
	public int buscar(E data) throws Exception {
		
		Node<E> node = inici;
		boolean trobat = false;
		int cost = 0;
		
		while (node != null && !trobat) {
			
			//Comparamos objetos de tipo Cuidata que hay en el nodo y en el T data
			//usando el compareTo definido en esa clase.
			if ((node.getData()).equals(data)) {
				trobat = true;
			}
			
			node = node.next; //Importante desplazar el puntero al siguiente
			cost++;
		}
		
		//si el coste es mayor o igual que la longitud de la lista
		/*if (cost >= longitud()) {
			throw new ElementNotFoundException(cost);
		}*/
		
		return cost;
	}
	
	private Node<E> getNode(int index) {
		if (index > longitud())
			throw new IndexException();
		Node<E> node = inici;
		for (int i = 0; i <= index; ++i) {
			node = node.next;
		}
		return node;
	}

	@Override
	public Iterator<E> iterator() {
		 
		return new Iterator<E>() {

			Node<E> actual = inici;
			
			@Override
			public boolean hasNext() {
				return actual != null;
			}

			@Override
			public E next() {
				
				if (hasNext()) {
	                 E data = (E) actual.data;
	                 actual = actual.next;
	                 return data;
	            }
				
	            return null;
			}
		};
	}
}
