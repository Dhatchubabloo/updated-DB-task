package javaDatabaseDemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DatabaseMain {
	static Scanner scan = new Scanner(System.in);
	static HashMap<Integer, CustomerInfo>existingCustomerMap=null;
	public static void main(String[] args){
		existingCustomerMap=Helper.customerDataStore();
		Helper.accountDataStore();
		DatabaseMain db = new DatabaseMain();
		db.execution();
	}

	public void execution(){
		System.out.println();
		System.out.println("1.Enroll both customer details and account details");
		System.out.println("2.Enroll & Check account details");
		System.out.println("3.Check account details");
		System.out.println("4.Exit");
		System.out.println("Enter your choice");
		int choice = scan.nextInt();

		switch (choice) {

			case 1:
				ArrayList<Object> inputList = new ArrayList();
				System.out.println("Enter number of customers do you want to enroll");
				int count = scan.nextInt();
				scan.nextLine();
				for (int i = 1; i <= count; i++) {
					CustomerInfo customerObject = new CustomerInfo();
					System.out.println("customer details for customer" + i);
					System.out.println("Enter Name");
					customerObject.setName(scan.nextLine());
					System.out.println("Enter City");
					customerObject.setCity(scan.next());
					inputList.add(customerObject);
					scan.nextLine();
					System.out.println("Account details for customer" + i);
					AccountInfo accountObject = new AccountInfo();
					System.out.println("Enter Balance");
					accountObject.setSalary(scan.nextInt());
					inputList.add(accountObject);
					scan.nextLine();
				}
				Helper.caseNewUser(inputList,count);
				execution();
				break;

			case 2:
				AccountInfo in = new AccountInfo();
				System.out.println("Account details for existing customer");
				System.out.println("Enter customer id");
				int id = scan.nextInt();
				in.setCustomer_id(id);
				System.out.println("Enter Account number");
				int account_no = scan.nextInt();
				in.setAccount_no(account_no);
				System.out.println("Enter Salary");
				int salary = scan.nextInt();
				in.setSalary(salary);
				Helper.caseExistingUser(in);
				execution();
				break;

			case 3:
				System.out.println("Enter Valid Customer id:");
				int mainId = scan.nextInt();
				if(Helper.helperThree(mainId)){
					System.out.println(Helper.helperThreeCustomer().get(mainId));
					HashMap<Integer, AccountInfo> temp = Helper.helperThreeAccount(mainId);
					System.out.println("1.To check Entire Account");
					System.out.println("2.To check particular Account");
					int option = scan.nextInt();
					switch (option) {
						case 1:
						for (AccountInfo ac : temp.values()) {
							System.out.println(ac);
						}
						break;
						case 2:
							System.out.println("Enter valid Account Number");
							int mainAccount_no = scan.nextInt();
							System.out.println(temp.get(mainAccount_no));
					}
				}else {
					System.out.println("invalid Customer id");
				}
				execution();
				break;

			case 4:
				if(Helper.dbClose())
					System.out.println("your Connection was closed successfully");
				else
					System.out.println("Not closed");
				break;

			default:
				System.out.println("ERROR!.........Enter valid choice");
		}
	}
}

