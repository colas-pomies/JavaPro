package inject.examples;

import inject.api.annotations.Prefered;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyServiceImpl implements MyService {

    private final static Logger LOGGER = Logger.getLogger(MyServiceImpl.class.getName());

    public void doSomthing() {
        LOGGER.log(Level.INFO, "MyServiceImpl.doSomething()");
    }
}
