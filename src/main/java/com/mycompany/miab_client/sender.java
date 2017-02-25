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
import org.json.simple.JSONObject;

/**
 *
 * @author Giulia Scalaberni
 */
public class sender implements senderInt{
    public sender (){
    
    }
    public void startSession(List<File> listaFiles){
           List<File> listaFinale= new ArrayList<>();
            try{
           Socket clientSocket= new Socket ("localhost", 7777);
       
        
      for (int i=0; i<listaFiles.size();i++){
            
          FileInputStream fis2=null;
            
           
        
                DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
                byte[] bytesArray = new byte[(int) listaFiles.get(i).length()];
                fis2 = new FileInputStream(listaFiles.get(i));
                fis2.read(bytesArray);
                fis2.close();
                
                //CREATE THE SEND PACKETs
                packet p=new packet ();
                p.setCommand("S");
                p.setOpcode(String.valueOf(i+1));
                p.setBuffer(bytesArray);
                p.setLen_buffer(p.getBuffer());
                p.setChecksum(p);
                JSONObject b=p.getContent();
                System.out.println("SEND"+i+b.toJSONString());
                File f2 = new File(String.valueOf(i));
                FileOutputStream fo= new FileOutputStream (f2);
                
                    //invio dati
                    outToServer.writeBytes(b.toJSONString()+"\n");
                    outToServer.flush();
                    System.out.println(outToServer);
                    System.out.println("File Sent!");
                    
                    listaFinale.add(f2);
                    System.out.println(listaFinale.size());
                    InputStream is2 = clientSocket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is2);
                    BufferedReader br = new BufferedReader(isr);
                    String message = br.readLine();
                    System.out.println("Message received from the server : " +message);
                    
                }  
                    
                } catch (FileNotFoundException e) {
                    System.out.println("FILE NOT FOUND" + e);
                } catch (IOException ioe) {
                    System.out.println("Exception while reading the FILE " + ioe);
                }    
      
        }

    
    
}
