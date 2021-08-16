package javaDatabaseDemo;

import java.math.BigDecimal;
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
    public int customerUpdation(int customer_id){
        int status=0;
        try{
            sql = "update account_details set accountStatus='Deactivate' where customer_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,customer_id);
             status = stmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
        return status;
    }
    public int accountUpdation(int account_no){
        int status=0;
        try{
            sql = "update account_details set accountStatus='Deactivate' where account_no=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,account_no);
            status= stmt.executeUpdate();
        }catch(Exception e) {
            System.out.println(e);
        }
        return status;
    }
    public static int withdrawl(int customer_id, int account_no, BigDecimal amount){
        int value=0;
        try {
            sql = "update account_details set balance = ? where customer_id=? and account_no=?";
            stmt = conn.prepareStatement(sql);
            stmt.setBigDecimal(1,amount);
            stmt.setInt(2,customer_id);
            stmt.setInt(3,account_no);
            value = stmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
        return value;
    }
    public static int deposite(int customer_id, int account_no, BigDecimal amount){
        int value=0;
        try{
        sql = "update account_details set balance = ? where customer_id=? and account_no=?";
        stmt = conn.prepareStatement(sql);
        stmt.setBigDecimal(1,amount);
        stmt.setInt(2,customer_id);
        stmt.setInt(3,account_no);
        value = stmt.executeUpdate();
    }catch(Exception e){
        System.out.println(e);
    }
        return value;
    }
}