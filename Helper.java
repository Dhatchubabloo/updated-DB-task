package javaDatabaseDemo;

import java.util.ArrayList;
import java.util.HashMap;

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
                    insertionStatus.put(details.getName(),"Fail");
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
            MapHandler.OBJECT.customerMapper(customerObject);
            j += 2;
            AccountInfo accountObject = (AccountInfo) inputList.get(k);
            accountObject.setCustomer_id(idList.get(i));
            ArrayList<Integer> account_no = accountToken.accountInsertion(accountObject);
            accountObject.setAccount_no(account_no.get(1));
            if(account_no.get(0)>0)
                MapHandler.OBJECT.accountMapper(accountObject);
                k += 2;
                CustomerInfo obj = MapHandler.OBJECT.retriveCustomerDetails().get(idList.get(i));
                insertionStatus.put(obj.getName(), "Success");
        }
    }
    public static ArrayList<Integer> caseExistingUser(AccountInfo in) {
        QueryHandler db = new QueryHandler();
        ArrayList<Integer>accountIdList = db.accountInsertion(in);
        in.setAccount_no(accountIdList.get(1));
        MapHandler.OBJECT.accountMapper(in);
        return accountIdList;
    }
    public static int entireDeletion(int id){
        QueryHandler1 db = new QueryHandler1();
        int status = db.customerDeletion(id);
        HashMap<Integer, CustomerInfo>customerMap = MapHandler.OBJECT.retriveCustomerDetails();
        customerMap.remove(id);
        HashMap<Integer,HashMap<Integer, AccountInfo>>accountMap =MapHandler.OBJECT.retriveAccountDetails();
        accountMap.remove(id);
        return status;
    }
    public static int particularAccountDeletion(int id,int account_no){
        QueryHandler1 db = new QueryHandler1();
        int status = db.accountDeletion(account_no);
        HashMap<Integer,HashMap<Integer, AccountInfo>>accountMap =MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo>inner = accountMap.get(id);
        inner.remove(account_no);
        return status;
    }
    public static boolean helperThree(int id) {
        if(MapHandler.OBJECT.retriveCustomerDetails().containsKey(id)){
            return true;
        }
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
