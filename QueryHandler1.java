package javaDatabaseDemo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
            sql = "update account_details set account_status='Deactivate' where customer_id=?";
            for(int i=0;i<2;i++) {
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, customer_id);
                status = stmt.executeUpdate();
                sql = "update customer_details set customer_status='Deactivate' where customer_id=?";
            }
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
    public static int withdrawl(TransactionInfo info,BigDecimal amount){
        int value=0;
        try {
            sql = "update account_details set balance = ? where customer_id=? and account_no=?";
            stmt = conn.prepareStatement(sql);
            stmt.setBigDecimal(1,amount);
            stmt.setInt(2,info.getCustomer_id());
            stmt.setInt(3,info.getAccount_no());
            value = stmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
        return value;
    }
    public static int deposite(TransactionInfo info,BigDecimal amount){
        int value=0;
        try{
        sql = "update account_details set balance = ? where customer_id=? and account_no=?";
        stmt = conn.prepareStatement(sql);
        stmt.setBigDecimal(1,amount);
        stmt.setInt(2,info.getCustomer_id());
        stmt.setInt(3,info.getAccount_no());
        value = stmt.executeUpdate();
    }catch(Exception e){
        System.out.println(e);
    }
        return value;
    }
    public static void transInsertion(TransactionInfo info,String type){
        try {
            String sql ="";
            if(type == "withdrawl") {
                sql = "insert into transaction_details(customer_id,account_no,transaction_type)values(?,?,'Withdraw')";
            }
            else {
                sql = "insert into transaction_details(customer_id,account_no,transaction_type)values(?,?,'Deposite')";
            }
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println();
            stmt.setInt(1, info.getCustomer_id());
            stmt.setInt(2, info.getAccount_no());
            stmt.executeUpdate();
            ResultSet set = stmt.getGeneratedKeys();
            set.next();
        }catch (Exception e){

        }
    }
}