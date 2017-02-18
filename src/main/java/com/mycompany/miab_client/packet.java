/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.miab_client;

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
    }

    public packet(char command, int opcode,  String buffer, byte checksum) {
        this.command = command;
        this.opcode = opcode;
        this.len_buffer=(short)this.buffer.getBytes().length;
        this.buffer = buffer;
        this.checksum = checksum;
    }
    

    public void setBuffer(String buffer) {
        //if (buffer.getBytes().length<=2000)
            this.buffer = buffer;
        
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

    public void setLen_buffer(String buffer) {
        this.len_buffer=(short)this.buffer.getBytes().length;
    }

    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }
    
     
    
}
 



