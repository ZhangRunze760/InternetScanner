package fun.jsserver.internetscanner;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InternetScanner implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onInitialize() {
        int port = 3465;
        JavaServerThreaded server = new JavaServerThreaded(port);
        server.start();
    }
}
