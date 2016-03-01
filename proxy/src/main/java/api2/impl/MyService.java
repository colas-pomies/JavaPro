package api2.impl;

import api2.model.IMyService;
import inject.api.annotations.Transactional;

import java.util.logging.Logger;

/**
 * Created by Patataa on 26/01/2016.
 */

@Transactional
public class MyService implements IMyService {

    protected Logger logger = Logger.getGlobal();
    public void doSomething() {
        logger.info(this.getClass() + " : DoSomething");
    }
}
