package org.example.dataAccess;

import org.example.businessLogic.BaseProduct;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class FileWriter {
  public FileWriter(String fileName, String text){
      try {
          java.io.FileWriter fileWriter = new java.io.FileWriter("src/main/resources/" + fileName + ".txt");
          fileWriter.append(text);
          fileWriter.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

}
