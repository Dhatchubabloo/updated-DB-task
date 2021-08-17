package javaDatabaseDemo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Helper {

    public static HashMap<Integer, CustomerInfo> customerDataStore() {
        HashMap<Integer, CustomerInfo>customerDataMap=null;
        try{
            QueryHandler hand = new QueryHandler();
            customerDataMap=hand.customerRetrival();
        }catch(Exception e){
            e.printStackTrace();
        }
        MapHandler.OBJECT.customerMapper(customerDataMap);
        return customerDataMap;
    }
    public static void accountDataStore() {
        HashMap<Integer,HashMap<Integer, AccountInfo>>accountDataMap=null;
        try {
            QueryHandler db = new QueryHandler();
            accountDataMap=db.accountRetrival();
        }catch(Exception e){
            e.printStackTrace();
        }
        MapHandler.OBJECT.accountMapper(accountDataMap);
    }

    public static HashMap<String,String> caseNewUser(ArrayList<Object> inputList,int size){
        QueryHandler customerToken = new QueryHandler();
        HashMap<String,String>insertionStatus = new HashMap();
        ArrayList<Integer> idList = customerToken.customerInsertion(inputList);
        if(inputList.size()==idList.size()) {
            dataInsertion(inputList,size,idList,insertionStatus);
        }
        else{
            int index;
            int count=0;
            for(int i=0;i< idList.size();i++){
                if(idList.get(i)<0){
                    if(count==0)
                        index =(i*2);
                    else
                        index=(i*2)-(count*2);
                    CustomerInfo details = (CustomerInfo)inputList.get(index);
                    String item ="Name :"+details.getName()+" customer_id:"+details.getCustomerId();
                    insertionStatus.put(item,"Fail(Customer details)");
                    inputList.remove(index);
                    inputList.remove(index);
                    count++;
                }
            }
            dataInsertion(inputList,size,idList,insertionStatus);
        }
        try{
            customerToken.customerRetrival();
        }catch(Exception e){
            e.printStackTrace();
        }
        return insertionStatus;
    }
    public static void dataInsertion(ArrayList<Object> inputList,int size,
                                     ArrayList<Integer> idList,HashMap<String,String>insertionStatus){
        QueryHandler accountToken = new QueryHandler();
        int j=0,k=1;
        for (int i =size;i < idList.size(); i++) {
            CustomerInfo customerObject = (CustomerInfo) inputList.get(j);
            customerObject.setCustomerId(idList.get(i));
            j += 2;
            AccountInfo accountObject = (AccountInfo) inputList.get(k);
            accountObject.setCustomer_id(idList.get(i));
            k+=2;
            ArrayList<Integer> account_no = accountToken.accountInsertion(accountObject);
            System.out.println(account_no);
            if(account_no.get(0)>0) {
                accountObject.setAccount_no(account_no.get(1));
                MapHandler.OBJECT.customerMapper(customerObject);
                MapHandler.OBJECT.accountMapper(accountObject);
                String item ="Name :"+customerObject.getName()+" customer_id:"+customerObject.getCustomerId()+" account no:"+accountObject.getAccount_no();
                insertionStatus.put(item, "Success");
            }
            else {
                String item ="Name :"+customerObject.getName()+" customer_id:"+customerObject.getCustomerId();
                insertionStatus.put(item, "Fail(account details)");
                int customer_id = customerObject.getCustomerId();
                QueryHandler1 db = new QueryHandler1();
                db.customerDeletion(customer_id);
            }
        }
        TreeMap<String,String>map = new TreeMap<>(insertionStatus);
    }
    public static ArrayList<Integer> caseExistingUser(AccountInfo in) {
        QueryHandler db = new QueryHandler();
        ArrayList<Integer>accountIdList = db.accountInsertion(in);
        if(accountIdList.get(0)>0) {
            in.setAccount_no(accountIdList.get(1));
            MapHandler.OBJECT.accountMapper(in);
        }
        return accountIdList;
    }
    public static int entireDeletion(int id){
        QueryHandler1 db = new QueryHandler1();
        int status = db.customerUpdation(id);
        MapHandler.OBJECT.customerDeletion(id);
        MapHandler.OBJECT.accountDeletion(id);
        return status;
    }
    public static int particularAccountDeletion(int id,int account_no){
        QueryHandler1 db = new QueryHandler1();
        int status = db.accountUpdation(account_no);
        HashMap<Integer,HashMap<Integer, AccountInfo>>accountMap =MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo>inner = accountMap.get(id);
        inner.remove(account_no);
        return status;
    }
    public static boolean amountWithdrawl(TransactionInfo info,String type){
        BigDecimal balance = getBalanceAmount(info.getCustomer_id(),info.getAccount_no());
        int rate =balance.compareTo(info.getAmount());
        if(rate>=0) {
            BigDecimal totalAmount = balance.subtract(info.getAmount());
            int status = QueryHandler1.withdrawl(info, totalAmount);
            if(status>0) {
                Helper.transData(info, type);
                MapHandler.OBJECT.balanceUpdation(info,totalAmount);
                return true;
            }else
                return false;
        }
        else
            return false;
    }
    public static boolean amountDeposite(TransactionInfo info,String type){
        BigDecimal balance = getBalanceAmount(info.getCustomer_id(),info.getAccount_no());
        BigDecimal totalAmount = balance.add(info.getAmount());
        int rate = QueryHandler1.deposite(info,totalAmount);
        if(rate>0){
            Helper.transData(info,type);
            MapHandler.OBJECT.balanceUpdation(info,totalAmount);
            return true;
        }
        else
            return false;
    }
    public static void transData(TransactionInfo info,String type){
        QueryHandler1.transInsertion(info,type);
    }
    public static BigDecimal getBalanceAmount(int customer_id, int account_no){
        HashMap<Integer,HashMap<Integer, AccountInfo>>finalAccountMap=MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo> temp = finalAccountMap.get(customer_id);
        AccountInfo info = temp.get(account_no);
        BigDecimal balance = info.getBalance();
        return balance;
    }
    public static boolean idCheck(int id) {
        if(MapHandler.OBJECT.retriveCustomerDetails().containsKey(id)){
            return true;
        }
        else
            return false;
    }
    public static boolean accountCheck(int id,int account_no){
        HashMap<Integer,HashMap<Integer, AccountInfo>>finalAccountMap=MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo> temp = finalAccountMap.get(id);
        if(temp.containsKey(account_no))
            return true;
        else
            return false;
    }
    public static boolean  dbClose(){
        QueryHandler qh = new QueryHandler();
        boolean status = qh.closingProcess();
        return status;
    }
    public static HashMap<Integer, CustomerInfo> helperThreeCustomer(){
        HashMap<Integer, CustomerInfo>finalCustomerMap=MapHandler.OBJECT.retriveCustomerDetails();
        return finalCustomerMap;
    }
    public static  HashMap<Integer, AccountInfo> helperThreeAccount(int id){
        HashMap<Integer,HashMap<Integer, AccountInfo>>finalAccountMap=MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo> temp = finalAccountMap.get(id);
            return temp;
    }
}
