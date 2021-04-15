package hu.holyoil.repository;

import hu.holyoil.resource.AbstractBaseResource;

public class ResourceBaseRepository extends AbstractBaseRepository<AbstractBaseResource> {

    private ResourceBaseRepository() {}

    private static ResourceBaseRepository resourceBaseRepository;

    public static ResourceBaseRepository GetInstance() {

        if (resourceBaseRepository == null) {
            resourceBaseRepository = new ResourceBaseRepository();
        }

        return resourceBaseRepository;

    }

}
