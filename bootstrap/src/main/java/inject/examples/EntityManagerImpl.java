package inject.examples;

import inject.api.annotations.Inject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by colas pomiès on 19/01/2016.
 */
public class EntityManagerImpl implements EntityManager {

    private final static Logger LOGGER = Logger.getLogger(EntityManagerImpl.class.getName());

    public void find() {
        LOGGER.log(Level.INFO, "EntityManagerImpl.find()");
    }
}
