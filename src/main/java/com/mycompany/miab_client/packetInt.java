/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.miab_client;

import java.io.File;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Giulia Scalaberni
 */
public interface packetInt {
    public void setCommand(char command);
    public void setOpcode(int opcode);
    public void setLen_buffer(String buffer);
    public void setBuffer(String buffer);
    public String getBuffer();
    public void setBuffer(File buffer);
    public void setChecksum(packet p);
    public JSONArray getContent();
}
