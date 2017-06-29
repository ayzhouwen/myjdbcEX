package nopool;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

//注意要使用完用关闭连接,注意次类不能当做单例使用,如果想高性能去使用pool下的类
public class DBBean {
//服务器数据库地址
	String url="jdbc:mysql://10.0.1.243:3306/jsytable?useUnicode=true&characterEncoding=utf-8";
	String user="root";
	String pwd="root";
	String driverManage="org.gjt.mm.mysql.Driver";
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;   
	//本地数据库地址
//	String url="jdbc:mysql://localhost:3306/hehui?useUnicode=true&characterEncoding=utf-8";
//	String user="root";
//	String pwd="root";
//	String driverManage="org.gjt.mm.mysql.Driver";
//	Connection conn=null;
//	Statement stmt=null;
//	ResultSet rs=null;  

	public DBBean()
	{
		try {
			Class.forName(driverManage);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("您的驱动错误");
		}
		try {
			 conn=DriverManager.getConnection(url,user,pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("您的连接错误");
		}
		try {
			stmt=conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("声明错误");
		}
				
	}
	public ResultSet getQuery(String sql)
	{
		try {
			rs=stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询错误");
		//	System.out.println(sql);
			
		}
		return rs;
	}
	public int getUpdate(String sql)
	{
		int i=0;
		try {
			i=stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("更改错误");
		//	System.out.println(sql);
		}
		return i;
	}
	//关闭链接
	  public void closeObject()
		{
			try {
				if (rs!=null){
					rs.close();
				}
				if (stmt!=null){
					stmt.close();
				}

				if (conn !=null){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public String getTimeCurrent()
	{
		String timeCurrent="";
	
		java.text.DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timeCurrent=df.format(new Date());
		return timeCurrent;
	}
	
	//输入日期计算星期几方法
	public String getWeekDay(String DateStr){
	      SimpleDateFormat formatYMD=new SimpleDateFormat("yyyy-MM-dd");//formatYMD表示的是yyyy-MM-dd格式
	      SimpleDateFormat formatD=new SimpleDateFormat("E");//"E"表示"day in week"
	      Date d=null;
	      String weekDay="";
	      try{
	         d=formatYMD.parse(DateStr);//将String 转换为符合格式的日期
	         weekDay=formatD.format(d);
	      }catch (Exception e){
	         e.printStackTrace();
	      }
	      //System.out.println("日期:"+DateStr+" ： "+weekDay);
	     return weekDay;
	 }
	public boolean getLoginById(String user,String password)
	{
		boolean bl=false;
		//System.out.println("是报");
		String sql="select * from users where user='"+user+"' and password='"+password+"'";
		ResultSet rs=getQuery(sql);
		//System.out.println(rs);
		try {
			if(rs.next())
			{
				bl=true;
			}
			else
			{
				bl=false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("登陆查询错误");
		}
		return bl;
	}
	//靠用户名获取ID,
	public String getUserById(String user)
	{
		String userid="";
		
		String sql="select id,username from user_info where userid='"+user+"'";
		//System.out.println(sql);
		ResultSet rs=getQuery(sql);
		try {
			if(rs.next()){
				userid=rs.getString("id");
			}else{
				userid="0";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userid;
	}
	 public String replace(String s,String oldStr,String newStr){   
		  while(s!=null&&(s.indexOf(oldStr)!=-1)){   
		  s=s.substring(0,s.indexOf(oldStr))+newStr+s.substring(s.indexOf(oldStr)+oldStr.length());   
		  }   
		  return   s;   
		  }  
	 public String convert2HTML(String s) {
	        StringBuffer buf = new StringBuffer();
	        for (int i = 0; i < s.length(); i++) {
	            char c = s.charAt(i);
	            switch (c) {
	                case '\n':
	                    buf.append("<br>"); break;
	                case '<':
	                    buf.append("<"); break;
	                case '>':
	                    buf.append(">"); break;
	                case ' ':
	                    buf.append(" "); break;
	                case '&':
	                    buf.append("&"); break;
	                case '\"':
	                    buf.append("\""); break;
	                default:
	                    buf.append(c);
	            }
	        }
	        return buf.toString();
	    }
	 public static boolean isNumeric0(String str){
		  for(int i=str.length();--i>=0;){
		   int chr=str.charAt(i);
		   if(chr<48 || chr>57)
		    return false;
		  }
		  return true;
		 }

	
	 //判断是否为double类型
	 public static boolean isdouble(String str){
		 try{ 
			 
		 double b = Double.parseDouble(str);
		// System.out.println("okok");
		 return true;
		 }catch(Exception e){
		//	 System.out.println("nono");
			 return false;
		 }
		
	 }
	 //测试连接数
	 public static void main(String arg[]){
		//直接连接的时候是释放一部分
		 DBBean db=new DBBean();
		System.out.println(db.isNumeric0(""));

	 }


}
