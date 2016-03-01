package inject.spi;

import inject.api.annotations.Transactional;
import inject.api.annotations.Inject;
import inject.api.annotations.Prefered;
import inject.proxy.TransactionFactory;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxyInjector extends AbstractInjector {

    private final static Logger LOGGER = Logger.getLogger(ProxyInjector.class.getName());

    protected <T> void bindFields(Reflections reflections) throws InstantiationException, IllegalAccessException {
        LOGGER.log(Level.INFO,"Boostrap Injection");
        Set<Field> fields = reflections.getFieldsAnnotatedWith(Inject.class);
        for (Field f : fields) {
            Class<T> classInterface = (Class<T>) f.getType();
            if (map.get(classInterface) == null) {
                Set<Class<? extends T>> classImplementations = (Set<Class<? extends T>>) reflections.getSubTypesOf(classInterface);

                if (classImplementations.size() == 0) {
                    throw new RuntimeException("Unable to get a subclass of type " + classInterface.getName());
                }

                Class<? extends T> classToBind = null;

                for (Class<? extends T> classImplementation : classImplementations) {
                    if (classImplementation.isAnnotationPresent(Prefered.class)) {
                        classToBind = classImplementation;
                    }
                }

                if (classToBind == null) {
                    classToBind = (Class<? extends T>) classImplementations.toArray()[0];
                }

                if (classToBind == null) {
                    throw new RuntimeException("Several implementations were found. Use @Prefered to choose one");
                }
                //defineProxy(classToBind);
                this.bind(classInterface, classToBind);
            }
        }
    }

    protected void defineProxy(Object object) throws IllegalAccessException, InstantiationException {
        LOGGER.log(Level.INFO,"Defining the proxy for the class " + Inject.class.getName());

        Reflections reflections = new Reflections('');

        reflections.getMethodsAnnotatedWith(Transactional.class);

        if(object.getClass().isAnnotationPresent(Transactional.class) || object.getClass().getMethods()[0].isAnnotationPresent()) {
            LOGGER.log(Level.INFO,"Has Transactionnal" + Inject.class.getName());
            TransactionFactory.newTransaction(object);
        }

    }

    public ProxyInjector() {

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("inject"))
                .setScanners(new FieldAnnotationsScanner(), new SubTypesScanner()));
        try {
            bindFields(reflections);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

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
                    Object obj = classImplementation.newInstance();
                    inject(obj);

                    defineProxy(obj);

                    f.set(instance, obj);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
