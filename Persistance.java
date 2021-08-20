package javaDatabaseDemo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public interface Persistance {

    ArrayList<Integer> customerInsertion(ArrayList<Object> list) throws ExceptionHandling;

    HashMap<Integer, CustomerInfo> customerRetrival() throws ExceptionHandling;

    ArrayList<Integer> accountInsertion(AccountInfo accountIn) throws ExceptionHandling;

    HashMap<Integer,HashMap<Integer, AccountInfo>> accountRetrival() throws ExceptionHandling;

    void customerDeletion(int id);

    int customerActivation(int id) throws ExceptionHandling;

    int accountActivation(int account_no) throws ExceptionHandling;

    int dataUpdation(AccountInfo info,String type) throws ExceptionHandling;

    void passwordSetter(CustomerInfo info);

    HashMap<Integer,HashMap<Integer, String>> wholeAccountDetails() throws ExceptionHandling;

    int transcation(TransactionInfo info,BigDecimal amount);

    void transInsertion(TransactionInfo info,String type);

    boolean closingProcess() throws ExceptionHandling;

}
