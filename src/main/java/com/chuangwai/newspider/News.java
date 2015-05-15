package com.chuangwai.newspider;

/**********************
 * 
 * @author seki
 * @date 2015.5.13
 * 
 */

public class News {
	
	private int id ;
	private String title ;
	private String content ;
	private String category ;
	private String source1 ;
	private String source2 ;
	private int pub_time ;
	
	public News()
	{
		id = -1 ;
		title = null ;
		content = null ;
		category = null ;
		source1 = null ;
		source2 = null ;
		pub_time = -1 ;
	}
	
	public News(int i, String tit, String cont, String cat, String src1, String src2, int tm)
	{
		id = i ;
		title = tit ;
		content = cont ;
		category = cat ;
		source1 = src1 ;
		source2 = src2 ;
		pub_time = tm ;
	}
	
	public void setId(int x) {id = x;}
	public void setTitle(String str) {title = str;}
	public void setContent(String str) {content = str;}
	public void setCategory(String str) {category = str;}
	public void setSource1(String str) {source1 = str;}
	public void setSource2(String str) {source2 = str;}
	public void setPubtime(int x) {pub_time = x;}
	
	public int getId() {return id;}
	public String getTitle() {return title;}
	public String getContent() {return content;}
	public String getCategory() {return category;}
	public String getSource1() {return source1;}
	public String getSource2() {return source2;}
	public int getPubtime() {return pub_time;}
	

}
