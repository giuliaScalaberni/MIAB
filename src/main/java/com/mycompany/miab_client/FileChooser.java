/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.miab_client;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
 
/*
 * FileChooser.java uses these files:
 *   images/Open16.gif
 *   images/Save16.gif
 */
public class FileChooser extends JPanel implements ActionListener {
    
    static private final String newline = "\n";
    JButton openButton, startButton;
    JTextArea log;
    JFileChooser fc;
 
    public FileChooser() {
        super(new BorderLayout());
 
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
 
        //Create a file chooser
        fc = new JFileChooser();
        openButton = new JButton("Open a File...");
        openButton.addActionListener(this);
        startButton= new JButton("Start upload...");
        startButton.addActionListener(this);
        JPanel buttonPanel = new JPanel(); 
        buttonPanel.add(openButton);
        buttonPanel.add(startButton);
        startButton.setVisible(false);
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }
 
    public void actionPerformed(ActionEvent e) {
 
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(FileChooser.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                log.setText("File Selected:" + file.getName() );
                startButton.setVisible(true);
            } 
            else {
                log.setText("Open command cancelled by user." + newline);
                startButton.setVisible(false);
            }
            log.setCaretPosition(log.getDocument().getLength());
           
        }
        else if (e.getSource() == startButton) {
            File file=fc.getSelectedFile();
            try {
                MIAB_client.uploadFile(file.getName(), file.getAbsolutePath());
            } 
            catch (IOException ex) {
                Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE, null, ex);
            }
               
        
    }}
 
   
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new FileChooser());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}