package com.company.utils2;

public class main {
    public static void main(String[] args) {
        ArchiveComparator ac = new ArchiveComparatorImpl();
        String[] filePaths = FileChoseer.filePath();
        ac.createReport(filePaths[0], filePaths[1]);


    }
}