package com.Jclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class DBUtility {
	static Map<String, PreparedStatement>preparedStatementMap=new HashMap<String, PreparedStatement>();
	
	static {
		try {
			Connection con=new GetConnectionObject().getConnectionObj();
			preparedStatementMap.put("insertIntoArrival", con.prepareStatement("select name ,count(*) AS cnt from history \r\n" + 
					"group by name\r\n" + 
					"order by cnt desc limit 3"));
//			preparedStatementMap.put("insertIntoValidRecord", con.prepareStatement("insert into ?values((select file_id from arrival_records where file_name=?),?,?,?)"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static PreparedStatement getPreparedStatement(String i) {
		return preparedStatementMap.get(i);
	}
}
