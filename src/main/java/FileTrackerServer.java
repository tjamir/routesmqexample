import br.ufpe.cin.routesmq.RoutesMQ;
import br.ufpe.cin.routesmq.distribution.Configuration;
import br.ufpe.cin.routesmq.distribution.Seed;
import br.ufpe.cin.routesmq.distribution.message.PeerDestination;
import br.ufpe.cin.routesmq.distribution.message.ServiceApplicationMessage;
import br.ufpe.cin.routesmq.distribution.service.PeerDescriptor;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tjamir on 7/4/17.
 */
public class FileTrackerServer {

    private Map<String, UUID> fileTrackerData;
    private RoutesMQ routesMQ;

    public void service() throws IOException {

        fileTrackerData=new ConcurrentHashMap<>();

        routesMQ = RoutesMQ.create(new Configuration().addSeed(new Seed("127.0.0.1", 9000)));


        routesMQ.addServiceMessageLister(FileTrakerService.getService(), this::processMessage);


    }

    private void processMessage(ServiceApplicationMessage serviceApplicationMessage) {

        System.out.println("Chegou mensagem!");
        String conent = new String(serviceApplicationMessage.getPayload());
        String[] parsed=conent.split("/");

        String operation=parsed[0];
        if("put".equals(operation)){
            String file=parsed[1];
            UUID peerId=UUID.fromString(parsed[2]);
            System.out.println("Salvando metadados: Aquivo "+file+" salvo no peer: "+peerId);
            fileTrackerData.put(file, peerId);



        }else if("get".equals(operation)){
            String file = parsed[1];
            UUID uuid = fileTrackerData.get(file);

            PeerDestination destination=new PeerDestination(serviceApplicationMessage.getSource());

            routesMQ.sendMessage(("get"+"-"+uuid.toString()).getBytes(), destination);
        }

    }

    public static void main(String [] args) throws IOException {
        new FileTrackerServer().service();
    }


}
