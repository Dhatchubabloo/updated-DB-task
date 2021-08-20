package javaDatabaseDemo;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryHandler implements Persistance{
	
	String url = "jdbc:mysql://localhost:3306/masterdb";
	String username = "root";
	String password = "Root@123";
	private static PreparedStatement stmt=null;
	private static String sql="";

	public Connection settings() {
		Connection connection = null;
		if (connection == null);
		try {
			connection = DriverManager.getConnection(url, username, password);
		}catch(Exception e){
		}
		return connection;
	}
	Connection conn =settings();
	public ArrayList<Integer> customerInsertion(ArrayList<Object> list) throws ExceptionHandling {
		ArrayList<Object> customerList = list;
		ArrayList<Integer> mainList = new ArrayList();
		if(customerList!=null) {
			ResultSet rs;
			int successRate[] = {};
			try {
				sql = "insert into customer_details(name,city)values(?,?)";
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				for (int i = 0; i < customerList.size(); i += 2) {
					CustomerInfo customerIn = (CustomerInfo) customerList.get(i);
					stmt.setString(1, customerIn.getName());
					stmt.setString(2, customerIn.getCity());
					stmt.addBatch();
				}
				successRate = stmt.executeBatch();
				for (int i = 0; i < successRate.length; i++) {
					mainList.add(successRate[i]);
				}
				rs = stmt.getGeneratedKeys();
				while (rs.next()) {
					mainList.add(rs.getInt(1));
				}
			} catch (BatchUpdateException e) {
				successRate = e.getUpdateCounts();
				for (int i = 0; i < successRate.length; i++) {
					mainList.add(successRate[i]);
				}
				System.out.println(mainList);
				try {
					rs = stmt.getGeneratedKeys();
					while (rs.next()) {
						mainList.add(rs.getInt(1));
					}
				} catch (Exception ex) {
					e.printStackTrace();
				}
			} catch (Exception throwables) {
				System.out.println(throwables);
				throw new ExceptionHandling("The data were Missing..Please Enter the valid data");
			} finally {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return mainList;
	}
	public HashMap<Integer, CustomerInfo> customerRetrival() throws ExceptionHandling {
		HashMap<Integer, CustomerInfo> entry =  new HashMap<>();
		ResultSet custom_rs=null;
		Statement stmt1 =null;
		try {
			stmt1 = conn.createStatement();
			sql = "select name,city,customer_id,password from customer_details where customer_status!='Deactivate'";
			custom_rs = stmt1.executeQuery(sql);
			while (custom_rs.next()) {
				Integer id = custom_rs.getInt("customer_id");
				String name = custom_rs.getString("name");
				String city = custom_rs.getString("city");
				String password = custom_rs.getString("password");
				CustomerInfo input = new CustomerInfo();
				input.setCity(city);
				input.setName(name);
				input.setCustomerId(id);
				input.setPassword(password);
				entry.put(id, input);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandling("There is some customer data was missing");
		}finally {
			try{
			custom_rs.close();
			stmt1.close();
			}catch(Exception e){}
		}
		return entry;
	}

	public ArrayList<Integer> accountInsertion(AccountInfo accountIn)throws ExceptionHandling{
		ArrayList<Integer>accountIdList = new ArrayList<>();
		int accountRate[] = {};
		try {
			sql = "insert into account_details(customer_id,balance,branch)values(?,?,?)";
			stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			System.out.println();
				stmt.setInt(1, accountIn.getCustomer_id());
				stmt.setBigDecimal(2, accountIn.getBalance());
				stmt.setString(3,accountIn.getBranch());
				stmt.addBatch();
				accountRate =stmt.executeBatch();
				for(int i:accountRate)
					accountIdList.add(i);
				ResultSet set = stmt.getGeneratedKeys();
				set.next();
				accountIdList.add(set.getInt(1));

		}catch(BatchUpdateException e) {
			accountRate = e.getUpdateCounts();
			for(int i:accountRate)
				accountIdList.add(i);
			try {
				ResultSet set = stmt.getGeneratedKeys();
				set.next();
				accountIdList.add(set.getInt(1));
			}catch(Exception ex){
				System.out.println(e);
			}
		}
		catch (Exception ex){
			System.out.println(ex);
			throw new ExceptionHandling("The data were Missing..Please Enter the valid data");
		}
		finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return accountIdList;
	}

	public HashMap<Integer,HashMap<Integer, AccountInfo>> accountRetrival() throws ExceptionHandling{
		HashMap<Integer,HashMap<Integer, AccountInfo>> outer =new HashMap();
		ResultSet account_rs = null;
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			sql = "select customer_id,account_no,balance,account_status from account_details where account_status!='Deactivate'";
			account_rs = stmt.executeQuery(sql);

		while(account_rs.next()) {
			Integer customer_id = account_rs.getInt("customer_id");
			Integer account_no = account_rs.getInt("account_no");
			BigDecimal salary = account_rs.getBigDecimal("balance");
			String status = account_rs.getString("account_status");
			AccountInfo accounts = new AccountInfo();
			accounts.setCustomer_id(customer_id);
			accounts.setAccount_no(account_no);
			accounts.setBalance(salary);
			accounts.setStatus(status);
			HashMap<Integer, AccountInfo>inner  = outer.getOrDefault(customer_id,new HashMap<>());
			inner.put(account_no,accounts);
			outer.put(customer_id, inner);
		}
		} catch (SQLException e) {
			throw new ExceptionHandling("There is some account data was missing");
		}
		finally {
			try {
				account_rs.close();
				stmt.close();
			}catch(Exception e){}
		}
		return outer;
	}
	public HashMap<Integer,HashMap<Integer, String>> wholeAccountDetails() throws ExceptionHandling{
		HashMap<Integer,HashMap<Integer, String>> outer =new HashMap();
		ResultSet account_rs = null;
		Statement stmt=null;
		try {
			stmt = conn.createStatement();
			sql = "select * from account_details";
			account_rs = stmt.executeQuery(sql);
			while(account_rs.next()) {
				Integer customer_id = account_rs.getInt("customer_id");
				Integer account_no = account_rs.getInt("account_no");
				BigDecimal balance = account_rs.getBigDecimal("balance");
				String status = account_rs.getString("account_status");
				AccountInfo accounts = new AccountInfo();
				accounts.setCustomer_id(customer_id);
				accounts.setAccount_no(account_no);
				accounts.setBalance(balance);
				accounts.setStatus(status);
				HashMap<Integer, String>inner  = outer.getOrDefault(customer_id,new HashMap<>());
				inner.put(account_no,status);
				outer.put(customer_id, inner);
			}
		} catch (SQLException e) {
			throw new ExceptionHandling("No data for that account");
		}
		finally {
			try {
				account_rs.close();
				stmt.close();
			}catch(Exception e){}
		}
		return outer;
	}
	public void customerDeletion(int id){
		try{
			sql = "delete from customer_details where customer_id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id);
			stmt.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
		}
		finally {
			try {
				stmt.close();
			}catch(Exception e){}
		}
	}
	public int customerActivation(int id)throws ExceptionHandling{
		int status=0;
		try{
			stmt =conn.prepareStatement("update customer_details set customer_status = 'Activate' where customer_id =?");
			stmt.setInt(1,id);
			status = stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
			throw new ExceptionHandling("Error occured while Activating the customer...please try again later");
		}
		finally {
			try {
				stmt.close();
			}catch(Exception e){}
		}
		return status;
	}
	public int accountActivation(int account_no) throws ExceptionHandling {
		int status=0;
		try{
			stmt=conn.prepareStatement("update account_details set account_status='Activate' where account_no=?");
			stmt.setInt(1,account_no);
			status = stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
			throw new ExceptionHandling("Error occured while Activating the account...please try again later");
		}
		finally {
			try {
				stmt.close();
			}catch(Exception e){}
		}
		return status;
	}
	public int dataUpdation(AccountInfo info,String type) throws ExceptionHandling {
		int status=0;
		try{
			if(type =="customer") {
				sql = "update account_details set account_status='Deactivate' where customer_id=? AND account_status!='Deactivate'";
				for (int i = 0; i < 2; i++) {
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, info.getCustomer_id());
					status = stmt.executeUpdate();
					sql = "update customer_details set customer_status='Deactivate' where customer_id=? AND customer_status!='Deactivate'";
				}
			}
			else{
				sql = "update account_details set account_status='Deactivate' where account_no=? AND account_status!='Deactivate'";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1,info.getAccount_no());
				status= stmt.executeUpdate();
			}
		}catch(Exception e){
			System.out.println(e);
			throw new ExceptionHandling("No data found for the customer so could Not update");
		}
		finally {
			try {
				stmt.close();
			}catch(Exception e){}
		}
		return status;
	}
	public int transcation(TransactionInfo info,BigDecimal amount){
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
		finally {
			try {
				stmt.close();
			}catch(Exception e){}
		}
		return value;
	}
	public void passwordSetter(CustomerInfo info){
		try {
			sql = "update customer_details set password = ? where customer_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,info.getPassword());
			stmt.setInt(2,info.getCustomerId());
			stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally {
			try{
				stmt.close();
			}catch(Exception e){}
		}
	}
	public void transInsertion(TransactionInfo info,String type){
		try {
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
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(Exception e){}
		}
	}
	public void statementClose(){
		try{
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean closingProcess() throws ExceptionHandling {
		Connection conn = settings();
		boolean status = false;
		try{
			conn.close();
			if(conn.isClosed())
				status=true;
		}catch (Exception e){
			e.printStackTrace();
			throw new ExceptionHandling("Error occured while closing your Database Connection...So please check your Connection");
		}
		finally {
			try {
				stmt.close();
			}catch(Exception e){}
		}
		return status;
	}
}

