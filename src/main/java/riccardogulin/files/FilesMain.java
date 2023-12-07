package riccardogulin.files;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FilesMain {
	public static void main(String[] args) {
		File file = new File("src/output.txt"); // Creo un riferimento al file e al suo percorso

		try {
			FileUtils.writeStringToFile(file, "NUOVO CONTENUTO" + System.lineSeparator(), StandardCharsets.UTF_8, true);
			// System.lineSeparator() è il carattere che rappresenta l' "a capo"
			System.out.println("Scritto");
			String contenuto = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
			System.out.println("Ho trovato all'interno del file: " + contenuto);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
