package cyrille.io;

import java.io.File;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 */
public class RenameFiles {

    public static void main(String[] args) {
        try {
            RenameFiles renameFiles = new RenameFiles();
            renameFiles.renameFiles(new File("C:/Programs/eclipse/workspace/PythonStoreDB/webApplication"), "_fr.jsp", "_nls.jsp");
        } catch (Exception e) {
        }
    }

    public void renameFiles(File folder, String searchedFileEnd, String newFileEnd) throws Exception {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Given File must be a folder. '" + folder.getAbsolutePath() + "' is NOT correct");
        }
        System.out.println("#FOLDER " + folder.getAbsolutePath());
        File[] files = folder.listFiles();
        for (File element : files) {
            if (element.isDirectory()) {
                renameFiles(element, searchedFileEnd, newFileEnd);
            } else {
                if (element.getName().endsWith(searchedFileEnd)) {
                    String newFilePath = element.getAbsolutePath().substring(0,
                            element.getAbsolutePath().length() - searchedFileEnd.length())
                            + newFileEnd;
                    boolean result = element.renameTo(new File(newFilePath));
                    if (result) {
                        System.out.println("\t" + element.getAbsolutePath() + "->" + newFilePath);
                    } else {
                        System.err.println("ERROR" + element.getAbsolutePath() + "->" + newFilePath);
                    }
                } else if (element.getName().endsWith(".jsp")) {
                    System.err.println("SKIP " + element.getName());
                }
            }
        }
    }
}
