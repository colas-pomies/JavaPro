package inject.examples;

import inject.api.annotations.Inject;
import inject.api.annotations.Prefered;

import java.util.logging.Level;
import java.util.logging.Logger;

@Prefered
public class MyServicePref implements MyService {

    private final static Logger LOGGER = Logger.getLogger(MyServicePref.class.getName());

    @Inject
    EntityManager entityManager;

    public void doSomthing() {
        LOGGER.log(Level.INFO, "MyServicePref.doSomething()");
        entityManager.find();
    }
}