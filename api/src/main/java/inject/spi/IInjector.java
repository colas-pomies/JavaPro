package inject.spi;

/**
 * Created by colas pomiès on 19/01/2016.
 */
public interface IInjector {
    void inject(Object instance);
    <T> IInjector bind(Class<T> classInterface, Class<? extends T> classImplementation);
}
