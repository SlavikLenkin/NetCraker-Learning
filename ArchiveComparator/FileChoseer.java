package com.company.utils2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChoseer {
    public static String[] filePath() {
        String[] filePaths = new String[2];
        filePaths[0] = JFileChooser();
        filePaths[1] = JFileChooser();
        return filePaths;
    }

    public static String JFileChooser() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("zip,jar","zip","jar");
        JFileChooser file = new JFileChooser();
        file.setFileFilter(filter);
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            java.io.File f = file.getSelectedFile();
            return f.getPath();
        }
        return "err";
    }

}
