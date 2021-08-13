package javaDatabaseDemo;

public class AccountInfo {
	private int customer_id;
	private int account_no;
	private int salary;
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getAccount_no() {
		return account_no;
	}
	public void setAccount_no(int account_no) {
		this.account_no = account_no;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public String toString()
	{
		return "Account_no:"+account_no+"  |  "+"Salary:"+salary;
	}
	
}
