package javaDatabaseDemo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public interface Persistance {

    ArrayList<Integer> customerInsertion(ArrayList<Object> list);

    HashMap<Integer, CustomerInfo> customerRetrival() throws ExceptionHandling;

    ArrayList<Integer> accountInsertion(AccountInfo accountIn);

    HashMap<Integer,HashMap<Integer, AccountInfo>> accountRetrival();

    void customerDeletion(int id);

    int customerActivation(int id);

    int accountActivation(int account_no);

    int dataUpdation(AccountInfo info,String type);

    void passwordSetter(CustomerInfo info);

    HashMap<Integer,HashMap<Integer, String>> wholeAccountDetails();

    int transcation(TransactionInfo info,BigDecimal amount);

    void transInsertion(TransactionInfo info,String type);

    boolean closingProcess();

}
