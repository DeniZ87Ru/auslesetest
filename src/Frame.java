import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

public class Frame {
    JFrame frame;
    JLabel label;

    public Frame() {

        frame = new JFrame();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label = new JLabel("Ziehe hier eine beliebige .txt datei rein", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(label);
        frame.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                evt.acceptDrop(DnDConstants.ACTION_COPY);

                try {
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        removeCommentsAndSortFile(file);
                    }
                    checkContentEquals3(droppedFiles.toArray(new File[0]));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        frame.setVisible(true);
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

    private static void removeCommentsAndSortFile(File inputFile) {
        try {
            File tempFile = File.createTempFile("sorted", ".txt");
            Files.write(tempFile.toPath(),
                    Files.lines(inputFile.toPath()).filter(line -> !line.startsWith("#") && !line.isBlank())
                            .sorted().toList());
            System.out.println("Die sortierte Datei ohne Kommentare wurde in " + tempFile.getAbsolutePath() + " geschrieben.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
