package hr.fer.oprpp1.hw05.crypto;

/**
 * Razred <code>Util</code> služi kao pomoć u pretvaranju podataka iz jednog tipa u drugi, u ovom konkretnom slučaju,
 * iz bitovnog u heksadekadski, i obrnuto 
 * 
 * @author Dorian Kablar
 *
 */
public class Util {

	/**
	 * Metoda <code>hextobyte</code> služi za pretvaranje heksadekadskog zapisa u polje bajtova
	 * 
	 * @param keyText heksadekadski zapis
	 * @return polje bajtova koje predstavlja bitovni prikaz heksadekadskog zapisa
	 * @throws IllegalArgumentException ako duljina heksadekadskog zapisa nije ispravna, ili ako sam zapis sadrži
	 * neispravne znakove
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText.length() == 0)
			return new byte[0];
		if(keyText.length() % 2 != 0)
			throw new IllegalArgumentException("Duljina texta nije ispravna. Mora biti djeljiva s dva.");
		
		char[] arr = keyText.toLowerCase().toCharArray();
		byte[] res = new byte[keyText.length() / 2];
		
		for(int i = 0, j = 0; i < keyText.length(); i+=2, j++) {
			String s = parseKeyText(arr[i]) + parseKeyText(arr[i+1]);
			if(s.charAt(0) == '1') {
				s = complementToOriginal(s);
				res[j] = (byte) (getByte(s) * -1);
			} else {
				res[j] = getByte(s);
			}
		}
		return res;
	}
	
	/**
	 * Metoda <code>bytetohex</code> služi za pretvaranje polja bajtova u heksadekadski prikaz
	 * 
	 * @param bytearray polje bajtova koje treba pretvoriti u heksadekadski prikaz
	 * @return heksadekadski prikaz
	 */
	public static String bytetohex(byte[] bytearray) {
		if(bytearray.length == 0)
			return new String();
		
		String res = new String();
		
		for(byte b : bytearray)
			res += parseByte(b);
		
		return res;
	}
	
	/**
	 * Pomoćna metoda <code>parseKeyText</code> na temelju zadanog heksadekadskog znaka vraća bitovni prikaz istog
	 * 
	 * @param c heksadekadski znak
	 * @return bitovni prikaz heksadekadskog znaka
	 * @throws IllegalArgumentException ako je predan znak koji nije heksadekadski
	 */
	private static String parseKeyText(char c) {
		switch(c) {
		case '0': return "0000";
		case '1': return "0001";
		case '2': return "0010";
		case '3': return "0011";
		case '4': return "0100";
		case '5': return "0101";
		case '6': return "0110";
		case '7': return "0111";
		case '8': return "1000";
		case '9': return "1001";
		case 'a': return "1010";
		case 'b': return "1011";
		case 'c': return "1100";
		case 'd': return "1101";
		case 'e': return "1110";
		case 'f': return "1111";
		default: throw new IllegalArgumentException("Text sadrži neispravne znakove.");
		}
	}
	
	/**
	 * Pomoćna metoda <code>complementToOriginal</code> iz komplementiranog prikaza negativnog broja računa originalni prikaz
	 * istog
	 * 
	 * @param s komplementirani bitovni prikaz negativnog broja
	 * @return prikaz originalnog broja, zanemarivši da je on negativan
	 */
	private static String complementToOriginal(String s) {
		int pom = 0;
		for(int i = s.length()-1, j = 1; i >= 0; i--, j*=2) {
			pom += Integer.valueOf(s.substring(i, i+1)) * j;
		}
		pom--;
		String tmp = new String();
		
		while(pom > 0) {
			tmp += pom % 2;
			pom /= 2;
		}
		
		tmp += "0".repeat(8 - tmp.length());
		
		String res = new String();
		
		for(int i = tmp.length()-1; i >= 0; i--)
			res += tmp.charAt(i) == '1' ? "0" : "1";
		
		return res;
	}
	
	/**
	 * Pomoćna metoda <code>getByte</code> na temelju zadanog bitovnog prikaza broja računa taj broj
	 * 
	 * @param s bitovni prikaz broja
	 * @return broj koji je izračunat na temelju bitovnog prikaza
	 */
	private static byte getByte(String s) {
		int res = 0;
		for(int i = s.length()-1, j = 1; i >= 0; i--, j*=2) {
			res += Integer.valueOf(s.substring(i, i+1)) * j;
		}
		return (byte) res;
	}
	
	/**
	 * Pomoćna metoda koja na temelju zadanog byte-a računa njegov heksadekaski prikaz
	 * 
	 * @param b broj koji treba prikazati heksadekadski
	 * @return heksadekadski prikaz byte-a
	 */
	private static String parseByte(int b) {
		boolean negative = false;
		String res = new String();
		
		if(b < 0) {
			b *= -1;
			negative = true;
		}
		
		String pom = new String();
		while(b > 0) {
			pom += b % 2 == 0 ? "0" : "1";
			b /= 2;
		}
		
		pom = pom + "0".repeat(8 - pom.length());
		
		if(negative) {
			String pom2 = new String();
			
			for(int i = 0; i < pom.length(); i++) {
				pom2 += pom.charAt(i) == '0' ? "1" : "0";
			}
			
			int tmp = 0;
			for(int i = 0, j = 1; i < pom2.length(); i++, j*=2)
				tmp += Integer.valueOf(pom2.substring(i, i+1)) * j;
			tmp++;
			
			pom = new String();
			while(tmp > 0) {
				pom += tmp % 2 == 0 ? "0" : "1";
				tmp /= 2;
			}
			
		}
		
		res += parseByteText(pom.substring(4)) + parseByteText(pom.substring(0, 4));
		
		return res;
	}
	
	/**
	 * Metoda koja na temelju zadanog 4-bitnog prikaza računa pripadajući heksadekadski znak
	 * 
	 * @param s 4-bitni prikaz
	 * @return heksadekadski znak
	 */
	private static String parseByteText(String s) {
		switch(s) {
		case "0000": return "0";
		case "1000": return "1";
		case "0100": return "2";
		case "1100": return "3";
		case "0010": return "4";
		case "1010": return "5";
		case "0110": return "6";
		case "1110": return "7";
		case "0001": return "8";
		case "1001": return "9";
		case "0101": return "a";
		case "1101": return "b";
		case "0011": return "c";
		case "1011": return "d";
		case "0111": return "e";
		case "1111": return "f";
		default: throw new IllegalArgumentException("Text sadrži neispravne znakove.");
		}
	}
}
