package api2.model;

/**
 * Created by Patataa on 26/01/2016.
 */
public interface IMyService {
    public void doSomething();

    public void editSomething(final int value);

    public void editSomethingNotTransactional(final int value);

    public void editAndThrowSomething(final int value);

    int getValue();
}
