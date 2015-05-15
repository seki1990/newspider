package com.chuangwai.newspider;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
		
		String pattern = "(?<=<div class=\"content_list\">)[\\s\\S]*?(?=</ul>)";
		content = Regex.matchOne(content, pattern) ;
		System.out.println(content);
		pattern = "(?<=<div class=\"dd_bt\"><a href=\").*?(?=\">)" ;
		ret = Regex.matchAll(content, pattern) ;
		
		return ret ;
	}
	
	public static News getNews(String content)
	{
		News ret = new News();
		
		try{
			String tmp = Regex.matchOne(content, "(?<=<title>).*?(?=</title>)");
			ret.setTitle( tmp.substring(0, tmp.indexOf('-'))) ;
			System.out.println("title :"+ret.getTitle()) ;
			
			tmp = Regex.matchOne(content, "(?<=<!--正文start-->).*?(?=<!--正文start-->)") ;
			tmp = tmp.replaceAll("<.*?>", "").replaceAll("\"", "'");
			ret.setContent( tmp) ;
			
			tmp = Regex.matchOne(content, "(?<=<div id=\"nav\">).*?(?=</div>)") ;
			ret.setCategory( Regex.matchLast(tmp, "(?<=\">).*?(?=</a>)")) ;
			System.out.println("category :"+ret.getCategory()) ;
			
			ret.setSource1("chinanews") ;
			
			tmp = Regex.matchOne(content, "(?<=source_baidu\">).*?(?=</span>)") ;
			ret.setSource2( Regex.matchOne(tmp, "(?<=blank\">).*?(?=</a>)")) ;
			
			tmp = Regex.matchOne(content, "(?<=pubtime_baidu\">).*?(?=</span>)");
			SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			Date date = format.parse(tmp);
			System.out.println(date);
			ret.setPubtime((int) (date.getTime()/1000));
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
		String url = "http://www.chinanews.com/scroll-news/news1.html" ;

		String text = getUrlContent(url);
		ArrayList<String> urls = getUrlList(text);
		
		int lastTime = getLastTime() ;
		for ( int i = 0 ; i < urls.size() ; i++ )
		{
			url = urls.get(i);
			if( url.contains("video")||url.contains("photo") )  continue ;
			System.out.println(url);
			text = getUrlContent(url) ;
			
			News news = getNews(text) ;
			if( news==null ) continue ;
			if( news.getPubtime()<=lastTime ) break ;
			
			System.out.println("cnt: "+cnt++) ;
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
	
	
	
	
	private static void allInMysql()
	{
		ArrayList<String> list = new ArrayList<String>();
		list =FileOperation.read("out_bak.txt");
		
		System.out.println(list.get(0));
		System.out.println(list.size());
		
	}
	private static void delSame()
	{
		
		return ;
	}
	private static void titletiny()
	{
		Mysql mysql = new Mysql("jdbc:mysql://localhost/chuangwai?useUnicode=true&characterEncoding=utf8","root","chuangwai123");
		ResultSet ret ;
		ret = mysql.query("select id,title from news");
		
		ArrayList<String> up = new ArrayList<String>();
		try {
			while( ret.next() )
			{
				String id = ret.getString(1);
				String title = ret.getString(2);
				for( int i = title.length()-1 ; i >= 0 ; i-- )
				{
					if( title.charAt(i)=='_' )
					{
						title = title.substring(0, i);
						String sql = "update news set title=\""+title+"\" where id="+id ;
						System.out.println(sql);
						up.add( sql);
						
						break;
					}
				}
			}
			mysql.update(up);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ;
	}
	
	
	public static void main(String[] args)
	{
	//	allInMysql();
	//	delSame();		
	//	if(true) return ;
	//	titletiny();
				
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
