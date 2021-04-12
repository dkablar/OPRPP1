package hr.fer.zemris.java.hw05.shell;

import java.util.SortedMap;

/**
 * Sučelje <code>Environment</code> predstavlja okruženje u kojem se izvodi <code>MyShell</code>. Preko njega se ostvaruje
 * komunikacija Shell-a sa korisnikom.
 * 
 * @author Dorian Kablar
 *
 */
public interface Environment {

	/**
	 * Metoda koja čita komande koje korisnik unese
	 * 
	 * @return komanda koju je korisnik unio
	 * @throws ShellIOException ako je došlo do greške prilikom čitanja komandi
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Metoda koja korisniku ispisuje rezultate zadanih komandi
	 * 
	 * @param text tekst koji treba ispisati korisniku
	 * @throws ShellIOException ako dođe do greške prilikom ispisa odgovora
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Metoda koja korisniku ispisuje rezultate zadanih komandi u obliku linijskog teksta
	 * 
	 * @param text tekst koji treba ispisati korisniku u obliku linijskog teksta
	 * @throws ShellIOException ako dođe do greške prilikom ispisa odgovora
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Metoda koja vraća sortiranu mapu svih podržanih komandi Shell-a
	 * 
	 * @return sortirana mapa koja sadrži sve podržane komande Shell-a
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Metoda koja korisniku ispisuje trenutni znak koji se ispisuje kada korisnik piše komandu kroz više linija
	 * 
	 * @return MultiLine simbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Metoda koja mijenja znak koji se ispisuje kada korisnik piše komandu kroz više linija
	 * 
	 * @param symbol novi Multiline znak
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Metoda koja korisniku ispisuje trenutni znak koji se ispisuje kada Shell očekuje da korisnik napiše komandu
	 * 
	 * @return Prompt simbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Metoda koja mijenja znak koji se ispisuje kada Shell očekuje da korisnik napiše komandu
	 * 
	 * @param symbol novi Prompt simbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Metoda koja korisniku ispisuje trenutni znak koji se koristi kada korisnik želi označiti Shell-u da komanda ima više linija
	 * 
	 * @return MoreLines simbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Metoda koja mijenja znak koji se koristi kada korisnik želi označiti Shell-u da komanda ima više linija
	 * 
	 * @param symbol novi MoreLines simbol
	 */
	void setMorelinesSymbol(Character symbol);
}
