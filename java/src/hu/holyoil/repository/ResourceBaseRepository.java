package hu.holyoil.repository;

import hu.holyoil.resource.AbstractBaseResource;

import java.util.HashMap;

public class ResourceBaseRepository extends AbstractBaseRepository<AbstractBaseResource> {

    HashMap<String, AbstractBaseResource> resources;

    private ResourceBaseRepository() {
        resources = new HashMap<>();
    }

    private static ResourceBaseRepository resourceBaseRepository;

    public static ResourceBaseRepository GetInstance() {

        if (resourceBaseRepository == null) {
            resourceBaseRepository = new ResourceBaseRepository();
        }

        return resourceBaseRepository;

    }

}
