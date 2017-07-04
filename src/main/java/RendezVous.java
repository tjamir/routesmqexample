import br.ufpe.cin.routesmq.RoutesMQ;
import br.ufpe.cin.routesmq.distribution.Configuration;

import java.io.IOException;

/**
 * Created by tjamir on 7/4/17.
 */
public class RendezVous {

    public static void main(String [] args) throws IOException {
        RoutesMQ.create(new Configuration().setPort(9000));
    }
}
