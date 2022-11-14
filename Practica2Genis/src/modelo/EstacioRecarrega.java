package modelo;

public class EstacioRecarrega {
	
	//Atributos
	private int id;
	private int id_estacio;
    private String nom;
    private String data;
    private double consum;
    private String carrer;
    private String ciutat;
    private Estat estat;
    private int temps;
    private double potencia;
    private String tipus;
    private double latitud;
    private double longitud;
    
	public EstacioRecarrega(int id, int id_estacio, String nom, String data, double consum, String carrer, String ciutat,
			Estat estat, int temps, double potencia, String tipus, double latitud, double longitud) {
		this.id = id;
		this.id_estacio = id_estacio;
		this.nom = nom;
		this.data = data;
		this.consum = consum;
		this.carrer = carrer;
		this.ciutat = ciutat;
		this.estat = estat;
		this.temps = temps;
		this.potencia = potencia;
		this.tipus = tipus;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_estacio() {
		return id_estacio;
	}

	public void setId_estacio(int id_estacio) {
		this.id_estacio = id_estacio;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public double getConsum() {
		return consum;
	}

	public void setConsum(double consum) {
		this.consum = consum;
	}

	public String getCarrer() {
		return carrer;
	}

	public void setCarrer(String carrer) {
		this.carrer = carrer;
	}

	public String getCiutat() {
		return ciutat;
	}

	public void setCiutat(String ciutat) {
		this.ciutat = ciutat;
	}

	public Estat getEstat() {
		return estat;
	}

	public void setEstat(Estat estat) {
		this.estat = estat;
	}

	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}

	public double getPotencia() {
		return potencia;
	}

	public void setPotencia(double potencia) {
		this.potencia = potencia;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
}
