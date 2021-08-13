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


    public static void caseNewUser(ArrayList<Object> inputList){
        QueryHandler customerToken = new QueryHandler();
        ArrayList<Integer> idList = customerToken.customerInsertion(inputList);
        int j=0;
        for(int i=idList.size()/2;i< idList.size();i++){
            CustomerInfo customerObject = (CustomerInfo) inputList.get(j);
            customerObject.setCustomerId(idList.get(i));//**************
            MapHandler.OBJECT.customerMapper(customerObject);
            CustomerInfo obj = MapHandler.OBJECT.retriveCustomerDetails().get(idList.get(i));
            System.out.println(obj+"details was inserted succesfully;");
            j+=2;
        }
        int k=0;
        int totalAccountRows =0;
        for(int i=1;i<inputList.size();i+=2){
            AccountInfo accountObject = (AccountInfo) inputList.get(i);
            accountObject.setCustomer_id(idList.get(k));//******************
            int rows = customerToken.accountInsertion(accountObject);
            totalAccountRows+=rows;
            if(totalAccountRows==inputList.size()/2){
                System.out.println("account details are inserted Succesfully");
            }
            MapHandler.OBJECT.accountMapper(accountObject);
            k++;
        }

        try{
            customerToken.customerRetrival();
        }catch(Exception e){
            e.printStackTrace();
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
