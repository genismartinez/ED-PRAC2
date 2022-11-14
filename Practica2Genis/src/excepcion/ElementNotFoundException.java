package excepcion;

public class ElementNotFoundException extends Exception{
	
	private static final String mensajeError = "No se ha encontrado el elemento";
	
	public ElementNotFoundException() {
		super(mensajeError);
	}
	
	public ElementNotFoundException(int elements) {
		super(mensajeError + " posiciones : " + elements);
	}
}
