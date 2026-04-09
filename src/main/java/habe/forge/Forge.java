package habe.forge;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public class Forge implements ModInitializer {
    public static final String MOD_ID = "forge";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);





    @Override
    public void onInitialize() {
        Database.connect();
        ModItems.initialize();
        ModCommands.initialize();
        LOGGER.info("[FORGE] Reingeladen! c:");

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> Database.disconnect());
    }
	}