package modelo;

public interface LlistaInterface<E> {
	
	void inserir (E data);
	
	void inserirPos (int posicio, E data);
	
	E obtenir (int posicio);
	
	int longitud();
	
	void esborrar (int posicio);
	
	int buscar (E data) throws Exception;
}
