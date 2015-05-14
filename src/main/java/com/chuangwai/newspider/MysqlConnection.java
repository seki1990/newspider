package com.chuangwai.newspider;

import java.sql.*;


public class MysqlConnection {
	
	public static Connection getConnection(String url,String user,String pswd) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection conn = DriverManager.getConnection(url,user,pswd);
		
		if(!conn.isClosed()) System.out.println("succeed.");
		else System.out.println("failed.");

		
		return conn;
	}
	
	public static void insertSQL(Statement stmt,String sql) throws SQLException
	{
		stmt.executeUpdate(sql);
		
		
		return ;
	}
	
	public static void selectSQL()
	{
		
		return ;
	}
	
	
	public static void cutConnection(Connection conn) throws SQLException
	{
		if(conn!=null) conn.close();
		
		
		return ;
	}
	
	
	public static void main (String args[]) throws ClassNotFoundException, SQLException
	{
		String url = "jdbc:mysql://localhost/shangxi";
		String usr = "root";
		String psd = "123456";
		
		Connection conn = getConnection(url,usr,psd);
		Statement stmt = conn.createStatement();
		
		ResultSet ret = stmt.executeQuery("select * from t_shangxi_fact limit 10");
		
		while(ret.next())
		{
			System.out.print(ret.getInt(1)+",");
			System.out.print(ret.getString(2)+",");
			System.out.print(ret.getString(3)+",");
			System.out.print(ret.getString(4)+",");
			System.out.print(ret.getString(5)+",");
			System.out.print(ret.getString(6)+",");
			System.out.print(ret.getString(7)+",");
			System.out.print(ret.getString(8)+",");
			System.out.print(ret.getString(9)+",");
			System.out.print(ret.getInt(10));
			
			System.out.println("");
		}
		
		
		return ;
	}


}
