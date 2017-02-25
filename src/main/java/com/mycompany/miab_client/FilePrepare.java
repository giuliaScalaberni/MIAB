/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.miab_client;

import static com.mycompany.miab_client.MIAB_client.splitFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author Giulia Scalaberni
 */
public class FilePrepare {
    private String fileName;
    private String md5;
    private int totPieces;
    
    public FilePrepare(String name){
            this.fileName=name;
            this.md5="";
            this.totPieces=0;
           
    }
    
    public String getMd5(){
        return this.md5;
    } 
    public int getTotPieces(){
        return this.totPieces;
    }
    
    public List<File> prepare () throws IOException, NoSuchAlgorithmException{
        List <File>listaFiles= new ArrayList<>();
        System.out.println(this.fileName);
        File fileToUpload= new File(this.fileName) ;
        listaFiles=splitFile(fileToUpload);
        //md5 for file
        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(fileToUpload);
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
          md.update(dataBytes, 0, nread);
        };
        byte[] mdbytes = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        this.md5=sb.toString();
        this.totPieces=listaFiles.size();
        
        return listaFiles;
        
    }
}
