import br.ufpe.cin.routesmq.RoutesMQ;
import br.ufpe.cin.routesmq.distribution.Configuration;
import br.ufpe.cin.routesmq.distribution.Marshaller;
import br.ufpe.cin.routesmq.distribution.Seed;
import br.ufpe.cin.routesmq.distribution.message.PeerDestination;
import br.ufpe.cin.routesmq.distribution.message.ServiceApplicationMessage;
import br.ufpe.cin.routesmq.distribution.message.ServiceDestination;
import br.ufpe.cin.routesmq.distribution.service.PeerDescriptor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by tjamir on 7/4/17.
 */
public class StorageNode {
    
    private RoutesMQ routesMQ;

    private Marshaller marshaller;
    
    
    public void service() throws IOException {
        routesMQ = RoutesMQ.create(new Configuration().addSeed(new Seed("127.0.0.1", 9000)));
        routesMQ.addServiceMessageLister(StorageService.getService(), this::processMessage);
        marshaller = new Marshaller();
    }

    private void processMessage(ServiceApplicationMessage serviceApplicationMessage) {
        try {
            StorageMessage storageMessage= (StorageMessage) marshaller.unMarshall(serviceApplicationMessage.getPayload());
            if(storageMessage.isPut()){

                String fileName=storageMessage.getFileName();
                System.out.println("Salvando arquivo "+fileName);
                Files.write(Paths.get(fileName), storageMessage.getData());

                routesMQ.sendMessage(("put/"+fileName+"/"+routesMQ.getPeerId().toString()).getBytes(), new ServiceDestination(FileTrakerService.getService()));
            }else{
                PeerDescriptor descriptor=serviceApplicationMessage.getSource();
                StorageMessage reply=new StorageMessage();
                reply.setData(Files.readAllBytes(Paths.get(storageMessage.getFileName())));
                reply.setPut(false);
                routesMQ.sendMessage(marshaller.marshall(storageMessage), new PeerDestination(descriptor));

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) throws IOException {
        new StorageNode().service();
    }


}
