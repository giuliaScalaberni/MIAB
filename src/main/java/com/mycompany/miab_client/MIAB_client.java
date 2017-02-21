/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.miab_client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
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
    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        List <File>lista= new ArrayList<>();
        File f= new File("C:\\Users\\istri_000\\Desktop\\stampare.odt") ;
        lista=splitFile(f);
        //md5
        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(f);
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
          md.update(dataBytes, 0, nread);
        };
        byte[] mdbytes = md.digest();
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        String tot="";
        packet upload= new packet();
        JSONObject o=upload.setUpload(f.getName(), sb.toString(), lista.size());
      
        List<File> file=new ArrayList<>();
        System.out.println("UPLOAD "+o.toJSONString());
        int op;
        List<File> listaFinale= new ArrayList<>();
 
        for (int i=0; i<lista.size();i++){
            packet p=new packet ();
            
            p.setCommand("S");
            p.setOpcode(String.valueOf(i+1));
            //Base64.getEncoder().encodeToString();
            byte[] bytesArray = new byte[(int) lista.get(i).length()];
            FileInputStream fis2 = new FileInputStream(lista.get(i));
            fis2.read(bytesArray); //read file into bytes[]
            fis2.close();
            p.setBuffer(bytesArray);
            p.setLen_buffer(p.getBuffer());
            p.setChecksum(p);
            JSONObject b=p.getContent();
          
            System.out.println("SEND"+i+b.toJSONString());
            File f2 = new File(String.valueOf(i));
            FileOutputStream fo= new FileOutputStream (f2);
            fo.write(p.getBuffer());
            fo.close();
            listaFinale.add(f2);
        }
        
        mergeFiles(listaFinale, new File ("file.odt"));
        try{
                for (int i=0;i<lista.size();i++){
    		File filex = new File(String.valueOf(i));
    		if(filex.delete()){
    			System.out.println(filex.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
                
                }

    	}catch(Exception e){

    		e.printStackTrace();

    	}

        /*packet end= new packet();
        end.setCommand('E');
        end.setOpcode(1);
        end.setChecksum(end);
        JSONArray e=end.getContent();
        System.out.println("End "+e.toJSONString());*/
       
       
   
}
}
