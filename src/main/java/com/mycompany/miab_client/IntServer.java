/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.miab_client;

import java.io.File;
import java.net.Socket;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author Giulia Scalaberni
 */
public interface IntServer {
    public void ackSession(List<File> listaFiles,Socket clientSocket);
    public Socket uploadSession(JSONObject o);
    
}
