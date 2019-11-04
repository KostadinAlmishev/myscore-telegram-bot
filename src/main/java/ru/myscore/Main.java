package ru.myscore;

import ru.myscore.config.Configuration;

public class Main {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        // No need of new thread)
        conf.run();
        // TODO close chrome driver
    }
}
