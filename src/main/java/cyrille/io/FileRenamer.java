package cyrille.io;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

public class FileRenamer {
    
    public static void main(String[] args) {
        new FileRenamer().scan();
    }
    
    public void scan() {
        String baseFolderName = "C:\\Documents and Settings\\Cyrille Le Clerc\\Mes documents\\Musique a convertir";
        System.out.println("enter " + baseFolderName);
        File baseFolder = new File(baseFolderName);
        scan(baseFolder);
    }
    
    private void scan(File baseFolder) {
        Validate.isTrue(baseFolder.exists() && baseFolder.isDirectory(), "folder exists " + baseFolder);
        
        File[] children = baseFolder.listFiles();
        for (File file : children) {
            if (file.isDirectory()) {
                scan(file);
            } else if (StringUtils.endsWith(file.getName(), ".wav")) {
                String fileName = file.getName();
                String trackNumber = StringUtils.substringBefore(fileName, " ");
                
                String trackName = StringUtils.substringBeforeLast(StringUtils.substringAfter(fileName, " "), ".");
                
                String album = file.getParentFile().getName();
                String artist = file.getParentFile().getParentFile().getName();
                
                String newFileName = artist + "_" + album + "_" + trackNumber + "_" + trackName + ".wav";
                //System.out.println(newFileName);
                File targetFile = new File(file.getParent(), newFileName);
                System.out.println(targetFile);
                file.renameTo(targetFile);
            } else {
                System.out.println("skip unsupported " + file);
            }
        }
    }
}
