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
public interface IntPacket {
    public void setCommand(String command);
    public void setOpcode(String opcode);
    public void setLen_buffer(byte[] buffer);
    public void setBuffer(byte[] buffer);
    public byte[] getBuffer();
    public void setChecksum(Packet p);
    public JSONObject getContent();
    public JSONObject setUpload(String filename,String x, int nTot);
}
