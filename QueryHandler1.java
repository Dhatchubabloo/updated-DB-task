package javaDatabaseDemo;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QueryHandler1 {
    static QueryHandler db = new QueryHandler();
    static Connection conn =db.settings();
    static PreparedStatement stmt;
    static String sql="";
    public int customerDeletion(int id){
        int status=0;
        try{
            sql = "delete from customer_details where customer_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,id);
            status= stmt.executeUpdate();
        }catch(Exception e) {
            System.out.println(e);
        }
        return status;
    }
    public int accountDeletion(int account_no){
        int status=0;
        try{
            sql = "delete from account_details where account_no = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,account_no);
            status= stmt.executeUpdate();
        }catch(Exception e) {
            System.out.println(e);
        }
        return status;
    }
}