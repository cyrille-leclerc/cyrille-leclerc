package cyrille.util.zip;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TestCreateZip {

    public static void main(String[] args) {
        System.out.println("Example of ZIP file creation.");

        // Specify files to be zipped
        String[] filesToZip = new String[3];
        filesToZip[0] = "firstfile.txt";
        filesToZip[1] = "secondfile.txt";
        filesToZip[2] = "temp\thirdfile.txt";

        byte[] buffer = new byte[18024];

        // Specify zip file name
        String zipFileName = "c:\\example.zip";

        try {

            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));

            // Set the compression ratio
            out.setLevel(Deflater.DEFAULT_COMPRESSION);

            // iterate through the array of files, adding each to the zip file
            for (int i = 0; i < filesToZip.length; i++) {
                System.out.println(i);
                // Associate a file input stream for the current file
                FileInputStream in = new FileInputStream(filesToZip[i]);

                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(filesToZip[i]));

                // Transfer bytes from the current file to the ZIP file
                // out.write(buffer, 0, in.read(buffer));

                int len;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }

                // Close the current entry
                out.closeEntry();

                // Close the current file input stream
                in.close();

            }
            // Close the ZipOutPutStream
            out.close();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}