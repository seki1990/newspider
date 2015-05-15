package com.chuangwai.newspider;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class Main {

	private static int cnt = 0 ;
	
	
	

	public static void writeIntoMysql(News obj)
	{
		Mysql mysql = new Mysql("jdbc:mysql://localhost/chuangwai?useUnicode=true&characterEncoding=utf8","root","chuangwai123");
		
		String sql = "insert into news (category,title,content,source1,source2,pub_time) values(\""+
						obj.getCategory()+"\",\"" +
						obj.getTitle()+"\",\""+
						obj.getContent()+"\",\""+
						obj.getSource1()+"\",\""+
						obj.getSource2()+"\",\""+
						obj.getPubtime()+"\");" ;
		
		mysql.update(sql) ;
		
		return ;
	}
	
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
			tmp = tmp.replaceAll("<p align=\"right\">", "");
			tmp = tmp.replaceAll("\"", "'");
			ret.setContent( tmp.replaceAll("(?<=<a).*?(?=/a>)", "")) ;
			
			tmp = Regex.matchOne(content, "(?<=h_nav_items).*?(?=</div>)") ;
			ret.setCategory( Regex.matchLast(tmp, "(?<=title=\").*?(?=\")")) ;
			System.out.println("category :"+ret.getCategory()) ;
			ret.setSource1("新浪") ;
			tmp = Regex.matchOne(content, "(?<=<span class=\"source\">).*?(?=</span>)") ;
			for( int i = tmp.length()-1 ; i >= 0 ; i-- )
			{
				if( tmp.charAt(i)==' ' )
				{
					SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
					String strTime = tmp.substring(0, i);
					Date date = format.parse(strTime);
					ret.setPubtime((int) date.getTime());
					ret.setSource2(tmp.substring(i+1, tmp.length()));
					break ;
				}
			}
		}catch(Exception e){
			System.out.println("parsing error.") ;
			return null ;
		}
		
		return ret ;
	}
	
	
	private static int getLastTime()
	{
		Mysql mysql = new Mysql("jdbc:mysql://localhost/chuangwai?useUnicode=true&characterEncoding=utf8","root","chuangwai123");
		ResultSet ret ;
		ret = mysql.query("select pub_time from news order by pub_time desc limit 1");
		
		int ans = -1 ;
		try {
			while( ret.next() )
			{
				 ans = ret.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ans ;
	}
	
	public static void work()
	{
		String url = "http://news.sina.cn/roll.d.html/?vt=4" ;

		String text = getUrlContent(url);
		ArrayList<String> urls = getUrlList(text);
		
		int lastTime = getLastTime() ;
		for ( int i = 0 ; i < urls.size() ; i++ )
		{
			url = urls.get(i);
			if( url.contains("video")||url.contains("photo") )  continue ;
			text = getUrlContent(url) ;
			
			News news = getNews(text) ;
			if( news==null ) continue ;
			if( news.getPubtime()<=lastTime ) break ;
			
			System.out.println("cnt: "+cnt++) ;
			FileOperation.write("out.txt", news.toJSON()) ;
			writeIntoMysql(news);
		}
		
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
	
	
	
	
	private static void runOnce()
	{
		Mysql mysql = new Mysql("jdbc:mysql://localhost/chuangwai?useUnicode=true&characterEncoding=utf8","root","chuangwai123");
		ResultSet ret ;
		ret = mysql.query("select * from news");
		
		try {
			while( ret.next() )
			{
				for( int i = 0 ; i < 7 ; i++ )
				{
					System.out.print(ret.getInt(1)+"\t");
				}
				System.out.println("");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
//		runOnce();
		
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
