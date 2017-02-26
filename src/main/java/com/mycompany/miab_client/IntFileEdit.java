/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.miab_client;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Giulia Scalaberni
 */
public interface IntFileEdit {
    public  List<File> splitFile(File f)throws IOException;
    public  void mergeFiles(List<File> files, File into) throws IOException;
    
}
