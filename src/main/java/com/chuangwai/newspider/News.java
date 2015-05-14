package com.chuangwai.newspider;

public class News {
	
	private static int count = 0 ;
	
	
	private String title ;
	private String content ;
	private String category ;
	private String source ;
	private String pub_time ;
	
	public News()
	{
		title = null ;
		content = null ;
		category = null ;
		source = null ;
		pub_time = null ;
	}
	
	public News(String tit, String cont, String cat, String src, String tm)
	{
		title = tit ;
		content = cont ;
		category = cat ;
		source = src ;
		pub_time = tm ;
	}
	
	public void setTitle(String str) {title = str;}
	public void setContent(String str) {content = str;}
	public void setCategory(String str) {category = str;}
	public void setSource(String str) {source = str;}
	public void setPubtime(String str) {pub_time = str;}
	
	public String getTitle() {return title;}
	public String getContent() {return content;}
	public String getCategory() {return category;}
	public String getSource() {return source;}
	public String getPubtime() {return pub_time;}
	
	
	public String toJSON()
	{
		String ret = null ;
		count++ ;
		ret = "["+count+"],["+title+"],["+content+"],["+category+"],["+source+"],["+pub_time+"]\n" ;

		return ret ;
	}

}
