package inject.examples;

import inject.api.annotations.Inject;
import inject.spi.InjectorFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class HardCodedInjection {

    @Inject
    MyService service;

    @Before
    public void before() {
        InjectorFactory.createInjector().inject(this);
    }

    @Test
    public void test()
    {
        assertThat(service).isInstanceOf(MyServicePref.class).hasFieldOrProperty("entityManager");

        service.doSomthing();

    }
}