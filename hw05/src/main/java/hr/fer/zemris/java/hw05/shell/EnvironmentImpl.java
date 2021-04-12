package hr.fer.zemris.java.hw05.shell;

import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw05.shell.commands.CatCommand;
import hr.fer.zemris.java.hw05.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw05.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw05.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw05.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw05.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw05.shell.commands.LsCommand;
import hr.fer.zemris.java.hw05.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw05.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw05.shell.commands.TreeCommand;

/**
 * Razred <code>EnvironmentImpl</code> predstavlja implementaciju sučelja <code>Environment</code>
 * 
 * @author Dorian Kablar
 *
 */
public class EnvironmentImpl implements Environment {

	private Character promptSymbol = '>';
	private Character morelinesSymbol = '\\';
	private Character multilineSymbol = '|';
	private Map<String, ShellCommand> commands;
	
	/**
	 * Konstruktor, stvara novi <code>EnvironmentImpl</code> i puni njegovu mapu komandi
	 */
	public EnvironmentImpl() {
		commands = new TreeMap<>();
		commands.put("copy", new CopyCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsCommand());
		commands.put("cat", new CatCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("tree", new TreeCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("help", new HelpCommand());
		commands.put("symbol", new SymbolCommand());
	}
	
	@Override
	public String readLine() throws ShellIOException {
		try {
			Scanner sc = new Scanner(System.in);
			String line = sc.nextLine().trim();
			return line.trim();
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap((SortedMap<String, ShellCommand>) this.commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return this.multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return this.promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		this.promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return this.morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.morelinesSymbol = symbol;
	}

}
