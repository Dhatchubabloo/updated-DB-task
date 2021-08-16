package javaDatabaseDemo;

import java.math.BigDecimal;
import java.util.*;

public class DatabaseMain {
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args){
		Helper.customerDataStore();
		Helper.accountDataStore();
		DatabaseMain db = new DatabaseMain();
		db.execution();
	}

	public void execution(){
		System.out.println();
		System.out.println("1.Enroll both customer details and account details");
		System.out.println("2.Enroll account details");
		System.out.println("3.Check account details");
		System.out.println("4.Cash Handling");
		System.out.println("5.delete the details");
		System.out.println("6.Exit");
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
					accountObject.setBalance(scan.nextBigDecimal());
					inputList.add(accountObject);
					scan.nextLine();
				}
				HashMap<String,String>insertionStatus = Helper.caseNewUser(inputList,count);
				System.out.println("Insertion status:");
                for (Map.Entry<String,String> entry : insertionStatus.entrySet())
                    System.out.println( entry.getKey() +
                            " = " + entry.getValue());
				execution();
				break;

			case 2:
				AccountInfo in = new AccountInfo();
				System.out.println("Account details for existing customer");
				System.out.println("Enter customer id");
				int id = scan.nextInt();
				if(Helper.idCheck(id)) {
					in.setCustomer_id(id);
					System.out.println("Enter balance");
					BigDecimal salary = scan.nextBigDecimal();
					in.setBalance(salary);
					ArrayList<Integer> accountStatus = Helper.caseExistingUser(in);
					if (accountStatus.get(0) >0)
						System.out.println("account insertion succcessful");
					else
						System.out.println("Account insertion Failed");
				}else
					System.out.println("invalid customer_id");
				execution();
				break;

			case 3:
				System.out.println("Enter Valid Customer id:");
				int mainId = scan.nextInt();
				if(Helper.idCheck(mainId)){
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
							System.out.println("--------------------------------------");
						break;
						case 2:
							System.out.println("Enter valid Account Number");
							int mainAccount_no = scan.nextInt();
							System.out.println(temp.get(mainAccount_no));
							break;

						default:
							System.out.println("invalid option");
							execution();
					}
				}else {
					System.out.println("invalid Customer id");
				}
				execution();
				break;
			case 4:
				System.out.println("Enter customer_id");
				int customerId = scan.nextInt();
				if(Helper.idCheck(customerId)) {
					System.out.println("Enter account_no");
					int aacount_no = scan.nextInt();
					if(Helper.accountCheck(customerId,aacount_no)) {
						System.out.println("1.withdrawl");
						System.out.println("2.deposite");
						int option = scan.nextInt();
						switch (option) {
							case 1:
								System.out.println("Enter withdrawl amount");
								BigDecimal Wamount = scan.nextBigDecimal();
								boolean condition = Helper.amountWithdrawl(customerId, aacount_no, Wamount);
								if (condition)
									System.out.println("transaction completed");
								else
									System.out.println("Insufficient amount");
								break;
							case 2:
								System.out.println("Enter deposite amount");
								BigDecimal Damount = scan.nextBigDecimal();
								if (Helper.amountDeposite(customerId, aacount_no, Damount))
									System.out.println("Deposition completed");
								else
									System.out.println("Deposition Failed");
						}
					}
					else
						System.out.println("invalid account number");
				}
				else
					System.out.println("invalid customer_id");
				execution();
				break;
			case 5:
				System.out.println("1.Entire customer_id deletion");
				System.out.println("2.particular account details deletion");
				int number = scan.nextInt();
				switch (number) {
					case 1:
						System.out.println("Enter customer_id");
						int customer_id = scan.nextInt();
						int status=Helper.entireDeletion(customer_id);
						if(status>0)
							System.out.println("Deleted Successfully;");
						else
							System.out.println("Deletion Failed....." +
									"invalid Customer_id");
						execution();
						break;
					case 2:
						System.out.println("Enter customer_id");
						int value = scan.nextInt();
						System.out.println("Enter account_no");
						int account_no = scan.nextInt();
						int status1 = Helper.particularAccountDeletion(value,account_no);
						if(status1==1)
							System.out.println("Deleted Successfully;");
						else
							System.out.println("Deletion Failed....." +
									"invalid Account_no");
						execution();
				}
				break;

			case 6:
				if(Helper.dbClose())
					System.out.println("your Connection was closed successfully");
				else
					System.out.println("Not closed");
				break;

			default:
				System.out.println("OOPS!.........Enter valid choice");
				execution();
		}
	}
}

