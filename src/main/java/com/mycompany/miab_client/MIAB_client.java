/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.miab_client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Giulia Scalaberni
 */

public class MIAB_client {
     public static List<File> splitFile(File f) throws IOException {
        int partCounter = 1;//I like to name parts from 001, 002, 003, ...
                            //you can change it to 0 if you want 000, 001, ...

        int sizeOfFiles = 1024 * 1024/1000*2;// 2KB
        byte[] buffer = new byte[sizeOfFiles];
         List <File>lista= new ArrayList<>();
        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f))) {//try-with-resources to ensure closing stream
            String name = f.getName();

            int tmp = 0;
            while ((tmp = bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                File newFile = new File(f.getParent(), name + "."
                        + String.format("%03d", partCounter++));
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, tmp);//tmp is chunk size
                    lista.add(newFile);
                }
            }
            return lista;
        }}
     
    public static void mergeFiles(List<File> files, File into)
        throws IOException {
    try (BufferedOutputStream mergingStream = new BufferedOutputStream(
            new FileOutputStream(into))) {
        for (File f : files) {
            Files.copy(f.toPath(), mergingStream);
        }
    }
}
    
    public static void main(String[] args) throws IOException {
        List <File>lista= new ArrayList<>();
        File f= new File("C:\\Users\\istri_000\\Desktop\\stampare.odt") ;
        lista=splitFile(f);
        System.out.println(lista.size());
        packet p1= new packet();
        p1.setCommand('u');
        String buffer=f.getName()+", "+"md5, "+lista.size();
        p1.setBuffer(buffer);
    
   
}
}
