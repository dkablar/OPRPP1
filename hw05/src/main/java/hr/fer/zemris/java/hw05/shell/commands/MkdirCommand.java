package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Razred <code>MkdirCommand</code> predstavlja mkdir komandu
 * 
 * @author Dorian Kablar
 *
 */
public class MkdirCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		
		if(arguments.isEmpty()) {
			env.writeln("No arguments given.");
			return ShellStatus.CONTINUE;
		}
		
		int index = 0;
		String directory = new String();
		
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
		if(!dir.mkdir()) {
			env.writeln("Given directory could not be created.");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The 'mkdir' command takes a single argument: directory name, and creates the appropriate directory structure");
		return Collections.unmodifiableList(list);
	}

}
