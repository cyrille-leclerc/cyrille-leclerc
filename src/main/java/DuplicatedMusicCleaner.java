import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/*
 * Copyright 2008-2009 Xebia and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class DuplicatedMusicCleaner {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/cyrilleleclerc/Music/iTunes/iTunes Music");
        cleanupFolder(file);
    }

    public static void cleanupFolder(File folder) throws IOException {
        // System.out.println("Evaluate " + folder.getAbsolutePath());
        String[] fileNames = folder.list();
        Map<String, String> fileNamesWithoutExtension = new HashMap<String, String>();
        for (String fileName : fileNames) {
            File file = new File(folder, fileName);
            String nameWithoutExtension = StringUtils.substringBeforeLast(file.getName(), ".");
            fileNamesWithoutExtension.put(nameWithoutExtension, file.getAbsolutePath());
        }

        for (String fileName : fileNames) {
            // System.out.println("Evaluate " + fileName);
            File file = new File(folder, fileName);
            if (file.isDirectory()) {
                cleanupFolder(file);
            } else {

                String nameWithoutExtension = StringUtils.substringBeforeLast(file.getName(), ".");
                String suffix = " 1";
                if (nameWithoutExtension.endsWith(suffix)) {
                    String nameWithoutSuffix = StringUtils.substringBeforeLast(nameWithoutExtension, suffix);
                    if (fileNamesWithoutExtension.keySet().contains(nameWithoutSuffix)) {
                        File originalFile = new File(fileNamesWithoutExtension.get(nameWithoutSuffix));
                        long originalFileLength = originalFile.length();
                        long fileLength = file.length();
                        if (originalFileLength == fileLength) {
                            System.err.println("Delete " + file.getName());
                            FileUtils.deleteQuietly(file);
                            // FileUtils.moveFile(file, new
                            // File(file.getAbsoluteFile() + ".todelete"));
                        }
                    }
                }
                /*
                 * if(file.getName().endsWith(".todelete")) { String newFileName
                 * = StringUtils.substringBeforeLast(file.getName(),
                 * ".todelete"); File parent = file.getParentFile(); File
                 * parentParent = parent.getParentFile(); newFileName =
                 * parentParent.getName() + "_" + parent.getName() + "_" +
                 * newFileName;
                 * 
                 * File target = new File("/Users/cyrilleleclerc/todelete",
                 * newFileName); System.out.println(target);
                 * FileUtils.moveFile(file, target);
                 * 
                 * }
                 */
            }
        }

    }
}
