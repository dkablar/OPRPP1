package hr.fer.zemris.java.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw05.crypto.Util;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Razred <code>HexdumpCommand</code> predstavlja hexdump komandu
 * 
 * @author Dorian Kablar
 *
 */
public class HexdumpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		int index = 0;
		String path = new String();
		
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
			
			if(!arguments.substring(index).trim().isEmpty()) {
				env.writeln("Invalid arguments given");
				return ShellStatus.CONTINUE;
			}
		}
		
		if(path.isEmpty()) {
			if(arguments.contains("\n") || arguments.contains(" ") || 
					arguments.contains("\t") || arguments.contains("\r")) {
				env.writeln("Invalid arguments given");
				return ShellStatus.CONTINUE;
			}
			path = arguments;
		}
		
		File file = new File(path);
		InputStream is = null;
		
		if(!file.exists() || !file.isFile()) {
			env.writeln("Given argument must be an file.");
			return ShellStatus.CONTINUE;
		}
		
		if(!file.canRead()) {
			env.writeln("Content of the given file cannot be read");
			return ShellStatus.CONTINUE;
		}
		
		try {
			is = new BufferedInputStream(Files.newInputStream(file.toPath()));
			byte[] buff = new byte[16];
			long num = 0l;
			int tmp = 0;
			StringBuilder sb = new StringBuilder();
			
			while(true) {
				int r = is.read(buff);
				if(r<0) break;
				sb.append("0".repeat(8 - String.valueOf(num).length()) + String.valueOf(num) + ": ");
				char[] characters = new char[r];
				for(int i = 0; i < r; i++) {
					if(buff[i] < 32 || buff[i] > 127) {
						characters[i] = '.';
						buff[i] = '.';
					} else {
						characters[i] = (char) buff[i];
					}
				}
				
				for(int i = 0; i < 16; i++) {
					if(i < r) {
						if(tmp == 7) {
							sb.append(Util.bytetohex(new byte[] {buff[i]}).toUpperCase() + "|");
						} else {
							sb.append(Util.bytetohex(new byte[] {buff[i]}).toUpperCase() + " ");
						}
					}
					tmp++;
				}
				sb.append(r < 7 ? " ".repeat((8 - r)*3) + "|" + " ".repeat(8*3) + "| " : " ".repeat((16-r)*3) + "| ");
				for(Character c : characters)
					sb.append(c.toString());
				sb.append("\n");
				tmp = 0;
				num += 10;
			}
			
			env.write(sb.toString());
			
		} catch(Exception e) {
			env.writeln("Error occured while reading the content of given file.");
			return ShellStatus.CONTINUE;
		} finally {
			if(is != null) {
				try { is.close(); } catch(IOException e) {
					env.writeln("Error occured while reading the content of given file.");
					return ShellStatus.CONTINUE;
				}
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The 'hexdump' method expects a single argument - file name.");
		list.add("Method produces hex-output of the given file's content.");
		return null;
	}

}
