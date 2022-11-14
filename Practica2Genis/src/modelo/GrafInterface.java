package modelo;

public interface GrafInterface<V, E> {
	
	void CrearGraf();
	
	void afegirAresta(V v1, V v2, E e);
	
	boolean existeixAresta(V v1, V v2);

	E valorAresta(V v1, V v2);
	
	Llista<V> adjacents(V v);
}
