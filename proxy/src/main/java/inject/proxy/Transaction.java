package inject.proxy;

import inject.api.annotations.Transactional;
import utils.Clone;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Created by Patataa on 26/01/2016.
 */
public class Transaction implements InvocationHandler {

    protected Object obj;
    protected Logger logger;
    protected Stack<Object> stateQueue;

    public Transaction(Object obj) {
        this.obj = obj;
        this.stateQueue = new Stack<>();
        this.logger = Logger.getLogger(obj.getClass().getName());
    }

    protected void commit(Method m, Object[] args) {
        logger.info("Commit : " + m.getName());

        try {
            Object tmp = obj.getClass().newInstance();

            for (Field f : obj.getClass().getFields()) {
                f.setAccessible(true);
                f.set(tmp, obj);
            }
            stateQueue.push(tmp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected void conclude(Method m, Object[] args) {
        logger.info("Proxy End : " + m.getName());
    }

    protected void rollback() {
        logger.info("Rollback : " + obj.getClass());
        obj = stateQueue.pop();
    }

    //TODO: Define a don't do for exception
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        Object result;
        logger.info("--------------Invoke");
        if(obj.getClass().isAnnotationPresent(Transactional.class) || m.isAnnotationPresent(Transactional.class)) {
            try {
                commit(m, args);

                result = m.invoke(obj, args);
            } catch (InvocationTargetException e) {
                rollback();
                throw e.getTargetException();
            } catch (Exception e) {
                rollback();
                throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
            } finally {
                conclude(m, args);
            }
        } else {
            result = m.invoke(obj, args);
        }
        return result;

    }

}