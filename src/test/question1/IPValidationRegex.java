/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.question1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;



/**
 * This Class validate the IP from a dedicated input file, 
 * and output the valid IP to console and a dedicated file 
 * or override the input file with valid IP(s). 
 * @author michaelzhang
 * @version 1.0
 */
public class IPValidationRegex {
    private String regexString = "((25[0-5])|(2[0-4]\\d)|([0-1]\\d\\d)|([0-9]\\d)|\\d)"
            + "(\\.((25[0-5])|(2[0-4]\\d)|([0-1]\\d\\d)|([0-9]\\d)|\\d)){3}";

    private String fileName;
    private String outputFileName;
    
  
    /**
     * @param filename name of the input file contains IP to be filtered
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    /**
     * 
     * @param outputFileName name of the output file contains IP filtered
     */
    public void setOutputFileName(String outputFileName)
    {
        this.outputFileName = outputFileName;
    }
    
    /**
     * 
     * @param ip IP string to be processed
     * @return true if valid, false if invalid 
     */
    public boolean isIPValid(String ip)
    {        
        return Pattern.matches(regexString, ip);
    }
    
    /**
    *read IP string from a input file, filtered out the invalid IP, 
    *output the valid IP(s) to console and dedicated output file.
    * @return number of valid IP.
    */
    public int doIPValidation()
    {
       boolean isOverRide = false;
       int valid_no = 0;
               
       try{
          String filepath = IPValidationRegex.class.getResource("/").getFile() +fileName;
          String outputFilepath = IPValidationRegex.class.getResource("/").getFile() + outputFileName;
          
          if(filepath.equals(outputFilepath)){
              //use temp_output.txt as a temporary file
              outputFilepath = IPValidationRegex.class.getResource("/").getFile() + "test/question1/temp_output.txt";
              isOverRide = true;
          }
 
           File file = new File(filepath);
           BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
           
           File outfile = new File(outputFilepath);
           BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outfile));
           
           String str = null;
           
           while((str=bufferedReader.readLine())!=null){
               if(isIPValid(str)) {//check if ip valid
                 valid_no++;
                 System.out.println(str);
                 bufferedWriter.write(str);
                 bufferedWriter.newLine();//return
               }
           }
           bufferedReader.close();
           bufferedWriter.close();
           
           //over ride the orignal file when input file name and out file name are same
           if(isOverRide == true){
             file = new File(outputFilepath);
             bufferedReader = new BufferedReader(new FileReader(file));
           
             outfile = new File(filepath);
             bufferedWriter = new BufferedWriter(new FileWriter(outfile));
             while((str=bufferedReader.readLine())!=null){   
                bufferedWriter.write(str);
                bufferedWriter.newLine();//return              
             }
             bufferedReader.close();
             bufferedWriter.close();
             file.delete();//delete temporary file
           }
       
       } catch(FileNotFoundException e){
           System.out.println("File " + fileName + " not exist !");          
       } catch(IOException e) {
           System.out.println(e.getMessage());
       }
        
        return valid_no;
    }

    /**
     * if no parameter, use ip.txt as a default input file name
     * if only 1 parameter, use ip_out.txt as a default output file name
     * if output file name is same with input file name, 
     * override the original input file.
     * @param args the command line arguments, args[0] is input file name, args[1] output file name
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //System.out.println(args[0]);
        
        IPValidationRegex ipvr = new IPValidationRegex();
        if(args.length == 0)//no arg
            ipvr.setFileName("test/question1/ip.txt");
        else ipvr.setFileName("test/question1/"+args[0]);
        
        if(args.length <2) //no outputfile
            ipvr.setOutputFileName("test/question1/ip_out.txt");
        else ipvr.setOutputFileName("test/question1/"+args[1]);
        
        ipvr.doIPValidation();
        
    }
    
}
