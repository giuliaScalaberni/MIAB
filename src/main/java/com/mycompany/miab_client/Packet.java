/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.miab_client;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Giulia Scalaberni
 */
public class Packet implements IntPacket{
    private String command;
    private String opCode;
    private int bufferLenght;
    private byte [] buffer;
    private byte checksum;

    public Packet() {
        this.command="";
        this.opCode="";
        this.checksum=0;
        this.bufferLenght=0;
        this.opCode="";
    }

    public Packet(String command, String opcode,  byte[] buffer, byte checksum) {
        this.command = command;
        this.opCode = opcode;
        this.bufferLenght=buffer.length;
        this.buffer = buffer;
        this.checksum = checksum;
    }
   

  

    public byte[] getBuffer() {
        return buffer;
    }
    

    public void setLen_buffer(byte[] buffer){
        this.bufferLenght=this.buffer.length;
    }
    
    public void setBuffer(byte[] buffer){
        this.buffer=buffer;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setChecksum(Packet p) {
        String  message="";
        message+=p.command;
        message+=p.opCode;
        message+=p.bufferLenght;
        message+=p.buffer;
        int chksm=0;
        for ( int i=0 ; i < message.length()-1 ; i++ ) 
        { 
         chksm +=(message.charAt(i)^message.charAt(i+1));
        } 
        this.checksum = (byte)chksm;
    }

    
    public void setOpcode(String opcode) {
        this.opCode = opcode;
    }
    
    public JSONObject getContent(){
        //scrittura dati nel JSON
            JSONObject obj= new JSONObject ();
   
            obj.put("command",this.command);
            obj.put("opCode",String.valueOf(this.opCode));
            
            obj.put("bufferLenght",String.valueOf(this.bufferLenght));
            obj.put("buffer",Base64.getEncoder().encodeToString(this.buffer));
            
            
            obj.put("checksum",String.valueOf(this.checksum));
            return obj;
    }
    
    public JSONObject setUpload(String filename,String x, int nTot){
    //scrittura dati nel JSON
            JSONObject obj= new JSONObject ();
            obj.put("command","U");
            obj.put("opCode",nTot);
            JSONArray buffer= new JSONArray ();
            JSONObject objName= new JSONObject ();
            JSONObject objMD5= new JSONObject ();
            objName.put("filename", filename);
            objMD5.put("md5",x);
            buffer.add(objName);
            buffer.add(objMD5);
            
            obj.put("bufferLenght",String.valueOf(buffer.size()));
            obj.put("buffer", buffer);
            this.setChecksum(this);
            obj.put("checksum",String.valueOf(this.checksum));
            return obj;
    }
    }
     
    

 



