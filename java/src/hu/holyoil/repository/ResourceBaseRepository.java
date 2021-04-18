package hu.holyoil.repository;

import hu.holyoil.resource.AbstractBaseResource;

/**
 * <i>AbstractBaseResource</i>-öket tároló tároló. Singleton osztály.
 * */
public class ResourceBaseRepository extends AbstractBaseRepository<AbstractBaseResource> {

    /**
     * Mivel siungleton, a konstruktora privát.
     * */
    private ResourceBaseRepository() {}

    /**
     * Belső instance.
     * */
    private static ResourceBaseRepository resourceBaseRepository;

    /**
     * Visszaadja a belső instance-t.
     * */
    public static ResourceBaseRepository GetInstance() {

        if (resourceBaseRepository == null) {
            resourceBaseRepository = new ResourceBaseRepository();
        }

        return resourceBaseRepository;

    }

}
