package api2.impl;

import api2.model.IMyService;
import inject.api.annotations.Transactional;

import java.util.logging.Logger;

/**
 * Created by Patataa on 26/01/2016.
 */


public class MyService implements IMyService {

    protected Logger logger = Logger.getGlobal();



    @Transactional
    public void doSomething() {
        logger.info(this.getClass() + " : DoSomething");
    }

    public void doSomethingNotTransactionnal(){
        logger.info(this.getClass() + " : DoSomethingWithoutTransactionnal");
    }
}
