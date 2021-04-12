package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Razred <code>Crypto</code> služi kao alat za kriptiranje/dekriptiranje datoteka te za 
 * računanje zaštitnih suma sadržaja datoteka
 * 
 * @author Dorian Kablar
 *
 */
public class Crypto {
	
	/**
	 * Metoda računa zaštitnu sumu sadržaja zadane datoteke na temelju algoritma SHA-256
	 * 
	 * @param file datoteka za koju treba izračunati sumu
	 */
	public static void checksha(String file) {
		Path p = Paths.get(file);
		InputStream is = null;
		String calculatedDigest, expectedDigest;
		Scanner sc = new Scanner(System.in);
		
		try{
			System.out.printf("Please provide expected sha-256 digest for %s:%n> ", file);
			expectedDigest = sc.nextLine().trim();
			
			is = new BufferedInputStream(Files.newInputStream(p));
			byte[] buff = new byte[4096];
			byte[] digest;
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			
			while(true) {
				int r = is.read(buff);
				if(r<4096) {
					if(r>1)
						sha.update(buff, 0, r);
					digest = sha.digest();
					break;
				}
				
				sha.update(buff, 0, r);
			}
			
			calculatedDigest = Util.bytetohex(digest);
			if(calculatedDigest.equals(expectedDigest.toLowerCase()))
				System.out.println("Digesting completed. Digest of " + file + " matches expected digest.");
			else
				System.out.println("Digesting completed. Digest of " + file + " does not match the excepted digest. "
						+ "Digest was: " + calculatedDigest);
			
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(is != null) {
				try { is.close(); } catch(IOException e) {}
			}
		}
	}
	
	/**
	 * Metoda koja kriptira/dekrpitira zadanu datoteku i taj kriptirani sadržaj sprema u drugu zadanu datoteku
	 * 
	 * @param encrypt zastavica koja određuje radi li se kriptiranje ili dekriptiranje
	 * @param readFile datoteka koja se krpitira/dekriptira
	 * @param writeFile datoteka koja nastanje kriptiranjem/dekriptiranjem prve
	 */
	public static void crypt(boolean encrypt, String readFile, String writeFile) {
		Path rp = Paths.get(readFile);
		Path wp = Paths.get(writeFile);
		InputStream is = null;
		OutputStream os = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits): \n> ");
			String keyText = sc.nextLine().trim();
			System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits): \n> ");
			String ivText = sc.nextLine().trim();
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			
			is = new BufferedInputStream(Files.newInputStream(rp));
			os = new BufferedOutputStream(Files.newOutputStream(wp));
			
			byte[] buff = new byte[4096];
			byte[] crypt;
			
			while(true) {
				int r = is.read(buff);
				if(r < 4096) {
					if(r > 1) {
						crypt = cipher.update(buff, 0, r);
						os.write(crypt);
						continue;
					}
					crypt = cipher.doFinal();
					os.write(crypt);
					break;
				}
				crypt = cipher.update(buff, 0, r);
				os.write(crypt);
			}
			
			System.out.print(encrypt ? "Encryption " : "Decryption ");
			System.out.println("completed. Generated file " + writeFile + " based on file " + readFile + ".");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(is != null) {
				try { is.close(); } catch(IOException e) {}
			}
			if(os != null) {
				try { os.close(); } catch(IOException e) {}
			}
		}
	}
	
	public static void main(String[] args) {
		
		if(args.length < 2 || args.length > 3)
			throw new IllegalArgumentException("Nedozvoljeni broj argumenata.");

		switch(args[0]) {
		case "checksha" -> {
			checksha(args[1]);
		}
		case "encrypt" -> {
			crypt(true, args[1], args[2]);
		}
		case "decrypt" -> {
			crypt(false, args[1], args[2]);
		}
		default -> {
			throw new IllegalArgumentException("Neispravni argumenti.");
		}
		}
	}
}
