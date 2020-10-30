package com.company.utils2;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ArchiveComparatorImpl implements ArchiveComparator{
    private static final String reportFpath = ".\\report.txt";
    private ArrayList<ArrayList<ZipEntry>> Files;
    private String[] fileNames;

    private void loadFiles(String filePath1, String filePath2) {
        Files = new ArrayList<>();
        Files.add(new ArrayList<>());
        Files.add(new ArrayList<>());
        fileNames = new String[2];
        List<File> files = new ArrayList<>();
        files.add(new File(filePath1));
        files.add(new File(filePath2));
        int FilesInd = 0;
        try {
            for (File file : files) {
                InputStream input = new FileInputStream(file);
                ZipInputStream zip = new ZipInputStream(input);
                ZipEntry entry = zip.   getNextEntry();
                fileNames[FilesInd] = file.getName();
                while (entry != null) {
                    Files.get(FilesInd).add(entry);
                    entry = zip.getNextEntry();
                }
                FilesInd++;
                zip.close();
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void createReport(String filePath1, String filePath2) {
        loadFiles(filePath1, filePath2);
        String header = String.format("%15s | %15s \r\n", fileNames[0], fileNames[1]);
        try {
            OutputStream output = new FileOutputStream(reportFpath);
            output.write(header.getBytes());
            output.write("----------------|--------------\r\n".getBytes());
            saveRepToFile(output);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveRepToFile(OutputStream output) {
        ArrayList<ZipEntry> arr1 = Files.get(0);
        ArrayList<ZipEntry> arr2 = Files.get(1);
        ArrayList<Integer> arr2IndexesContains = new ArrayList<>();
        ArrayList<Integer> arr2Indexes = new ArrayList<>(Files.get(1).size());
        for (int i = 0; i < Files.get(1).size(); i++) {
            arr2Indexes.add(i);
        }
        for (int i = 0; i < arr1.size(); i++) {
            boolean found = false;
            for (int j = 0; j < arr2.size(); j++) {
                if (arr1.get(i).getName().equals(arr2.get(j).getName())) {
                    if (arr1.get(i).getSize() != arr2.get(j).getSize()) {
                        updateFilesWriteRep(i, j, output);
                    }
                    found = true;
                    if (!arr2IndexesContains.contains(j)) {
                        arr2IndexesContains.add(j);
                    }
                } else if (arr1.get(i).getSize() == arr2.get(j).getSize()) {
                    if (!arr1.get(i).getName().equals(arr2.get(j).getName())) {
                        renamedFilesWriteRep(i, j, output);
                    }
                    found = true;
                    if (!arr2IndexesContains.contains(j)) {
                        arr2IndexesContains.add(j);
                    }
                }
            }
            if (!found) {
                deletedFilesWriteRep(i, output);
            }
        }
        newFilesWriteRep(arr2Indexes, arr2IndexesContains, output);
    }

    private void newFilesWriteRep(ArrayList<Integer> arr2Indexes, ArrayList<Integer> arr2IndexesContains, OutputStream output) {
        for (Integer arr2IndexesContain : arr2IndexesContains) {
            arr2Indexes.remove(arr2IndexesContain);
        }
        try {
            for (int i : arr2Indexes) {
                String toWrite = String.format("%15s | %15s\r\n", "", "+ newFile " + Files.get(1).get(i).getName());
                output.write(toWrite.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFilesWriteRep(int i, int j, OutputStream output) {
        String toWrite = String.format("%15s | %15s\r\n", "* updated " +
                Files.get(0).get(i).getName(), "* updated " + Files.get(1).get(j).getName());
        try {
            output.write(toWrite.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renamedFilesWriteRep(int i, int j, OutputStream output) {
        String toWrite = String.format("%15s | %15s\r\n", "? renamed " +
                Files.get(0).get(i).getName(), "? renamed " + Files.get(0).get(j).getName());
        try {
            output.write(toWrite.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deletedFilesWriteRep(int i, OutputStream output) {
        String toWrite = String.format("%15s | %15s\r\n", "- deleted " +
                Files.get(0).get(i).getName(), "");
        try {
            output.write(toWrite.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}