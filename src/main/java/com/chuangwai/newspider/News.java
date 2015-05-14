package com.chuangwai.newspider;

public class News {
	
	private static int count = 0 ;
	
	
	private String title ;
	private String content ;
	private String category ;
	private String source1 ;
	private String source2 ;
	private String pub_time ;
	
	public News()
	{
		title = null ;
		content = null ;
		category = null ;
		source1 = null ;
		source2 = null ;
		pub_time = null ;
	}
	
	public News(String tit, String cont, String cat, String src1, String src2, String tm)
	{
		title = tit ;
		content = cont ;
		category = cat ;
		source1 = src1 ;
		source2 = src2 ;
		pub_time = tm ;
	}
	
	public void setTitle(String str) {title = str;}
	public void setContent(String str) {content = str;}
	public void setCategory(String str) {category = str;}
	public void setSource1(String str) {source1 = str;}
	public void setSource2(String str) {source2 = str;}
	public void setPubtime(String str) {pub_time = str;}
	
	public String getTitle() {return title;}
	public String getContent() {return content;}
	public String getCategory() {return category;}
	public String getSource1() {return source1;}
	public String getSource2() {return source2;}
	public String getPubtime() {return pub_time;}
	
	
	public String toJSON()
	{
		String ret = null ;
		count++ ;
		ret = "["+count+"],["+title+"],["+content+"],["+category+"],["+source1+"],["+pub_time+"]\n" ;

		return ret ;
	}

}
