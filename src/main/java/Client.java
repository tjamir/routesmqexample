import br.ufpe.cin.routesmq.RoutesMQ;
import br.ufpe.cin.routesmq.distribution.Configuration;
import br.ufpe.cin.routesmq.distribution.Marshaller;
import br.ufpe.cin.routesmq.distribution.Seed;
import br.ufpe.cin.routesmq.distribution.message.ServiceDestination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by tjamir on 7/4/17.
 */
public class Client {


    public static void main(String [] args) throws IOException {

        RoutesMQ routesMQ=RoutesMQ.create(new Configuration().addSeed(new Seed("127.0.0.1", 9000)));

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Digite o nome do arquivo ");

        String fileName = reader.readLine();


        System.out.print("Digite o conteudo do arquivo");
        String fileContent = reader.readLine();

        byte[] data= fileContent.getBytes();

        StorageMessage storageMessage = new StorageMessage();
        storageMessage.setPut(true);
        storageMessage.setFileName(fileName);
        storageMessage.setData(data);

        routesMQ.sendMessage(new Marshaller().marshall(storageMessage), new ServiceDestination(StorageService.getService()));

        reader.close();

    }
}
