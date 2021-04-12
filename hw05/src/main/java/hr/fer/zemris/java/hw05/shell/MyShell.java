package hr.fer.zemris.java.hw05.shell;

import java.util.Set;

/**
 * Razred <code>MyShell</code> predstavlja Shell
 * 
 * @author Dorian Kablar
 *
 */
public class MyShell {
	
	private Environment env;
	private ShellStatus status;
	
	/**
	 * Konstruktor, stvara novi <code>MyShell</code> i ostvaruje komunikaciju s korisnikom
	 */
	public MyShell() {
		this.env = new EnvironmentImpl();
		this.status = ShellStatus.CONTINUE;
		
		this.env.writeln("Welcome to MyShell v 1.0");
		while(this.status == ShellStatus.CONTINUE) {
			this.env.write(this.env.getPromptSymbol() + " ");
			String command = new String();
			while(true) {
				String line = this.env.readLine().trim();
				command += line;
				if(!(command.charAt(command.length()-1) == this.env.getMorelinesSymbol())) {
					break;
				}
				command = command.substring(0, command.length()-1);
				this.env.write(this.env.getMultilineSymbol() + " ");
			}
			
			Set<String> keys = this.env.commands().keySet();
			String key = command.trim().contains(" ") ? command.split(" ")[0] : command;
			if(!keys.contains(key)) {
				this.env.writeln("Given command is not supported.");
				this.status = ShellStatus.CONTINUE;
				continue;
			}
			
			this.status = this.env.commands().get(key).executeCommand(env, command.substring(key.length()).trim());
		}
		env.writeln("May the force be with you :)");
	}
	
	public static void main(String[] args) {
		MyShell shell = new MyShell();
	}
}
