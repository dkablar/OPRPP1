package hr.fer.zemris.java.hw05.shell;

import java.util.List;

/**
 * Sučelje <code>ShellCommand</code> predstavlja komandu koju <code>MyShell</code> podržava
 * 
 * @author Dorian Kablar
 *
 */
public interface ShellCommand {

	/**
	 * Metoda koja izvodi zadanu komandu
	 * 
	 * @param env okruženje u kojem se naredba izvodi
	 * @param arguments argumenti naredbe
	 * @return status koji govori hoće li Shell nastaviti s radom
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Metoda vraća ime komande
	 * 
	 * @return ime komande
	 */
	String getCommandName();
	
	/**
	 * Metoda koja vraća listu s opisom komande
	 * 
	 * @return lista s opisom komande
	 */
	List<String> getCommandDescription();
	
}
