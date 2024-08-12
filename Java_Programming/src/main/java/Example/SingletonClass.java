package Example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SingletonClass {
    private static SingletonClass singletonClass;
    private static final Properties prop = new Properties();
    private SingletonClass() throws IOException {
        String sp= System.getProperty("user.dir")+"\\src\\main\\resources\\xyz.properties";
        //InputStream is = SingletonClass.class.getResourceAsStream("xyz.properties");
        FileInputStream fs=new FileInputStream(sp);
        prop.load(fs);
    }

    public static SingletonClass getInstance() throws IOException {
        if(singletonClass==null) {
            singletonClass=new SingletonClass();
            return singletonClass;
        }
        return singletonClass;
    }

    public String getString(String key){
        return prop.getProperty(key);
    }
}
