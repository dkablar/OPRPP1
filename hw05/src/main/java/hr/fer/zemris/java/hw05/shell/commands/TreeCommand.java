package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Razred <code>TreeCommand</code> predstavlja tree komandu
 * 
 * @author kabla
 *
 */
public class TreeCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		int index = 0;
		String directory = new String();
		
		if(arguments.isEmpty()) {
			env.writeln("No arguments given.");
			return ShellStatus.CONTINUE;
		}
		
		if(arguments.startsWith("\\\"")) {
			arguments = arguments.substring(0, 2);
			while(true) {
				if(arguments.charAt(index) == ' ')
					index++;
				if(arguments.substring(index).startsWith("\\\"")) {
					index+=2;
					break;
				}
				directory += arguments.charAt(index);
				index++;
			}
			if(!arguments.substring(index).trim().isEmpty()) {
				env.writeln("Invalid arguments given");
				return ShellStatus.CONTINUE;
			}
		}
		
		if(directory.isEmpty()) {
			if(arguments.contains("\n") || arguments.contains(" ") || 
					arguments.contains("\t") || arguments.contains("\r")) {
				env.writeln("Invalid arguments given");
				return ShellStatus.CONTINUE;
			}
			directory = arguments;
		}
		
		File dir = new File(directory);
		if(!dir.isDirectory()) {
			env.writeln("Given argument is not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		printTree(dir, env, 0);
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The 'tree' command expects a single argument - directory - and prints a tree.");
		list.add("Each directory level shitfs output two characters to the right.");
		return Collections.unmodifiableList(list);
	}
	
	/**
	 * Pomoćna metoda koja služi za ispis stabla zadanog direktorija
	 * 
	 * @param f direktorij za koji se treba ispisati stablo
	 * @param env okruženje u koje se ispisuje stablo
	 * @param level dubina trenutnog čvora u stablu
	 */
	private static void printTree(File f, Environment env, int level) {
		env.write(" ".repeat(level*2) + f.getName() + "\n");
		if(f.isDirectory()) {
			File[] children = f.listFiles();
			if(children==null) return;
			for(File child : children)
				printTree(child, env, level+1);
		}
	}

}
