import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortLines {
	public static void main(String... args) { // ist Dir die Parameterangabe mit ... geläufig?
		File inputFile = new File("Test.txt");
		File outputFile = new File("Ausgabe.txt");
		File outputFile2 = new File("Ausgabe2.txt");
		File outputFile3 = new File("Ausgabe3.txt");
		File outputFile4 = new File("Ausgabe4.txt");
		File outputFile5 = new File("Ausgabe5.txt");
		
		// Eingabedatei ohne Kommentar- und Leerzeilen einlesen,
		// sortieren und in Ausgabedatei schreiben
		removeCommentsAndSortFile(inputFile, outputFile);
		removeCommentsAndSortFile2(inputFile, outputFile2);
		removeCommentsAndSortFile3(inputFile, outputFile3);
		removeCommentsAndSortFile4(inputFile, outputFile4);
		removeCommentsAndSortFile5(inputFile, outputFile5);
		
		// Und hier noch eine Zusatzaufgabe für Dich: Befülle diese Methode mit Code
		checkContentEquals(outputFile, outputFile2, outputFile3, outputFile4, outputFile5);
	}

	private static void checkContentEquals(File... files) {
		boolean filesAreEqual = true;
		
		// Hier passenden Code einfügen
		
		System.out.println("Die Dateien haben " + (filesAreEqual ? "" : "NICHT ") + "den gleichen Inhalt");
	}

	// Ungefähr so war Dein Code, der wunderbar funktionieren würde
	private static void removeCommentsAndSortFile(File inputFile, File outputFile) {
		BufferedReader reader = null; // Hier deklariert, damit in finally verfügbar
		PrintWriter writer = null; // Hier deklariert, damit in finally verfügbar
		try {
	        reader = new BufferedReader(new FileReader(inputFile));
	        List<String> lines = new ArrayList<>();
	        String line;

	        while ((line = reader.readLine()) != null) {
	            if (!line.startsWith("#") && !line.isBlank()) {
	                lines.add(line);
	            }
	        }

	        Collections.sort(lines);
	        writer = new PrintWriter(outputFile);

	        for (String sortedLine : lines) {
	            writer.println(sortedLine);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
	        try {
	        	// muss in neuen try/catch-Block, weil auch hier eine IOException möglich ist
				reader.close();
		        writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// So könnte man sich die Intialisierung von Reader und Writer mit null
	// sowie den gesamten finally-Block sparen
	private static void removeCommentsAndSortFile2(File inputFile, File outputFile) {
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				PrintWriter writer = new PrintWriter(outputFile)) {
	        List<String> lines = new ArrayList<>();
	        String line;

	        while ((line = reader.readLine()) != null) {
	            if (!line.startsWith("#") && !line.isBlank()) {
	                lines.add(line);
	            }
	        }

	        Collections.sort(lines);

	        for (String sortedLine : lines) {
	            writer.println(sortedLine);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// So könnte man sich sowohl Reader als auch Writer sparen
	private static void removeCommentsAndSortFile3(File inputFile, File outputFile) {
		try {
	        List<String> inputLines = Files.readAllLines(inputFile.toPath());
	        List<String> outputLines = new ArrayList<>();

	        for (String line : inputLines) {
	            if (!line.startsWith("#") && !line.isBlank()) {
	                outputLines.add(line);
	            }
	        }

	        Collections.sort(outputLines);

	        Files.write(outputFile.toPath(), outputLines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// So könnte es mit Java Streams aussehen
	private static void removeCommentsAndSortFile4(File inputFile, File outputFile) {
		try {
	        List<String> inputLines = Files.readAllLines(inputFile.toPath());
			List<String> outputLines = inputLines.stream().filter(line -> !line.startsWith("#") && !line.isBlank())
					.sorted().toList();
	        Files.write(outputFile.toPath(), outputLines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// und noch kürzer
	private static void removeCommentsAndSortFile5(File inputFile, File outputFile) {
		try {
	        Files.write(outputFile.toPath(),
	        		Files.lines(inputFile.toPath()).filter(line -> !line.startsWith("#") && !line.isBlank())
						.sorted().toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
