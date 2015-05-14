package com.chuangwai.newspider;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Mysql {
	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	private String host;
	private String user;
	private String pswd;
	
	private Connection conn;
	private Statement stmt;
	
	
	public Mysql(String hst,String usr,String psd)
	{
		host = hst;
		user = usr;
		pswd = psd;
		
		try {
			Class.forName(DRIVER).newInstance();
			conn = DriverManager.getConnection(host,user,pswd);

			if(conn.isClosed())
			{
				System.out.println("[ERROR] mysql connect failed...");
				System.out.println("Driver: "+DRIVER);
				System.out.println("host: "+host);
				System.out.println("user: "+user);
				System.out.println("pswd: "+pswd);
			}
			else
			{
				stmt = conn.createStatement();
			}
		} catch (SQLException e) {
			System.out.println("[isClosed error]: null");
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ResultSet query(String sql)
	{
		try {
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("[stmt.executeQuery error]: "+sql);
			e.printStackTrace();
		}
		
		return null ;
	}
	
	public void update(String sql)
	{
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("[stmt.executeUpdate error]: "+sql);
			e.printStackTrace();
		}
	}
	
	public void update(String sql, String content)
	{
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql) ;
			pstmt.setObject(1, content);
			pstmt.execute() ;
			
			if( pstmt!=null )
			{
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ;
	}
	
	public void update(ArrayList<String> sql)
	{
		Iterator<String> it = sql.iterator();
		closeAutoCommit();
		
		while(it.hasNext())
		{
			String str = it.next();
			try {
				stmt.executeUpdate(str);
			} catch (SQLException e) {
				System.out.println("ERROR SQL: "+str);
				e.printStackTrace();
			}
		}
		
		openAutoCommit();
		return ;
	}
	
	public void closeAutoCommit()
	{
		if(conn!=null)
			try {
				conn.setAutoCommit( false );
			} catch (SQLException e) {
				System.out.println("[conn.setAutoCommit error]: null");
				e.printStackTrace();
			}
	}
	
	public void openAutoCommit()
	{
		try {
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println("[conn.commit|setAutoCommit error]: null");
			e.printStackTrace();
		}
	}
	
	public void colse()
	{
		try {
			if(stmt!=null&&!stmt.isClosed()) stmt.close();
			if(conn!=null&&!conn.isClosed()) conn.close();
		} catch (SQLException e) {
			System.out.println("[close error]: null");
			e.printStackTrace();
		}
		
		return ;
	}


	
}
