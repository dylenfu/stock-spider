package org.dylenfu.spider;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.Config;
import org.dylenfu.spider.StockModule.StockModule;

public class Main {

    public static void main(String[] args) {
        Config config = ConfigFactory.load();

        StockModule module = new StockModule(config);
        module.read();
        module.write();
    }
}
