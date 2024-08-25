package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static ConfigManager configManager;
    private static final Properties prop = new Properties();
    private ConfigManager() throws IOException {
        String sp= System.getProperty("user.dir")+"\\src\\main\\resources\\config.properties";
        //InputStream is = SingletonClass.class.getResourceAsStream("xyz.properties");
        FileInputStream fs=new FileInputStream(sp);
        prop.load(fs);
    }

    public static ConfigManager getInstance() throws IOException {
        if(configManager==null) {
            configManager=new ConfigManager();
            return configManager;
        }
        return configManager;
    }

    public String getString(String key){
        return prop.getProperty(key);
    }
}
