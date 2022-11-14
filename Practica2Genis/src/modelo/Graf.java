package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import excepcion.ElementNotFoundException;

public class Graf<V, E> implements GrafInterface<V, E>{

	//Atributos
	private int numVert;
	private Map<V, Llista<E>> vertGraf;
	private EstacioRecarrega[][] matriuAdj;
	
	private final int DISTANCIA_MIN = 40;
	private final Double DISTANCIA_INI = 0.0;
	private final Double DISTANCIA_MAX = 999.0;
	private final int CONVERT_KM = 111;
	
	@Override
	public void CrearGraf() {
		
		numVert = 0;
		vertGraf = new HashMap<V, Llista<E>>();
		matriuAdj = null;	
	}

	@Override
	public void afegirAresta(V v1, V v2, E e) {				
		matriuAdj[((Vertex)v1).getPosicio()][((Vertex)v2).getPosicio()] = (EstacioRecarrega) e;
	}

	@Override
	public boolean existeixAresta(V v1, V v2) {
		return matriuAdj[(Integer)v1][(Integer)v2] != null;
	}

	@Override
	public E valorAresta(V v1, V v2) {
		return (E) matriuAdj[(Integer)v1][(Integer)v2]; 
	}

	@Override
	public Llista<V> adjacents(V v) {
		
		Llista<V> llista = new Llista<V>();
		
		for (int i = 0; i < matriuAdj.length; i++) {
			llista.inserir(v);
		}
		
		return llista;
	}

	public Map<V, Llista<E>> getVertGraf() {
		return vertGraf;
	}

	public void setNumVert(int numVert) {
		this.numVert = numVert;
	}

	public void setMatriuAdj() {
		this.matriuAdj =  new EstacioRecarrega[numVert][numVert];
	}
	
	private boolean estacioNoConnectada(ArrayList<EstacioRecarrega> llistaEstacions, int posicio) {
		boolean connectada = true;
		
		for (int j = 0; j < llistaEstacions.size() && connectada == true; j++){
			if (matriuAdj[posicio][j] != null) {
				connectada = false;
			}
		}
		
		return connectada;
	}
	
	private int estacioMasPropera (ArrayList<EstacioRecarrega> llistaEstacions, int posicio) {

		Punt coordI= new Punt(llistaEstacions.get(posicio).getLatitud(), llistaEstacions.get(posicio).getLongitud());
		Punt coordJ = new Punt(llistaEstacions.get(0).getLatitud(), llistaEstacions.get(0).getLongitud());
		double minDistancia = Punt.distancia(coordI, coordJ) * CONVERT_KM;
		int posicioMin = 0;
		
		for (int j = 0; j < llistaEstacions.size(); j++){
				
			if (llistaEstacions.get(posicio).getId() != llistaEstacions.get(j).getId()) {

				coordJ = new Punt(llistaEstacions.get(j).getLatitud(), llistaEstacions.get(j).getLongitud());
				double distancia = Punt.distancia(coordI, coordJ) * CONVERT_KM;
					
				if (distancia < minDistancia && distancia != 0) {
					minDistancia = distancia;
					posicioMin = j;
				}
			}
		}
		
		return posicioMin;
	}
			

	public <V, E> void construirGraf(ArrayList<EstacioRecarrega> llistaEstacions) {
		
		for (int i = 0; i < llistaEstacions.size(); i++){
			
			Punt coordI = new Punt(llistaEstacions.get(i).getLatitud(), llistaEstacions.get(i).getLongitud());
			
			for (int j = 0; j < llistaEstacions.size(); j++){
				
				if (llistaEstacions.get(i).getId() != llistaEstacions.get(j).getId()) {

					Punt coordJ = new Punt(llistaEstacions.get(j).getLatitud(), llistaEstacions.get(j).getLongitud());
					double distancia = Punt.distancia(coordI, coordJ) * CONVERT_KM;
					
					if (distancia < DISTANCIA_MIN) {
						matriuAdj[i][j] = llistaEstacions.get(j);
					}
				}	
			}
			
			if (estacioNoConnectada(llistaEstacions, i)) {
				int min = estacioMasPropera (llistaEstacions, i);
				matriuAdj[i][min] = llistaEstacions.get(min);
			}
		}	
	}
	
	public Llista<String> camiOptim(String identificador_origen, String indentificador_desti, int autonomia, ArrayList<EstacioRecarrega> llistaEstacions) throws ElementNotFoundException  {
		Llista<String> cami = new Llista<>();
		Map<EstacioRecarrega, Double> mapaDist = new HashMap<>();
		Map<EstacioRecarrega, EstacioRecarrega> nodesAnteriors = new HashMap<>();
		List<EstacioRecarrega> cua = new LinkedList<>();
		EstacioRecarrega estIni = null, estFi = null;
		EstacioRecarrega estMin = null;
		boolean trobat = false, error = false;
		
		 for (EstacioRecarrega e : llistaEstacions) {
			//Guadarmos cada estacion en una map, con distancia inicial 0
            mapaDist.put(e, DISTANCIA_INI);
            //Añadimos estacion a la cola
            cua.add(e);
            //Buscamos la estacio segun el identificador
            if (String.valueOf(e.getId()).equals(identificador_origen)) {
                estIni = e;
            }
            if (String.valueOf(e.getId()).equals(indentificador_desti)) {
                estFi = e;
            }
	     }
		 
		 //Recorrer los elementos de la cola
		 while (!cua.isEmpty() && !trobat && !error) {
			 Double distMin = DISTANCIA_MAX;
			 
			 for (EstacioRecarrega e : cua) {
				 //Si la distancia guardada es menor que la min, se modifica
	             if (mapaDist.get(e) < distMin) {
	                 distMin = mapaDist.get(e);
	                 estMin = e;
	             }
	         }
			 
			 //Borramos elemento de la cola, y si da error cambiamos variable para que el programa finalice
			 if (!cua.remove(estMin)) {
				 error = true;
			 }
			 
			 //Si la estación es la final, hemos terminado de buscar el camino entre ambas estaciones
			 if (estMin.getId() == estFi.getId()) {
				 trobat = true;
			 }
	         else {
	        	 //Calculamos el camino minimo entre ambas estaciones
	        	 //Inicializamos la distancia acumulada entre estaciones
	        	 Double distAcum = 0.0;
	        	 for (int i = 0; i < llistaEstacions.size(); i++) {
	        		 
	        		 for (int j = 0; j < llistaEstacions.size(); j++) {
	        			 if (matriuAdj[i][j] != null) {
	        				 
	        				 if (cua.contains(matriuAdj[i][j])) {
	 	                        Punt coordMin = new Punt(estMin.getLatitud(), estMin.getLongitud());
								Punt coordMat = new Punt(matriuAdj[i][j].getLatitud(), matriuAdj[i][j].getLongitud());
								double distancia = Punt.distancia(coordMin, coordMat)* CONVERT_KM;
										
								distAcum = mapaDist.get(estMin) + distancia;
								if (distAcum < autonomia) {
								    mapaDist.put(matriuAdj[i][j], distAcum);
								    nodesAnteriors.put(matriuAdj[i][j], estMin);
								}
	 	                    }
	        			 }
	        		 } 
	        	 }
	         } 
		}
		 
		while (estMin != estIni && estMin != null) {
			
			if (estMin != null) {
	            cami.inserir(String.valueOf(estMin.getId()));
	            estMin = nodesAnteriors.get(estMin);
			}
	    }
		
		if (estMin != null) {
			cami.inserir(String.valueOf(estMin.getId()));
		}

	    if (error) {
	    	throw new ElementNotFoundException();
	    }
		 
		return cami;
	}
	
	public Llista<String> zonesDistMaxNoGarantida(String indentificador_origen, int autonomia, ArrayList<EstacioRecarrega> llistaEstacions) {
		List<String> noVisitats = new ArrayList<>();
		EstacioRecarrega est = null;
		Llista<String> zones = new Llista<String>();
		
		//Cargamos todas las estaciones en la lista final
		for (EstacioRecarrega e : llistaEstacions) {
            if (String.valueOf(e.getId()).equals(indentificador_origen)) {
                est = e;
            }
            noVisitats.add(String.valueOf(e.getId()));  
        }
		
		//Si no se encuentra la estacion de origen
		if (est != null) {
	        noVisitats.remove(String.valueOf(est.getId()));
	        Stack<EstacioRecarrega> stack = new Stack<>();
	        Map<EstacioRecarrega, EstacioRecarrega> visitats = new HashMap<>();
	        stack.push(est);
	        
	        //Mientras que la pila no esté vacía
	        while (!stack.isEmpty()) {
	        	EstacioRecarrega estacio = stack.pop();
	            if (!visitats.containsKey(estacio)) {
	                visitats.put(estacio, estacio);
	                
	                int posicio = llistaEstacions.indexOf(estacio);
	        		for (int j = 0; j < llistaEstacions.size(); j++) {
	        			 if (matriuAdj[posicio][j] != null) {
	        				 Punt coordMin = new Punt(estacio.getLatitud(), estacio.getLongitud());
							 Punt coordMat = new Punt(matriuAdj[posicio][j].getLatitud(), matriuAdj[posicio][j].getLongitud());
							 double distancia = Punt.distancia(coordMin, coordMat)* CONVERT_KM;
							 
							 if (!visitats.containsKey(matriuAdj[posicio][j]) && distancia < autonomia) {
								 stack.push(matriuAdj[posicio][j]);
								 noVisitats.remove(String.valueOf(matriuAdj[posicio][j].getId()));
	
							 }
	        			 }
	        		}
	            }
	        }         
		}
		
		for (String str : noVisitats) {
			zones.inserir(str);
		}
		
		return zones;
	}
}
