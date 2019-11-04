package ru.myscore;

import ru.myscore.config.Configuration;

public class Main {

    public static void main(String[] args) throws Exception {
        try (Configuration conf = new Configuration()) {
            // No need of new thread)
            conf.run();
        } catch (InterruptedException e) {
            throw e;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
