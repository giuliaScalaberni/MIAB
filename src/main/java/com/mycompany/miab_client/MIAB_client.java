/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.miab_client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
/**
 *
 * @author Giulia Scalaberni
 */

public class MIAB_client{
    
   
    //SPLIT FILE
     public static List<File> splitFile(File f) throws IOException {
        int partCounter = 0;
        int sizeOfFiles = 1024 * 1024/1000*2;// 2KB
        byte[] buffer = new byte[sizeOfFiles];
         List <File>lista= new ArrayList<>();
        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f))) {
            String name = f.getName();
            int tmp = 0;
            while ((tmp = bis.read(buffer)) > 0) {
                File newFile = new File(f.getParent(), f.getName()+String.valueOf(partCounter++));
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, tmp);
                    lista.add(newFile);
                }
            }
            return lista;
        }
     
     }
     
    public static void uploadFile (String fileName, String filePath) throws FileNotFoundException, IOException, NoSuchAlgorithmException{
    
        //PREPARE FILE SELECTED
        List<File> listaFiles= new ArrayList<>();
        FilePrepare filePrepared=new FilePrepare(filePath);
        listaFiles=filePrepared.prepare();
        
        //CREATION UPLOAD PACKET
        Packet upload= new Packet();
        JSONObject o=upload.setUpload(fileName, filePrepared.getMd5(), filePrepared.getTotPieces());
        System.out.println("UPLOAD "+o.toJSONString());
        Sender s= new Sender ();
        
        s.ackSession(listaFiles,s.uploadSession(o));
     
        try{
                for (int i=0;i<listaFiles.size();i++){
    		File filex = new File(filePath+String.valueOf(i));
    		if(filex.delete()){
    			System.out.println(filex.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
                
                }

    	}catch(Exception e){

    		e.printStackTrace();

    	}
        
        
       

    }

      
      
    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException  {
       
                FileChooser choo=new FileChooser();
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                choo.createAndShowGUI();
                String filename=choo.fc.getName();
                
        
        /*Packet end= new Packet();
        end.setCommand('E');
        end.setOpcode(1);
        end.setChecksum(end);
        JSONArray e=end.getContent();
        System.out.println("End "+e.toJSONString());*/
       
       
   
}
}
