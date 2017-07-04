import br.ufpe.cin.routesmq.distribution.service.ServiceDescriptor;

import java.util.UUID;

/**
 * Created by tjamir on 7/4/17.
 */
public class FileTrakerService {


    private static ServiceDescriptor serviceDescriptor;

    public static ServiceDescriptor getService(){
        if(serviceDescriptor==null){
            serviceDescriptor=new ServiceDescriptor();
            serviceDescriptor.setServiceUUid(UUID.fromString("bd3c16fd-fa23-4429-bdca-47500456cd06"));
            serviceDescriptor.setServiceName("filetracker");
            serviceDescriptor.setServiceDescription("File Tracker Service");
        }
        return serviceDescriptor;
    }
}
