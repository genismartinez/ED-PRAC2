package modelo;

public class Punt {
	//Atributos de la clase
	private double x;
	private double y;
	
	//Constructor por defecto: no tiene parametros
	//public nombreClase(vacio)
	public Punt() {
		x = 0;
		y = 0;
	}
	
	//Constructor con parametros
	//public nombreClase(parametros)
	public Punt(double d, double e) {
		this.x = d;
		this.y = e;
	}
	
	//Metodo devuelve la coordenada X de un punto
	public double getX() {
		return x;
	}
	
	//Medodo devuelve la coordenada Y de un punto
	public double getY() {
		return y;
	}
	
	//Metodo String: devuelve el objeto Punto como un String
	@Override
	public String toString() {
		String texto = "";
		texto = "(" + x + ", " + y + ")";
		return texto;
	}
	
	//Metodo que devuelve que dos puntos son iguales
	@Override
	public boolean equals(Object obj) {
		Punt punt = (Punt) obj;
		
		if (x != punt.x)
			return false;
		if (y != punt.y)
			return false;
		
		return true;
	}
	
	//Metodo sobrecargado que devuelve la distancia entre dos puntos
	//Metodo parametros coordenadas
	public static double distancia(int x1, int y1, int x2, int y2) {
		double distancia = 0;
		distancia = (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))*100)/100;
		return distancia;
	}

	//Metodo parametros objetos Punto
	public static double distancia(Punt p1, Punt p2) {
		double distancia = 0;
		distancia = (Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2)) * 100)/100;
		return distancia;
	}
}
