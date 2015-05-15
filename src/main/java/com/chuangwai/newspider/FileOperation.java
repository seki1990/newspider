package com.chuangwai.newspider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

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
	
	public static ArrayList<String> read(String fileName)
	{
		ArrayList<String> text = new ArrayList<String>();
		String str;
		String tempString = null;
	
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
			BufferedReader fileReader = new BufferedReader(isr);
	
			while ((tempString = fileReader.readLine()) != null)
			{
				str = tempString;
				text.add(str);
			}
			fileReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return text;

	}
	
	
	public static void main(String[] args)
	{
		write("out.txt", "hello world1\n") ;
		write("out.txt", "hello world2\n") ;
	}

}
