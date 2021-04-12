package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Razred <code>LsCommand</code> predstavlja ls komandu
 * 
 * @author Dorian Kablar
 *
 */
public class LsCommand implements ShellCommand {

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
			arguments = arguments.substring(2);
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		File dir = new File(directory);
		if(!dir.isDirectory()) {
			env.writeln("Given argument is not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		File[] children = dir.listFiles();
		
		for(File child : children) {
			Path p = child.toPath();
		
			BasicFileAttributeView faView = Files.getFileAttributeView(
				p, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
				);
			BasicFileAttributes attributes;
			try {
				attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			
				String res = new String();
				res += Files.isDirectory(p) ? "d" : "-";
				res += Files.isReadable(p) ? "r" : "-";
				res += Files.isWritable(p) ? "w" : "-";
				res += Files.isExecutable(p) ? "x" : "-";
				res += " " + " ".repeat(10 - String.valueOf(Files.size(p)).length()) + Files.size(p) 
				+ " " + formattedDateTime + " " + p.getFileName();
			
				env.writeln(res);
			} catch (IOException e) {
				env.writeln("File attributes could not be read.");
				return ShellStatus.CONTINUE;
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command 'ls' takes a single argument - directory - and writes a directory listing.");
		return Collections.unmodifiableList(list);
	}

}
