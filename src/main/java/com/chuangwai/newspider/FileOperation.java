package com.chuangwai.newspider;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileOperation {
	
	
	public static void write(String file, String content)
	{
		BufferedWriter out = null ;
		try {
			out = new BufferedWriter(new OutputStreamWriter(                        
                    new FileOutputStream(file, true)));
			out.write(content);
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {                                                                 
            try {                                                                    
                out.close();                                                        
            } catch (IOException e) {                                               
                e.printStackTrace();                                                
            }                                                                       
        }   
		
		return ;
	}
	
	
	public static void main(String[] args)
	{
		write("out.txt", "hello world1\n") ;
		write("out.txt", "hello world2\n") ;
	}

}
