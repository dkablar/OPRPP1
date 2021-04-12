package hr.fer.zemris.java.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Razred <code>CopyCommand</code> predtavlja 'copy' komandu
 * 
 * @author Dorian Kablar
 *
 */
public class CopyCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		String first = new String(), second = new String();
		int index = 0;
		
		if(arguments.isEmpty()) {
			env.writeln("No arguments given.");
			return ShellStatus.CONTINUE;
		}
		
		if(arguments.startsWith("\\\"")) {
			arguments = arguments.substring(2);
			index = 0;
			while(index < arguments.length()) {
				if(arguments.charAt(index) == ' ')
					index++;
				if(arguments.substring(index).startsWith("\\\"")) {
					index+=2;
					break;
				}
				first += arguments.charAt(index);
				index++;
			}
		}
		
		if(first.isEmpty()) {
			while(index < arguments.length()) {
				if(Character.isWhitespace(arguments.charAt(index)))
					break;
				first += arguments.charAt(index);
				index++;
			}
			if(first.contains("\\\"")) {
				env.write("Invalid first argument given.");
				return ShellStatus.CONTINUE;
			}
		}
		
		if(arguments.substring(index).isEmpty() || !Character.isWhitespace(arguments.charAt(index))) {
			env.writeln("Invalid first argument given.");
			return ShellStatus.CONTINUE;
		}
		
		arguments = arguments.substring(index).trim();
		index = 0;
		
		if(arguments.isEmpty()) {
			env.writeln("Invalid number of arguments (2 are expected).");
			return ShellStatus.CONTINUE;
		}
		
		if(arguments.startsWith("\\\"")) {
			arguments = arguments.substring(2);
			index = 0;
			while(index < arguments.length()) {
				if(arguments.charAt(index) == ' ')
					index++;
				if(arguments.substring(index).startsWith("\\\"")) {
					index+=2;
					break;
				}
				second += arguments.charAt(index);
				index++;
			}
		}
		
		if(second.isEmpty()) {
			while(index < arguments.length()) {
				if(Character.isWhitespace(arguments.charAt(index)))
					break;
				second += arguments.charAt(index);
				index++;
			}
			if(second.contains("\\\"")) {
				env.write("Invalid second argument given.");
				return ShellStatus.CONTINUE;
			}
		}
		
		if(!arguments.substring(index).isEmpty()) {
			env.writeln("Invalid second argument given.");
			return ShellStatus.CONTINUE;
		}
		
		File original = new File(first);
		if(!original.isFile()) {
			env.writeln("The first argument must be an file.");
			return ShellStatus.CONTINUE;
		}
		
		File copy = new File(second);
		if(copy.isFile()) {
			if(!copy.canWrite()) {
				env.writeln("If second argument is file, it must be overwriteable");
				return ShellStatus.CONTINUE;
			}
			try {
				Path o = original.toPath();
				Path c = copy.toPath();
				BufferedInputStream reader = new BufferedInputStream(Files.newInputStream(o));
				BufferedOutputStream writer = new BufferedOutputStream(Files.newOutputStream(c));
				writer.write(new byte[] {});
				writer.flush();
				byte[] buff = new byte[4096];
				
				while(true) {
					int r = reader.read(buff);
					if(r<4096) {
						if(r>1)
							writer.write(buff, 0, r);
						break;
					}
					
					writer.write(buff);
				}
			} catch(Exception e) {
				env.writeln("Original file content could not be read.");
				return ShellStatus.CONTINUE;
			}
		}
		
		if(!copy.isDirectory()) {
			env.writeln("Second argument must be a file or a directory.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Path o = original.toPath();
			String name = copy.getAbsolutePath() + original.getName();
			File c = copy;
			c.renameTo(new File(name));
			Path dest = c.toPath();
			
			BufferedInputStream reader = new BufferedInputStream(Files.newInputStream(o));
			BufferedOutputStream writer = new BufferedOutputStream(Files.newOutputStream(dest));
			byte[] buff = new byte[4096];
			
			while(true) {
				int r = reader.read(buff);
				if(r<4096) {
					if(r>1)
						writer.write(buff, 0, r);
					break;
				}
				
				writer.write(buff);
			}
		} catch(Exception e) {
			env.writeln("Original file content could not be read.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The 'copy' command expects two arguments: source file name and destination file name(i.e. paths and names).");
		list.add("If the second argument is directory, original file will be copied into that directory.");
		return Collections.unmodifiableList(list);
	}

}
