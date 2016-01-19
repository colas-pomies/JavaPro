package inject.spi;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractInjector implements IInjector{

    private final static Logger LOGGER = Logger.getLogger(AbstractInjector.class.getName());

    protected Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();


    public <T> IInjector bind(Class<T> classInterface, Class<? extends T> classImplementation) {
        if (map.get(classInterface) == null) {
            try {
                LOGGER.log(Level.INFO,"Bind " + classInterface.getName() + " and " + classImplementation.getName());
                map.put(classInterface,classImplementation);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE,"Unable to bind " + classInterface.getName() + " and " + classImplementation.getName());
            }
        }
        return this;
    }


    public abstract void inject(Object instance);
}
