package com.chuangwai.newspider;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	
	public static String matchOne(String cont, String ptn)
	{
		String ret = null ;
		
		Pattern pattern = Pattern.compile(ptn) ;
		Matcher matcher = pattern.matcher(cont) ;
		
		if( matcher.find())
		{
			ret = matcher.group(0);
		}
		
	
		return ret ;
	}
	
	public static ArrayList<String> matchAll(String cont, String ptn)
	{
		ArrayList<String> ret = new ArrayList<String>() ;
		
		Pattern pattern = Pattern.compile(ptn) ;
		Matcher matcher = pattern.matcher(cont) ;
		
		while(matcher.find())
		{
			ret.add(matcher.group()) ;
		}
		
		
		return ret ;
	}
	
	public static String matchLast(String cont, String ptn)
	{
		String ret = null ;
		
		Pattern pattern = Pattern.compile(ptn);
		Matcher matcher = pattern.matcher(cont);
		
		while(matcher.find())
		{
			ret = matcher.group() ;
		}
		
		return ret ;
	}

	
	
	public static void main(String[] args)
	{
		System.out.println("hellllllo");
		
		System.out.println(matchOne("1234567","234")) ;
		System.out.println(matchAll("12341234","23")) ;
		
		
		return ;
	}
	

}
