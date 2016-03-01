package api2.impl;

import api2.model.IMyService;
import inject.api.annotations.Transactional;

import java.util.logging.Logger;

@Transactional
public class MyService implements IMyService {

    protected Logger logger;


    protected int value;

    public MyService() {
        value = 0;
        logger = Logger.getGlobal();
    }

    @Override
    @Transactional
    public void doSomething() {
        logger.info(this.getClass() + " : DoSomething");
    }

    @Override
    public void editSomething(final int value) {
        this.value = value;
    }

    @Override
    public void editSomethingNotTransactional(int value) {
        this.value = value;
    }

    @Override
    public void editAndThrowSomething(final int value) {
        this.value = value;
        throw new RuntimeException("EditAndThrowSometing");
    }

    @Override
    public int getValue() {
        return this.value;
    }

}
