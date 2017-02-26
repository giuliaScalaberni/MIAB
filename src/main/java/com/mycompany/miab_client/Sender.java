/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.miab_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Giulia Scalaberni
 */
public class Sender implements IntServer{
    public Sender (){
    
    }
    public Socket uploadSession(JSONObject o){
        try {
            Socket clientSocket= new Socket ("localhost", 7777);
            DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("UPLOAD"+o.toJSONString());
            //UPLOAD TO SERVER
            outToServer.writeBytes(o.toJSONString()+"\n");
            outToServer.flush();
            //WAITING FOR SERVER RESPONSE
            InputStream is2 = clientSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is2);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            //OUT SERVER MESSAGE
            System.out.println("Message received from the server : " +message);
            return clientSocket;    
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public void ackSession(List<File> listaFiles,Socket clientSocket){
        List<File> listaFinale= new ArrayList<>();
        try{
            
            //FOR EACH PIECE OF FILE
            for (int i=0; i<listaFiles.size();i++){
                //GET THE FILE PIECE IN BYTE ARRAY
                DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
                byte[] bytesArray = new byte[(int) listaFiles.get(i).length()];
                FileInputStream fis2 = new FileInputStream(listaFiles.get(i));
                fis2.read(bytesArray);
                fis2.close();
                //CREATE THE SEND PACKET
                Packet p=new Packet ();
                p.setCommand("S");
                p.setOpcode(i+1);
                p.setBuffer(bytesArray);
                p.setLen_buffer(p.getBuffer());
                p.setChecksum(p);
                //PACKET TO JSON
                JSONObject b=p.getContent();
                //OUTPUT PAKCET
                System.out.println("SEND"+i+b.toJSONString());
                //SEND TO SERVER
                outToServer.writeBytes(b.toJSONString()+"\n");
                outToServer.flush();
                System.out.println("PIECE "+i+" sent!");
                //WAITING FOR SERVER RESPONSE
                InputStream is2 = clientSocket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is2);
                BufferedReader br = new BufferedReader(isr);
                String message = br.readLine();
                //OUT SERVER MESSAGE
                System.out.println("Message received from the server : " +message);
                
            }  
                    
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the FILE " + ioe);
        }    
      
    }
}
