package modelo;

public class Vertex {

	//Atributos
	private int id;
	private int posicio;
	
	public Vertex(int id, int posicio) {
		super();
		this.id = id;
		this.posicio = posicio;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPosicio() {
		return posicio;
	}
	public void setPosicio(int posicio) {
		this.posicio = posicio;
	}
}
