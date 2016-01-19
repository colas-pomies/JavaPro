package inject.spi;

import inject.api.annotations.Inject;
import inject.api.annotations.Prefered;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.reflections.ReflectionUtils.getAllFields;

/**
 * Created by colas pomiès on 19/01/2016.
 */
public class BootstrapInjector extends AbstractInjector {

    private final static Logger LOGGER = Logger.getLogger(BootstrapInjector.class.getName());

    public <T> BootstrapInjector() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("inject"))
                .setScanners(new FieldAnnotationsScanner(), new SubTypesScanner()));

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

                this.bind(classInterface, classToBind);
            }
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
                    f.set(instance, obj);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
