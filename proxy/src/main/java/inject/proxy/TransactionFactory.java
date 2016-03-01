package inject.proxy;

import java.lang.reflect.Proxy;
/**
 * Created by Patataa on 26/01/2016.
 */
public class TransactionFactory {

    //TODO: custom this method to accept different kind of transactions
    public static Object newTransaction(Object obj) {
        return Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new Transaction(obj)
        );
    }
}
