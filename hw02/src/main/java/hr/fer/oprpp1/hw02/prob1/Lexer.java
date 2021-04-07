package hr.fer.oprpp1.hw02.prob1;

/**
 * Razred <code>Lexer</code> predstavlja leksički analizator
 * 
 * @author Dorian Kablar
 *
 */
public class Lexer {
	private char[] data; // ulazni tekst
	private Token token; // trenutni token
	private int currentIndex; // index prvog neodređenog znaka
	private LexerState lexerState = LexerState.BASIC; // način rada lexera
	
	/**
	 * Stvara novi <code>Lexer</code> koji prima novi tekst koji se treba tokenizirati, tj. analizirati
	 * 
	 * @param text tekst koji treba tokenizirati
	 */
	public Lexer(String text) {
		if(text == null) 
			throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
	}
	
	/**
	 * Metoda generira i vraća sljedeći token, tj. sljedeću leksičku jedinku
	 * 
	 * @return sljedeći token
	 * @throws LexerException ako je došlo do pogreške
	 */
	public Token nextToken() {
		if(this.currentIndex > this.data.length)
			throw new LexerException("index je veći od veličine polja.");
		String word = new String();
		String number = new String();
		
		while(this.currentIndex < this.data.length) {
			
			// obrada za WORD
			if(this.lexerState == LexerState.BASIC) { // "normalno" ponašanje
				if(this.data[this.currentIndex] == '\\' && (this.currentIndex + 1 >= this.data.length || 
						Character.isLetter(this.data[this.currentIndex + 1])))
					throw new LexerException("Nedozvoljeni escape");
			
				while(Character.isLetter(this.data[this.currentIndex])) {
					word += this.data[this.currentIndex++];
					if(this.currentIndex == this.data.length)
						break;
				}
			
				if(this.currentIndex < this.data.length && this.data[this.currentIndex] == '\\' && (Character.isDigit(this.data[this.currentIndex + 1]) 
						|| this.data[this.currentIndex + 1] == '\\')) {
					word += this.data[++this.currentIndex];
					this.currentIndex++;
					continue;
				}
			} else { // "prošireno" ponašanje
				while(Character.isLetterOrDigit(this.data[this.currentIndex]) || this.data[this.currentIndex] == '\\') {
					word += this.data[this.currentIndex++];
					if(this.currentIndex == this.data.length)
						break;
				}
			}
			
			if(word.length() != 0)
				break;
			
			// obrada za NUMBER
			if(this.lexerState == LexerState.BASIC) {
				while(Character.isDigit(this.data[this.currentIndex])) {
					number += this.data[this.currentIndex++];
					if(this.currentIndex == this.data.length)
						break;
				}
			}
			
			if(number.length() != 0)
				break;
			
			// obrada za SYMBOL
			if(this.data[this.currentIndex] != ' ' && this.data[this.currentIndex] != '\t' &&
					this.data[this.currentIndex] != '\n' && this.data[this.currentIndex] != '\r') {
				Character symbol = this.data[currentIndex];
				this.currentIndex++;
				this.token = new Token(TokenType.SYMBOL, symbol);
				return this.token;
			}
			
			// obrada ostalih znakova
			while(this.currentIndex < this.data.length && (this.data[this.currentIndex] == ' ' || this.data[this.currentIndex] == '\t' ||
					this.data[this.currentIndex] == '\n' || this.data[this.currentIndex] == '\r'))
				this.currentIndex++;
		}
		
		if(word.length() != 0) {
			this.token = new Token(TokenType.WORD, word);
			return this.token;
		}
		
		if(number.length() != 0) {
			try {
				this.token = new Token(TokenType.NUMBER, Long.valueOf(number));
				return this.token;
			} catch(NumberFormatException ex) {
				throw new LexerException("Broj nije ispravno zadan.");
			}
		}
		
		if(this.currentIndex == this.data.length) {
			this.token = new Token(TokenType.EOF, null);
			this.currentIndex++;
			return this.token;
		}

		return this.token;
	}
	
	/**
	 * Metoda vraća zadnji generirani token. Može se pozivati više puta.
	 * Ne pokreće generiranje sljedećeg tokena
	 * 
	 * @return zadnji generirani token
	 */
	public Token getToken() {
		return this.token;
	}
	
	/**
	 * Metoda koja postavlja načina rada leksičkog analizatora
	 * 
	 * @param state način rada leksičkog analizatora
	 */
	public void setState(LexerState state) {
		if(state == null)
			throw new NullPointerException();
		this.lexerState = state;
	}
	
}
