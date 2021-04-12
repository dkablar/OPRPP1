package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Razred <code>SymbolCommand</code> predstavlja symbol komandu
 * 
 * @author Dorian Kablar
 *
 */
public class SymbolCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(arguments.isBlank()) {
			env.writeln("Invalid arguments given.");
			return ShellStatus.CONTINUE;
		}
		
		arguments = arguments.trim();
		
		String symbol = new String();
		Character character;
		int index = 0;
		
		while(index < arguments.length()) {
			if(Character.isWhitespace(arguments.charAt(index))) {
				break;
			}
			symbol += arguments.charAt(index);
			index++;
		}
		
		
		arguments = arguments.substring(index).trim();
		
		if(arguments.isEmpty()) {
			switch(symbol) {
			case "PROMPT" -> {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
				return ShellStatus.CONTINUE;
			}
			case "MULTILINE" -> {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
				return ShellStatus.CONTINUE;
			}
			case "MORELINES" -> {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
				return ShellStatus.CONTINUE;
			}
			default -> {
				env.writeln("Invalid symbol given. Valid symbols are: PROMPT, MULTILINE and MORELINES.");
				return ShellStatus.CONTINUE;
			}
			}
		}
		
		if(arguments.trim().length() != 1) {
			env.writeln("Symbol can only be one character.");
			return ShellStatus.CONTINUE;
		}
		
		character = arguments.trim().charAt(0);
		
		switch(symbol) {
		case "PROMPT" -> {
			Character oldSymbol = env.getPromptSymbol();
			env.setPromptSymbol(character);
			env.writeln("Symbol for PROMPT changed from '" + oldSymbol + "' to '" + env.getPromptSymbol() + "'");
			return ShellStatus.CONTINUE;
		}
		case "MULTILINE" -> {
			Character oldSymbol = env.getMultilineSymbol();
			env.setMultilineSymbol(character);
			env.writeln("Symbol for MULTILINE changed from '" + oldSymbol + "' to '" + env.getMultilineSymbol() + "'");
			return ShellStatus.CONTINUE;
		}
		case "MORELINES" -> {
			Character oldSymbol = env.getMorelinesSymbol();
			env.setMorelinesSymbol(character);
			env.writeln("Symbol for MORELINES changed from '" + oldSymbol + "' to '" + env.getMorelinesSymbol() + "'");
			return ShellStatus.CONTINUE;
		}
		default -> {
			env.writeln("Invalid symbol given. Valid symbols are: PROMPT, MULTILINE and MORELINES.");
			return ShellStatus.CONTINUE;
		}
		}
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The 'symbol' command is used to change the PROMPT, MORELINES or MULTILINE character.");
		return Collections.unmodifiableList(list);
	}

}
