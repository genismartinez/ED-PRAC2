package excepcion;

public class NewNodeException extends Exception{
	
	private static final String mensajeError = "Error al crear el nuevo nodo";
	
	public NewNodeException() {
		super(mensajeError);
	}
}
