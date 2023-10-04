/*
5. domača naloga


*/

// package csv;
import java.util.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*; // Zaradi branja iz tipkovnice in iz/v datoteke
//import java.util.List;
//import java.util.Array;
//import java.util.ArrayList;


class SeoKWCleaner{
    
  private String cvsSplitBy = ",";
  private int lines;
  private String fileIn;
  private ArrayList<ArrayList<String>> rows;
  private ArrayList<ArrayList<String>> deleted;

    
  public SeoKWCleaner(String file){

      this.fileIn = file;
      this.deleted = new ArrayList<ArrayList<String>>();
      this.rows = new ArrayList<ArrayList<String>>();

      this.readCsvFile();
  }

    
  public int getUserNextStep(){

    System.out.println("\n------------------------------------------------------\nPlease enter next step:\n");
    System.out.println(" 0 - End program");
    System.out.println(" 1 - Check imported");
    System.out.println(" 2 - Remove duplicate lines");
    System.out.println(" 3 - Find all keywords");
    System.out.println(" 4 - Remove lines with keywords");
    System.out.println(" 5 - Undelete last");
    System.out.println(" 6 - Save file\n");

    Scanner userInputReader = new Scanner(System.in);

    System.out.print("Enter selection[]: ");
    int userIntInput = 10;
    String userInput;
    // ponavljamo, dokler ni pravilen vnos
    while(userIntInput>6){

      try{

         userInput = userInputReader.nextLine();  // Read user input
         userIntInput = Integer.parseInt(userInput);

         if(userIntInput>4){
           System.out.println("Please repeat your selection: ");
         }

       }catch(Exception e){
         userIntInput = 10;
         System.out.println("Please repeat your selection: ");
       }
    }
    switch(userIntInput){

      case 1:
        this.checkArray();
        break;
      case 2:
        this.removeDuplicateLines();
        break;
      case 3:
        this.findKeywords();
        break;
      case 4:
        this.removeRowsWithKeyword();
        break;
      case 5:
        this.undeleteRows();
        break;
      case 6:
        this.saveCSVFile();
        break;
    }

    return userIntInput;

  }

    
  private void readCsvFile(){

    BufferedReader br = null;
    String line = "";

    try{
        
        this.lines = 0;
        System.out.println("Opening file:   " + this.fileIn);
        br = new BufferedReader(new FileReader(this.fileIn));
        
        while ((line = br.readLine()) != null) {
               // use comma as separator
               try{
                 String[] seoLine = line.split(cvsSplitBy);

                 if(seoLine.length>0 && seoLine[0].length()>3){
                     
                   System.out.println("Length: " + line.length() + ", fields: " + seoLine[0].length() +" : " + line);
                   ArrayList<String> myList = new ArrayList<String>(Arrays.asList(seoLine));
                     
                   this.rows.add(myList);
                   this.lines++;
                 }
                   
              } catch(Exception e){}
               // System.out.print(this.lines + " ");
        }
        
        System.out.println("------------------------------------------------------");
        System.out.println("CSV contains  " + this.rows.size() + " lines.");
        
    }catch (FileNotFoundException e) {
        e.printStackTrace();
    }catch (IOException e) {
        e.printStackTrace();
    }finally{

      if (br != null) {
          
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
          
      }
    }
  }

  private void saveCSVFile(){

    int dot = this.fileIn.lastIndexOf('.');
    String newFileNameBase = (dot == -1) ? this.fileIn : this.fileIn.substring(0, dot);
    String newFileExt = (dot == -1) ? ".csv" : this.fileIn.substring(dot+1);

    newFileNameBase += "_cleaned";
    
    int k=0;
    String add ="";
    String newFileFullName = "";
    Boolean exists = true;
      
    while(exists){
        
          newFileFullName = newFileNameBase + add + "." + newFileExt;
          File f = new File(newFileFullName);
          exists = f.exists();
          k++;
          add ="_" + k;
        
    }

    System.out.println("Writing content to file: " + newFileFullName);

    FileWriter fw = null;
    PrintWriter cswWriter =null;
      
    try{

        fw = new FileWriter (newFileFullName);
        cswWriter = new PrintWriter(fw);
        
        for (int i=0; i<this.rows.size(); i++){
          
              ArrayList<String> myRow = this.rows.get(i);
              String line ="";

              for (int j=0; j<myRow.size(); j++){

                    line +=  myRow.get(j) + this.cvsSplitBy;

              }
          
          cswWriter.println (line);
        }

    }catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }finally{

        if (cswWriter != null) {
          //try {
            cswWriter.close();
          //} catch (IOException e) {
          //  e.printStackTrace();
        //  }
        }
    }
  }




 private void checkArray(){
     System.out.println("Imported file:   " + this.fileIn);
     System.out.println("Rows in table:   " + this.rows.size());

     for (int i=0; i<this.rows.size(); i++){
         ArrayList<String> myRow = this.rows.get(i);
         System.out.println(i + " " + myRow.get(0));
      }
     
      System.out.println("------------------------------------------------------");
      System.out.println("Rows in table:   " + this.rows.size());
     
 }

 private void undeleteRows(){

    int n = 0;

    for (int i=0; i<this.deleted.size(); i++){

        ArrayList<String> myUndeletedRow = this.deleted.get(i);
        String undeletedLine= myUndeletedRow.get(0);

        for (int k=1; k<myUndeletedRow.size(); k++){

            undeletedLine += ", " + myUndeletedRow.get(k);
        }
        
        System.out.println("+undeleted row: " + undeletedLine);
        this.rows.add(myUndeletedRow); // this.deleted.get(i));
        n++;
    }
     
    System.out.println("------------------------------------------------------");
    System.out.println( n + " rows undeleted.");
    System.out.println("New rows count: " + this.rows.size());
    this.deleted.clear();
 }
    

 private void removeRowsWithKeyword(){

     int removed =0;

     System.out.print("Enter keyword you want to remove from: ");
     Scanner userInputReader = new Scanner(System.in);
     String userInput = userInputReader.nextLine();
     
     userInput = userInput.trim();
     this.deleted.clear();
     System.out.println();

    for (int i=1; i<this.rows.size(); i++){
        
        ArrayList<String> myList = this.rows.get(i);
        String currentRow = myList.get(0);

        if(currentRow.contains(userInput)){
            //ArrayList<String> myDeletedRow = this.rows.get(i);
            this.deleted.add(myList); //myDeletedRow);
            System.out.println("-removed"+currentRow.contains(userInput)+": " + currentRow);
            this.rows.remove(i);
            removed++;
            i--;
        }
    }
     
    System.out.println("------------------------------------------------------");
    System.out.println("Items removed: " + removed);
    // System.out.println("Rows for undelete: " + this.deleted.size());
    System.out.println("New rows count: " + this.rows.size());

 }


  private void removeDuplicateLines(){

      int removed =0;
      System.out.println("Removing duplicates.");

      for (int i=1; i<this.rows.size(); i++){
          
          ArrayList<String> myList = this.rows.get(i);
          String currentRow = myList.get(0);

          for (int j=i+1; j<this.rows.size(); j++){

              ArrayList<String> myList2 = this.rows.get(j);
              if(currentRow.compareTo(myList2.get(0))==0 ){
                  
                  String removedLine= myList2.get(0);
                  
                  for (int k=1; k<myList2.size(); k++){
                    removedLine += ", " + myList2.get(k);
                  }
                  
                  System.out.println("-removed: " + removedLine);
                  this.rows.remove(j);
                  removed++;
                  j--;
              }
          }
      }
      
      System.out.println("------------------------------------------------------");
      System.out.println("Items removed: " + removed);
      System.out.println("New rows count: " + this.rows.size());
      
  }

    
    private void findKeywords(){

        ArrayList<String> keywords = new ArrayList<String>();
        int kwCounter = 0;
        for (int i=1; i<this.rows.size(); i++){

            ArrayList<String> myList = this.rows.get(i);
            String currentRow = myList.get(0);
            String[] rowKw = currentRow.split(" ");

            for(int j = 0; j<rowKw.length;j++){

                boolean ans = keywords.contains(rowKw[j]);

                if(!ans){
                    keywords.add(rowKw[j]);
                    kwCounter++;
                }
            }
        }

        System.out.println("Keywords found: " + kwCounter);

        // Sort keywords
        Collections.sort(keywords);

        for (int i=1; i<keywords.size(); i++){
            
            System.out.print(keywords.get(i) + " ");
        }
        
        System.out.println("");

    }


  public static void main (String[] args){

    System.out.println ("SEO keyword filter tool");

    String fileIn = args[0];
    SeoKWCleaner s = new SeoKWCleaner(fileIn);

    while(s.getUserNextStep() > 0){}

  }
    
}
