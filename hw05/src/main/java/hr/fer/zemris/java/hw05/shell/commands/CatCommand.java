package hr.fer.zemris.java.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Razred <code>CatCommand</code> predstavlja cat komandu
 * 
 * @author Dorian Kablar
 *
 */
public class CatCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		String path = new String();
		String charset = new String();
		int index = 0;
		
		if(arguments.isEmpty()) {
			env.writeln("No arguments given.");
			return ShellStatus.CONTINUE;
		}
		
		if(arguments.startsWith("\\\"")) {
			arguments = arguments.substring(2);
			while(index < arguments.length()) {
				if(arguments.charAt(index) == ' ') {
					index++;
					continue;
				}
				if(arguments.substring(index).startsWith("\\\"")) {
					index+=2;
					break;
				}
				path += arguments.charAt(index);
				index++;
			}
			if(index < arguments.length() && !Character.isWhitespace(arguments.charAt(index))) {
				env.writeln("Invalid arguments given.");
				return ShellStatus.CONTINUE;
			}
		}
		
		if(path.isEmpty()) {
			while(index < arguments.length()) {
				if(arguments.charAt(index) == '\n' || arguments.charAt(index) == ' ' || 
						arguments.charAt(index) == '\r' || arguments.charAt(index) == '\t')
					break;
				path += arguments.charAt(index);
				index++;
			}
		}
		
		arguments = arguments.substring(index).trim();
		
		if(!arguments.isEmpty()) {
			if(arguments.contains(" ")) {
				env.writeln("Invalid number of arguments.");
				return ShellStatus.CONTINUE;
			}
			charset = arguments;
		}
		
		Path p = Paths.get(path);
		if(!Files.exists(p) || Files.isDirectory(p) || !Files.isReadable(p)) {
			env.writeln("Given file cannot be opened.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			BufferedReader br = new BufferedReader(
					 new InputStreamReader(
					 new BufferedInputStream(
					 Files.newInputStream(p)), charset.isEmpty() ? Charset.defaultCharset().displayName() : charset));
			
			while(true) {
				String line = br.readLine();
				if(line == null)
					break;
				env.writeln(line);
			}
		} catch (UnsupportedEncodingException e) {
			env.writeln("File content could not be read.");
			return ShellStatus.CONTINUE;
		} catch (IOException e) {
			env.writeln("File content could not be read.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command 'cat' takes one or two arguments.");
		list.add("The first argument is a path to some file and is mandatory.");
		list.add("The second argument is charset name that should be used to interpret chars from bytes.");
		list.add("If not provided, a default platform charset will be used.");
		return Collections.unmodifiableList(list);
	}

}
