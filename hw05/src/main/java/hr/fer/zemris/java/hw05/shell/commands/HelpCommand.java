package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Razred <code>HelpCommand</code> predstavlja 'help' komandu
 * 
 * @author Dorian Kablar
 *
 */
public class HelpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, ShellCommand> commands = env.commands();
		
		if(arguments.isBlank()) {
			for(String s : commands.keySet()) {
				ShellCommand command = commands.get(s);
				env.writeln(command.getCommandName());
			}
		}
		
		ShellCommand command = commands.get(arguments);
		if(command == null) {
			env.writeln("Given command is not supported.");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln(command.getCommandName());
		for(String description : command.getCommandDescription())
			env.writeln(description);
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("If stated with no arguments, it lists names of all supported commands.");
		list.add("If started with a single argument, it must print name and description of selected command.");
		return Collections.unmodifiableList(list);
	}

}
