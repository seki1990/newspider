package com.chuangwai.newspider;

import java.util.ArrayList;



public class Main {

	private static String stop = null ;
	private static int cnt = 0 ;
	
	
	public static String getUrlContent(String url)
	{
		String ans = HttpConnection.sendGet(url, null);
		
		return ans ;
	}
	
	
	public static ArrayList<String> getUrlList(String content)
	{
		ArrayList<String> ret = new ArrayList<String>() ;
		
		String pattern = "(?<=<div class=\"carditems_box\" id=\"j_items_list\">)[\\s\\S]*?(?=</div>)";
		content = Regex.matchOne(content, pattern) ;
		
		
		pattern = "(?<=<a href=\").*?(?=\")" ;
		ret = Regex.matchAll(content, pattern) ;
		
		
		return ret ;
	}
	
	public static News getNews(String content)
	{
		News ret = new News();
		
		try{
			ret.setTitle( Regex.matchOne(content, "(?<=<title>).*?(?=</title>)")) ;
			System.out.println("title :"+ret.getTitle()) ;
			
			String tmp = Regex.matchOne(content, "(?<=<!--正文内容-->).*?(?=<!-- loading -->)") ;
			ret.setContent( tmp.replaceAll("(?<=<a).*?(?=/a>)", "")) ;
			
			tmp = Regex.matchOne(content, "(?<=h_nav_items).*?(?=</div>)") ;
			ret.setCategory( Regex.matchLast(tmp, "(?<=title=\").*?(?=\")")) ;
			System.out.println("category :"+ret.getCategory()) ;
			ret.setSource("新浪") ;
			ret.setPubtime( Regex.matchOne(content, "(?<=<span class=\"source\">).*?(?=</span>)"));
		}catch(Exception e){
			System.out.println("parsing error.") ;
			return null ;
		}
		
		return ret ;
	}
	
	public static void work()
	{
		String url = "http://news.sina.cn/roll.d.html/?vt=4" ;

		String text = getUrlContent(url);
		ArrayList<String> urls = getUrlList(text);
		
		String tmp = null ;
		boolean first = true ;
		for ( int i = 0 ; i < urls.size() ; i++ )
		{
			url = urls.get(i);
			if( url.contains("video")||url.contains("photo") )  continue ;
			text = getUrlContent(url) ;
			
			News news = getNews(text) ;
			if( news==null ) continue ;
			if( first==true )
			{
				first = false ;
				tmp = news.getPubtime() ;
			}
			
			if( stop == null )
			{
				stop = news.getPubtime() ;
			}
			else if( stop.equals(news.getPubtime()) )
			{
				break ;
			}
			
			System.out.println("cnt: "+cnt++) ;
			FileOperation.write("out.txt", news.toJSON()) ;
		}
		
		stop = tmp ;
		return ;
	}
	
	private static void sleep(int time)
	{
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		}
	}
	
	
	public static void main(String[] args)
	{
		while(true)
		{
			try
			{
			work() ;
			sleep(1000*60) ;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
