package main;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import excepcion.ElementNotFoundException;
import modelo.EstacioRecarrega;
import modelo.Estat;
import modelo.Graf;
import modelo.Llista;

public class Main {

	private static ArrayList<EstacioRecarrega> llegirJSON() {
		
		JSONParser parser = new JSONParser();
		ArrayList<EstacioRecarrega> arr = new ArrayList<EstacioRecarrega>();
		
		try {
			JSONArray a = (JSONArray) parser.parse(new FileReader("icaen.json"));
	
			for (Object o : a)
			{
			    JSONObject estacio = (JSONObject) o;
		
			    String id = (String) estacio.get("id");
			    String id_estacio = (String) estacio.get("id_estacio");
			    String nom = (String) estacio.get("nom");
			    String data = (String) estacio.get("data");
			    String consum = (String) estacio.get("consum");
			    String carrer = (String) estacio.get("carrer");
			    String ciutat = (String) estacio.get("ciutat");
			    String estat = (String) estacio.get("estat");
			    String temps = (String) estacio.get("temps");
			    String potencia = (String) estacio.get("potencia");
			    String tipus = (String) estacio.get("tipus");
			    String latitud = (String) estacio.get("latitud");
			    String longitud = (String) estacio.get("longitud");
		       
			    Estat estatDef = null;
			    double consumo = 0;
			    double potenciaConvert = 0;
			    int tempsConvert = 0;
			    
			    if (estat.equals("lliure")) {
			    	estatDef = Estat.LLIURE;
			    }
			    else if (estat.equals("ocupat")) {
			    	estatDef = Estat.OCUPAT;
			    }
			    else if (estat.equals("error")) {
			    	estatDef = Estat.ERROR;
			    }
			    
			    if (!consum.equals("")) {
			    	consumo = Double.parseDouble(consum);
			    }
			    
			    if (!potencia.equals("")) {
			    	potenciaConvert = Double.parseDouble(potencia);
			    }
			    
			    if (!temps.equals("")) {
			    	tempsConvert = Integer.parseInt(temps);
			    }
			    
			    EstacioRecarrega objEstacio = new EstacioRecarrega(Integer.parseInt(id), Integer.parseInt(id_estacio), nom, data, consumo, 
			    							  carrer, ciutat, estatDef, tempsConvert, potenciaConvert, tipus, 
			    							  Double.parseDouble(latitud), Double.parseDouble(longitud));
			
			    arr.add(objEstacio);
			}
		} 
		catch (Exception e) {
            e.printStackTrace();
		}
		
		return arr;
	}

	public static void main(String[] args) throws ElementNotFoundException {
		
		ArrayList<EstacioRecarrega> llistaEstacions =  llegirJSON();
		Graf graf = new Graf();
		graf.CrearGraf();
		graf.setNumVert(llistaEstacions.size());
		graf.setMatriuAdj();
		graf.construirGraf(llistaEstacions); 
		Llista<EstacioRecarrega> llistaOptim = graf.camiOptim("61737", "12727513", 500, llistaEstacions);
		
		System.out.println("\n-----------Cami optim--------");
		Iterator it = llistaOptim.iterator();
		while (it.hasNext()) {
			System.out.print(it.next() + " ");
		}
		
		
		Llista<EstacioRecarrega> llistaDist = graf.zonesDistMaxNoGarantida("61737", 500, llistaEstacions);
		
		System.out.println("\n-----------Zona distancia no garantida--------");
		Iterator it2 = llistaDist.iterator();
		while (it2.hasNext()) {
			System.out.print(it2.next() + " ");
		}
	}
}
