package nopool;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.ResultSetMetaData;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {

	public  String  getUserByKey(String key){
		List<User> list=new ArrayList();
		try {
			DBBean db=new DBBean();
			String sql="select * from user where name like '%"+key+"%'";
			ResultSet rs =(ResultSet) db.getQuery(sql);
			ResultSetMetaData m=null;//获取 列信息
			m=(ResultSetMetaData) rs.getMetaData();
			int columns=m.getColumnCount();
			//显示列,表格的表头
	
			for(int i=1;i<=columns;i++)
			{
			 System.out.print(m.getColumnName(i));
			 System.out.print("\t\t");
			}

			System.out.println();
			//显示表格内容
			while(rs.next())
			{
				User u=new User();
			 for(int i=1;i<=columns;i++)
			 {
			  //id
				if(i==1){
				  u.setId(Integer.parseInt(rs.getString(i)));
			  }
			  //姓名
				if(i==2){
					  u.setName(rs.getString(i));
				  }
				  
			 // System.out.print(rs.getString(i));
			  //System.out.print("\t\t");
			 }
			 list.add(u);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list", list);
		String json= JSON.toJSONString(map);
		return json;
	}
}
