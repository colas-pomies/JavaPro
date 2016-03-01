package inject.examples;


import api2.impl.*;
import api2.model.IMyService;
import inject.api.annotations.Inject;
import inject.spi.InjectorFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

public class ProxyInjection {

    Logger log = Logger.getGlobal();
    @Inject
    IMyService service;

    @Before
    public void before() {
        InjectorFactory.createInjector().inject(this);
    }

    @Test
    public void test()
    {
        log.info("Test injector" + service.getClass());

        service.doSomething();

        assert(service instanceof api2.impl.MyService);
    }
}
