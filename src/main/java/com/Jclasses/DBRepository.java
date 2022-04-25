package com.Jclasses;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DBRepository {


	public DBRepository() {

	}
	Connection CON;
	java.sql.Statement ST;
	ResultSet RS;
	List<NameNCount> nList= new ArrayList<NameNCount>();
	public List<NameNCount> nameNCounts() {
		
		try {
			CON=new GetConnectionObject().getConnectionObj();
			
			String query="select * ,count(*) AS cnt from items group by name order by cnt desc limit 3";
			ST=CON.createStatement();
			RS=ST.executeQuery(query);
			
			
			while (RS.next()) {
				System.out.println(RS.getString("name")+" "+RS.getInt("cnt")+" "+RS.getInt("price")+" "+RS.getString("type"));
				NameNCount nt=new NameNCount();
				nt.setName(RS.getString("name"));
				nt.setCount(RS.getInt("cnt"));
				nt.setPrice(RS.getInt("price"));
				nt.setType(RS.getString("type"));
				System.out.println(nt);
				nList.add(nt);
				System.out.println(nList.size());
			}
			
			
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nList;
	}

}
