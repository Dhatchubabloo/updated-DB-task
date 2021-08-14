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


    public static void caseNewUser(ArrayList<Object> inputList,int size){
        QueryHandler customerToken = new QueryHandler();
        ArrayList<Integer> idList = customerToken.customerInsertion(inputList);
        if(inputList.size()==idList.size()) {
            dataInsertion(inputList,size,idList);
        }
        else{
            int s=0;
            for(int i=0;i< idList.size();i++){
                if(idList.get(i)<0){
                    s =(i*2)-s;
                    CustomerInfo details = (CustomerInfo)inputList.get(s);
                    System.out.println(details.getName() + " details was not inserted");
                    inputList.remove(s);
                    inputList.remove(s);
                }
            }
            dataInsertion(inputList,size,idList);
        }
        try{
            customerToken.customerRetrival();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void dataInsertion(ArrayList<Object> inputList,int size,ArrayList<Integer> idList){
        QueryHandler accountToken = new QueryHandler();
        int j=0,k=1;
        for (int i =size;i < idList.size(); i++) {
            CustomerInfo customerObject = (CustomerInfo) inputList.get(j);
            customerObject.setCustomerId(idList.get(i));
            MapHandler.OBJECT.customerMapper(customerObject);
            j += 2;
            AccountInfo accountObject = (AccountInfo) inputList.get(k);
            accountObject.setCustomer_id(idList.get(i));
            int account_no = accountToken.accountInsertion(accountObject);
            accountObject.setAccount_no(account_no);
            MapHandler.OBJECT.accountMapper(accountObject);
            k += 2;
            CustomerInfo obj = MapHandler.OBJECT.retriveCustomerDetails().get(idList.get(i));
            System.out.println(obj.getName() + " details was inserted succesfully;");
        }

    }
    public static void caseExistingUser(AccountInfo in) {
        QueryHandler db = new QueryHandler();
        db.accountInsertion(in);
        MapHandler.OBJECT.accountMapper(in);
    }

    public static boolean helperThree(int id) {
        if(MapHandler.OBJECT.retriveAccountDetails().containsKey(id)){
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
