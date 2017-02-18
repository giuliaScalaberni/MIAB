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
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Giulia Scalaberni
 */
public class packet implements packetInt{
    private char command;
    private int opcode;
    private short len_buffer;
    private String buffer;
    private byte checksum;

    public packet() {
        this.command=0;
        this.buffer="";
        this.checksum=0;
        this.len_buffer=0;
        this.opcode=0;
    }

    public packet(char command, int opcode,  String buffer, byte checksum) {
        this.command = command;
        this.opcode = opcode;
        this.len_buffer=(short)this.buffer.getBytes().length;
        this.buffer = buffer;
        this.checksum = checksum;
    }

    @Override
    public void setBuffer(File buffer) {
        
    String line = "";

    try {
        BufferedReader br = new BufferedReader(new FileReader(buffer));

        while (br.readLine() != null) {
          line+=br.readLine();

          if (br == null) {
              
              // close reader when all data is read
              br.close();
          }
        }}
    catch (FileNotFoundException e) {
        e.getMessage();
    } catch (IOException e) {
        e.printStackTrace();
    }
    this.buffer=line;
}

    public String getBuffer() {
        return buffer;
    }
    

    public void setBuffer(String buffer) {
        //if (buffer.getBytes().length<=2000)
            this.buffer = buffer;
            
        
    }
    public void setLen_buffer(String buffer){
        this.len_buffer=(short)this.buffer.getBytes().length;
    }

    public void setCommand(char command) {
        this.command = command;
    }

    public void setChecksum(packet p) {
        String  message="";
        message+=p.command;
        message+=p.opcode;
        message+=p.len_buffer;
        message+=p.buffer;
        int chksm=0;
        for ( int i=0 ; i < message.length()-1 ; i++ ) 
        { 
         chksm +=(message.charAt(i)^message.charAt(i+1));
        } 
        this.checksum = (byte)chksm;
    }

    
    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }
    
    public JSONArray getContent(){
        //scrittura dati nel JSON
            JSONArray arr= new JSONArray ();
            
            arr.add(this.command);
            arr.add(this.opcode);
            arr.add(this.len_buffer);
            arr.add(this.buffer);
            arr.add(this.checksum);
            return arr;
    }
    }
     
    

 



