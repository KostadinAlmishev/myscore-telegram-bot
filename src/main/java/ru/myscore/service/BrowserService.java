package ru.myscore.service;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.concurrent.Semaphore;

public class BrowserService implements AutoCloseable {

    private long waitAfterWait = 10 * 1000;
    private ChromeDriver chromeDriver = null;

    private String urlAfterReloading = "";

    private final Semaphore mutex = new Semaphore(1);

    public BrowserService(String url) {
        configDriver();
        this.urlAfterReloading = url;
    }

    public long getWaitAfterWait() {
        return waitAfterWait;
    }

    public void setWaitAfterWait(long waitAfterWait) {
        this.waitAfterWait = waitAfterWait;
    }

    public String getUrlAfterReloading() {
        return urlAfterReloading;
    }

    public void setUrlAfterReloading(String urlAfterReloading) {
        this.urlAfterReloading = urlAfterReloading;
    }

    public String getSource(String url) {
        synchronized (this) {
            try {
                chromeDriver.get(url);
                try {
                    chromeDriver.wait(waitAfterWait);
                } catch (InterruptedException e) { e. printStackTrace(); }

                return chromeDriver.getPageSource();
            } catch (Exception e) {
                reload();
            }
            return chromeDriver.getPageSource();
        }
    }

    public String getCurrSource() {
        synchronized (this) {
            try {
                return chromeDriver.getPageSource();
            } catch (Exception e) {
                reload();
            }

            return chromeDriver.getPageSource();
        }
    }

    public void reload() {
        synchronized (this) {
            close();
            configDriver();
            // chromeDriver.get(urlAfterReloading);
            try {
                // `chromeDriver.wait(waitAfterWait);
                chromeDriver.get(urlAfterReloading);
                Thread.sleep(this.waitAfterWait);
            } catch (InterruptedException ignored) {}
        }
    }

    @Override
    public void close() {

        if (chromeDriver != null) {
            try {
                chromeDriver.close();
            } catch (Exception ignored) {}

            try {
                Runtime.getRuntime().exec("pkill chrome");
            } catch(IOException ignored) { }

            chromeDriver = null;
        }
    }

    private void configDriver() {
        String pathToChromeDriver = "lib/chromedriver";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");

        chromeDriver = new ChromeDriver(options);
    }
}
