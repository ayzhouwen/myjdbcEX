package pool.spring;

import com.alibaba.fastjson.JSON;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */
public class DbPollTest {

    public  static   String getList(){
        {
            List<HashMap<String,Object>> list=new ArrayList();
            try {
                String sql="SELECT id,robot_name,create_date FROM reverseauction_histroy LIMIT 50 ";
                PoolDB db=new PoolDB();
                ResultSet rs=db.getQuery(sql);
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
                    HashMap<String,Object> hmap=new HashMap<String, Object>();
                    for(int i=1;i<=columns;i++)
                    {
                        //id
                        hmap.put("id",rs.getString("id"));
                        hmap.put("robot_name",rs.getString("robot_name"));
                        hmap.put("create_date",rs.getString("create_date"));
                        // System.out.print(rs.getString(i));
                        //System.out.print("\t\t");
                    }
                    list.add(hmap);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("list", list);
            String json= JSON.toJSONString(map);
            System.out.println(json);
            return json;

        }
    }

    public static void main(String[] args){
        getList();
    }
}
