package inject.spi;


import inject.api.annotations.Inject;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Injector extends AbstractInjector {

    private final static Logger LOGGER = Logger.getLogger(Injector.class.getName());

    @Override
    public void inject(Object instance) {
        LOGGER.log(Level.INFO,"Getting all the fields annotating with " + Inject.class.getName());
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Inject.class)) {
                Class<?> classInterface = f.getType();
                Class<?> classImplementation = map.get(classInterface);

                LOGGER.log(Level.INFO,"Injecting instance of " + classImplementation.getName() + " in " + instance.getClass().getName());
                try {
                    f.setAccessible(true);
                    f.set(instance, classImplementation.newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
