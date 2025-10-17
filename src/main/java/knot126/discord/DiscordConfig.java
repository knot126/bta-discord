package knot126.discord;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Properties;

import net.fabricmc.loader.api.FabricLoader;

public class DiscordConfig {
    public static String getConfigPath() {
        return FabricLoader.getInstance().getGameDir().toString() + "/config/discord.conf";
    }

    public static Properties loadProperties() {
        try (InputStream in = new FileInputStream(getConfigPath())) {
            Properties p = new Properties();
            p.load(in);
            in.close();
            return p;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void saveProperties(Properties p) throws FileNotFoundException, IOException {
        OutputStream out = new FileOutputStream(getConfigPath());
        p.store(out, "Updating config");
        out.close();
    }

    public static String getWithFallback(String option, String fallback) {
        Properties p = loadProperties();
        try {
            return p.getProperty(option);
        }
        catch (Exception e) {
            return fallback;
        }
    }

    public static String get(String option) {
        return getWithFallback(option, null);
    }

    public static void set(String option, String value) {
        try {
            Properties p = loadProperties();
            
            if (p == null) {
                p = new Properties();
            }

            p.setProperty(option, value);
            
            saveProperties(p);
        }
        catch (Exception e) {
            DiscordIntegration.LOGGER.error("Failed to save config!", e);
        }
    }
}
