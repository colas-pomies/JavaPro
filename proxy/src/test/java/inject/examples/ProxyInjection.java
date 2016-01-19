package inject.examples;


import inject.api.annotations.Inject;
import inject.spi.InjectorFactory;
import org.junit.Before;
import org.junit.Test;

public class ProxyInjection {

    @Inject
    MyService service;

    @Before
    public void before() {
        InjectorFactory.createInjector().bind(MyService.class, MyServiceImpl.class).inject(this);
    }

    @Test
    public void test()
    {
        assert(service instanceof MyServiceImpl);
    }
}
