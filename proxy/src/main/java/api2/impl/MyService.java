package api2.impl;

import api2.model.IMyService;
import inject.api.annotations.Transactional;

import java.util.logging.Logger;

public class MyService implements IMyService {

    protected Logger logger = Logger.getGlobal();


    protected int value;

    public MyService() {
        value = 0;
    }

    @Override
    @Transactional
    public void doSomething() {
        logger.info(this.getClass() + " : DoSomething");
    }

    @Override
    @Transactional
    public void editSomething(final int value) {
        this.value = value;
    }

    @Override
    public void editSomethingNotTransactional(int value) {
        this.value = value;
    }

    @Override
    @Transactional
    public void editAndThrowSomething(final int value) {
        this.value = value;
        throw new RuntimeException("EditAndThrowSometing");
    }

    @Override
    public int getValue() {
        return this.value;
    }

}
