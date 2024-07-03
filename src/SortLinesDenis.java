import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SortLinesDenis {

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
        removeCommentsAndSortFile(inputFile, outputFile2);
        removeCommentsAndSortFile(inputFile, outputFile3);
        removeCommentsAndSortFile(inputFile, outputFile4);
        removeCommentsAndSortFile(inputFile, outputFile5);

        // Und hier noch eine Zusatzaufgabe für Dich: Befülle diese Methode mit Code
        checkContentEquals(outputFile, outputFile2, outputFile3, outputFile4, outputFile5);
        checkContentEquals2(outputFile, outputFile2, outputFile3, outputFile4, outputFile5);
        checkContentEquals3(outputFile, outputFile2, outputFile3, outputFile4, outputFile5);
    }

    private static void checkContentEquals(File... files) {
        boolean filesAreEqual = true;

        try {
            for (int i = 0; i < files.length - 1; i++) {
                String contentI = new String(Files.readAllBytes(files[i].toPath()), StandardCharsets.UTF_8);

                for (int j = i + 1; j < files.length; j++) {
                    String contentJ = new String(Files.readAllBytes(files[j].toPath()), StandardCharsets.UTF_8);

                    if (!contentI.equals(contentJ)) {
                        filesAreEqual = false;
                        break;
                    }
                }

                if (!filesAreEqual) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Die Dateien haben " + (filesAreEqual ? "" : "NICHT ") + "den gleichen Inhalt");
    }

    private static void checkContentEquals2(File... files) {
        boolean filesAreEqual = true;
        String firstFile = "";

        try {
            for (int i = 0; i < files.length; i++) {
                String content = new String(Files.readAllBytes(files[i].toPath()));

                if (i == 0) {
                    firstFile = content;
                } else if (!content.equals(firstFile)) {
                    filesAreEqual = false;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Die Dateien haben " + (filesAreEqual ? "" : "NICHT ") + "den gleichen Inhalt");
    }


    private static void checkContentEquals3(File... files) {
        try {
            List<List<String>> fileInhalt = new ArrayList<>();

            for (File file : files) {
                List<String> lines = Files.readAllLines(file.toPath());
                fileInhalt.add(lines);
            }

            boolean filesAreEqual = fileInhalt.stream().distinct().count() == 1;

            System.out.println("Die Dateien haben " + (filesAreEqual ? "" : "NICHT ") + "den gleichen Inhalt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void removeCommentsAndSortFile(File inputFile, File outputFile) {
        try {
            Files.write(outputFile.toPath(),
                    Files.lines(inputFile.toPath()).filter(line -> !line.startsWith("#") && !line.isBlank())
                            .sorted().toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
