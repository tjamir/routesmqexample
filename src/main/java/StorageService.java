import br.ufpe.cin.routesmq.distribution.service.ServiceDescriptor;

import java.util.UUID;

/**
 * Created by tjamir on 7/4/17.
 */
public class StorageService {


    private static ServiceDescriptor serviceDescriptor;

    public static ServiceDescriptor getService() {
        if (serviceDescriptor == null) {
            serviceDescriptor = new ServiceDescriptor();
            serviceDescriptor.setServiceUUid(UUID.fromString("a1fd3bcc-cf2d-44af-bbd2-d3edf33ace0c"));
            serviceDescriptor.setServiceName("storage");
            serviceDescriptor.setServiceDescription("Storage Node Services");
        }
        return serviceDescriptor;
    }
}
