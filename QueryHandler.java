package javaDatabaseDemo;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class QueryHandler {
	
	String url = "jdbc:mysql://localhost:3306/masterdb";
	String username = "root";
	String password = "password";
	Connection connection = null;

	public Connection settings() {
		if (connection == null);
		try {
			connection = DriverManager.getConnection(url, username, password);
		}catch(Exception e){
		}
		return connection;
	}
	public ArrayList<Integer> customerInsertion(ArrayList<Object> list){
		Connection custom_connection ;
		PreparedStatement stmt1 =null;
		ArrayList<Object> customerList = list;
		ArrayList<Integer> mainList = new ArrayList();
		ResultSet rs;
		int successRate [] = {};
		try {
			custom_connection = settings();
			String sql = "insert into customer_details(name,city)values(?,?)";
			stmt1 = custom_connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for(int i=0; i< customerList.size(); i+=2) {
				CustomerInfo customerIn = (CustomerInfo) customerList.get(i);
				stmt1.setString(1, customerIn.getName());
				stmt1.setString(2, customerIn.getCity());
				stmt1.addBatch();
			}
			successRate = stmt1.executeBatch();
			 for(int i=0;i< successRate.length;i++) {
					mainList.add(successRate[i]);
			 }
			rs = stmt1.getGeneratedKeys();
			while(rs.next()){
				mainList.add(rs.getInt(1));
			}
		}catch(BatchUpdateException e){
			successRate= e.getUpdateCounts();
			for(int i=0;i< successRate.length;i++) {
				mainList.add(successRate[i]);
			}
			try {
				rs = stmt1.getGeneratedKeys();
				while(rs.next()){
					mainList.add(rs.getInt(1));
				}
			}catch (Exception ex){
				e.printStackTrace();
			}
		} catch (Exception throwables) {
			throwables.printStackTrace();
	} finally {
			try {
				assert stmt1 != null;
				stmt1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return mainList;
	}
	public HashMap<Integer, CustomerInfo> customerRetrival() {
		HashMap<Integer, CustomerInfo> entry =  new HashMap<>();
		QueryHandler handler = new QueryHandler();
		Connection custom_connection;
		ResultSet custom_rs=null;
		Statement stmt =null;
		try {
			custom_connection = handler.settings();
			stmt = custom_connection.createStatement();
			String sql1 = "select name,city,customer_id from customer_details";
			custom_rs = stmt.executeQuery(sql1);
			while (custom_rs.next()) {
				Integer id = custom_rs.getInt("customer_id");
				String name = custom_rs.getString("name");
				String city = custom_rs.getString("city");
				CustomerInfo input = new CustomerInfo();
				input.setCity(city);
				input.setName(name);
				input.setCustomerId(id);
				entry.put(id, input);
			}

			} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{
			custom_rs.close();
			stmt.close();}
			catch(Exception e){

			}
		}
		return entry;
	}


	static Connection account_connection = null;
	static PreparedStatement stmt1=null;

	public ArrayList<Integer> accountInsertion(AccountInfo accountIn){
		int account_no =0;
		ArrayList<Integer>accountIdList = new ArrayList<>();
		try {
			account_connection = settings();
			String sql = "insert into account_details(account_no,customer_id,balance)values(?,?,?)";
			stmt1 = account_connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			System.out.println();
				stmt1.setInt(1, accountIn.getAccount_no());
				stmt1.setInt(2, accountIn.getCustomer_id());
				stmt1.setBigDecimal(3, accountIn.getSalary());
				int id = stmt1.executeUpdate();
				accountIdList.add(id);
				ResultSet set = stmt1.getGeneratedKeys();
				set.next();
				account_no = set.getInt(1);
				accountIdList.add(account_no);
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			try {
				stmt1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return accountIdList;
	}

	public HashMap<Integer,HashMap<Integer, AccountInfo>> accountRetrival() throws SQLException {
		HashMap<Integer,HashMap<Integer, AccountInfo>> outer =new HashMap();
		QueryHandler handler = new QueryHandler();
		Connection account_connection;
		ResultSet account_rs = null;
		Statement stmt;
		try {
			account_connection = handler.settings();
			stmt = account_connection.createStatement();
			String sql1 = "select customer_id,account_no,balance from account_details";
			account_rs = stmt.executeQuery(sql1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		while(account_rs.next()) {
			Integer customer_id = account_rs.getInt("customer_id");
			Integer account_no = account_rs.getInt("account_no");
			BigDecimal salary = account_rs.getBigDecimal("balance");
			AccountInfo accounts = new AccountInfo();
			accounts.setCustomer_id(customer_id);
			accounts.setAccount_no(account_no);
			accounts.setSalary(salary);
			HashMap<Integer, AccountInfo>inner  = outer.getOrDefault(customer_id,new HashMap<>());
			inner.put(account_no,accounts);
			outer.put(customer_id, inner);
		}
		account_rs.close();
		return outer;
	}
	public boolean closingProcess() {
		Connection conn = settings();
		boolean status = false;
		try{
			conn.close();
			if(conn.isClosed())
				status=true;
		}catch (Exception e){

		}
		return status;
	}
}
