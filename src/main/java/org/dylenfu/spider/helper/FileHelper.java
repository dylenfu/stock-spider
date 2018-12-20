package org.dylenfu.spider.helper;

import java.io.File;

public class FileWriter {

    public void write(String data) {
        try{

            File file =new File("javaio-appendfile.txt");

            //if file doesnt exists, then create it
            if(!file.exists()){
                file.createNewFile();
            }

            //true = append file
            FileWriter fileWritter = new FileWriter(file.getName(),true);
            fileWritter.write(data);
            fileWritter.close();

            System.out.println("Done");

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
