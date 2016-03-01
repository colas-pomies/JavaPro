package api2.proxy;

import api2.impl.MyService;

/**
 * Created by Patataa on 19/01/2016.
 */
public class MyServiceProxy extends MyService {

    @Override
    public void doSomething() {
        logger.info(this.getClass() + " : Before");
        super.doSomething();
        logger.info(this.getClass() + " : After");
    }
}
