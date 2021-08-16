package javaDatabaseDemo;

import java.util.HashMap;

public enum MapHandler {
    OBJECT;
    private HashMap<Integer,HashMap<Integer, AccountInfo>>accountDetailsMap = new HashMap<>();
    private HashMap<Integer, CustomerInfo>customerDetailsMap = new HashMap<>();

    public void accountMapper(HashMap<Integer,HashMap<Integer, AccountInfo>> accountMap) {
        accountDetailsMap = accountMap;
    }

    public void customerMapper(HashMap<Integer, CustomerInfo> customeMap) {
        customerDetailsMap=customeMap;
    }
    public void accountMapper(AccountInfo account) {
        int customerId=account.getCustomer_id();
        HashMap<Integer,AccountInfo> accountHashMap=accountDetailsMap.get(customerId);
        if(accountHashMap==null){
            accountHashMap=new HashMap<>();
            accountDetailsMap.put(customerId,accountHashMap);
        }
        accountHashMap.put(account.getAccount_no(),account);
    }
    public void customerMapper(CustomerInfo customer){
        int customer_id = customer.getCustomerId();
        customerDetailsMap.put(customer_id,customer);
    }

    public  HashMap<Integer, CustomerInfo> retriveCustomerDetails() {
        return customerDetailsMap;
    }
    public  HashMap<Integer,HashMap<Integer, AccountInfo>> retriveAccountDetails() {
        return accountDetailsMap;
    }
    public void customerDeletion(int id){
        customerDetailsMap.remove(id);
    }
    public void accountDeletion(int id){
        accountDetailsMap.remove(id);
    }

}
