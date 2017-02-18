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
public interface packetInt {
    public void setCommand(char command);
    public void setOpcode(int opcode);
    public void setLen_buffer(String buffer);
    public void setBuffer(String buffer);
    public void setChecksum(packet p);
    public void getContent(packet p);
}
