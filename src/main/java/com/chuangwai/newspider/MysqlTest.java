package com.chuangwai.newspider;

import java.sql.ResultSet;
import java.sql.SQLException;



public class MysqlTest {
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
	{
		Mysql mysql = new Mysql("jdbc:mysql://localhost/chuangwai?useUnicode=true&characterEncoding=utf8","root","chuangwai123");
		ResultSet ret ;
		ret = mysql.query("select * from news");
		
		while( ret.next() )
		{
			for( int i = 0 ; i < 7 ; i++ )
			{
				System.out.print(ret.getInt(1)+"\t");
			}
			System.out.println("");
		}
		
		return ;
	}

}
