package excepcion;

public class IndexException extends IndexOutOfBoundsException{

	private static final String mensajeError = "No existe la posicion";
	
	public IndexException() {
		super(mensajeError);
	}
	
	public IndexException(int posicio) {
		super(mensajeError + " : " + posicio);
	}
}
