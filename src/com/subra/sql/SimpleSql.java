package com.subra.sql;

/*
 mysql-connector-java-*.jar in the build path to hook to jdbc driver impl
property is user (NOT username) and password. protocol is jdbc:mysql, dbname=practdb
DriverManager good practice. Other tweak straight from he vendor class is NOT because tied to mysql specific implementation.
 */
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class SimpleSql {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		System.out.println("----");
		String user = "sdass";
		String passwd = "xxxxx"; 
		 //simplyworks(user, passwd); 
		tweakStraightDriverClass(user, passwd);
		

	}//main 
	
	private static void simplyworks(String user, String passwd ){
		
		//String  dburl = "http://localhost:3306/practdb";
		
		try{
		Class<?> DriverclassName = Class.forName("com.mysql.jdbc.Driver");
		System.out.println(DriverclassName);
		 
		 Properties props = new Properties(); props.put("user", "sdass"); props.put("password", passwd);

		 String  dburl = "jdbc:mysql://localhost:3306/practdb";
		// Connection connection =  DriverManager.getConnection(dburl, "sdass", "xxxxx");
		 Connection connection =  DriverManager.getConnection(dburl, props);//works
		 // StringBuilder sb = new StringBuilder(dburl).append('?').append("user=").append(user).append("&password=").append(passwd);works
		//System.out.println("sb=" + sb);
		// Connection connection =  DriverManager.getConnection(sb.toString());		 
		 // Connection connection =  DriverManager.getConnection(dburl, props);		 

		 

		 if(connection == null){
			 System.out.println("connection is null=" + connection);
		 }
		 String sql = "select * from usr";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		// Statement statement = connection.createStatement();
		// ResultSet rs = statement.executeQuery("select name from usr;");
		 ResultSet rs =  preparedStatement.executeQuery();
		 while(rs.next()){
			 System.out.printf("%d - %s - %s\n",rs.getInt(1), rs.getString(2), rs.getString(3));
		 }
		 
		}catch(Exception e){
			e.printStackTrace();
		}
	}//method ends
	
	private static void tweakStraightDriverClass(String user, String passwd ){
		
		try{
		Class<?> DriverclassName = Class.forName("com.mysql.jdbc.Driver");
		System.out.println(DriverclassName);
		com.mysql.jdbc.Driver jdbcDriver =  (com.mysql.jdbc.Driver) DriverclassName.newInstance(); //Directory from DriverClass. Not good practice
		 Properties props = new Properties(); props.put("user", "sdass"); props.put("password", passwd);
		 String  dburl = "jdbc:mysql://localhost:3306/practdb";
		 
		 String sql =   "select * from usr";
		 
		 
		Connection connection = jdbcDriver.connect(dburl, props); //works
		//System.out.println(connection == null);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()){
			 System.out.printf("%d - %s - %s\n",rs.getInt(1), rs.getString(2), rs.getString(3));
		}
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}//method ends
	
}
