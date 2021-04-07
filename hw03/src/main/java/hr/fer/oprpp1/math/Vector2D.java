package hr.fer.oprpp1.math;

/**
 * Razred <code>Vector2D</code> predstavlja dvodimenzionalni vektor
 * 
 * @author Dorian Kablar
 *
 */
public class Vector2D {

	private double x;
	private double y;
	
	/**
	 * Konstruktor, stvara novi <code>Vector2D</code> s parametrima x i y
	 * 
	 * @param x x komponenta dvodimenzionalnog vektora
	 * @param y y komponenta dvodimenzionalnog vektora
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter, dohvaća x komponentu vektora
	 * 
	 * @return x komponenta vektora
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Getter, dohvaća y komponentu vektora
	 * 
	 * @return y komponenta vektora
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Metoda koja zbraja trenutni vektor s zadanim
	 * 
	 * @param offset vektor s kojim treba zbrojiti trenutni vektor
	 */
	public void add(Vector2D offset) {
		this.x = this.getX() + offset.getX();
		this.y = this.getY() + offset.getY();
	}
	
	/**
	 * Metoda koja zbraja trenutni vektor s zadanim, i stvara novi vektor koji predstavlja rezultat
	 * 
	 * @param offset vektor s kojim treba zbrojiti trenutni vektor
	 * @return novi vektor koji predstavlja rezultat zbrajanja
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.getX() + offset.getX(), this.getY() + offset.getY());
	}
	
	/**
	 * Metoda koja rotira trenutni vektor za zadani kut u radijanima
	 * 
	 * @param angle kut za koji treba rotirati trenutni vektor
	 */
	public void rotate(double angle) {
		double magnitude = Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
		double originalAngle = Math.atan2(this.getY(), this.getX());
		
		this.x = magnitude*Math.cos(angle + originalAngle);
		this.y = magnitude*Math.sin(angle + originalAngle);
	}
	
	/**
	 * Metoda koja rotira trenutni vektor za zadani kut u radijanima, i stvara novi vektor koji predstavlja rezultat
	 * 
	 * @param angle kut za koji treba rotirati trenutni vektor
	 * @return novi vektor koji predstavlja rezultat rotacije
	 */
	public Vector2D rotated(double angle) {
		double magnitude = Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
		double originalAngle = Math.atan2(this.getY(), this.getX());
		
		return new Vector2D(magnitude*Math.cos(angle + originalAngle), magnitude*Math.sin(angle + originalAngle)); 
	}
	
	/**
	 * Metoda koja skalira trenutni vektor za zadanu vrijednost
	 * 
	 * @param scaler vrijednost za koju treba skalirati trenutni vektor
	 */
	public void scale(double scaler) {
		this.x = this.getX() * scaler;
		this.y = this.getY() * scaler;
	}
	
	/**
	 * Metoda koja skalira trenutni vektor za zadanu vrijednost, i stvara novi vektor koji predstavlja rezultat
	 * 
	 * @param scaler vrijednost za koju treba skalirati trenutni vektor
	 * @return novi vektor koji predstavlja rezultat skaliranja
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.getX()*scaler, this.getY()*scaler);
	}
	
	/**
	 * Metoda vraća novu instancu trenutnog vektora
	 * 
	 * @return nova instanca trenutnog vektora
	 */
	public Vector2D copy() {
		return new Vector2D(this.getX(), this.getY());
	}
}
