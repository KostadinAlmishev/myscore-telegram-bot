package ru.myscore.service;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

public class BrowserService implements AutoCloseable {

    private long waitAfterWait = 10 * 1000;
    private ChromeDriver chromeDriver = null;

    private String urlAfterReloading = "";

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

    public String getSource(String url) {
        synchronized (chromeDriver) {
            try {
                chromeDriver.get(url);
                try {
                    chromeDriver.wait(waitAfterWait);
                } catch (InterruptedException ignored) { }

                return chromeDriver.getPageSource();
            } catch (Exception e) {
                reload();
            }
        }
        return "";
    }

    public String getCurrSource() {
        synchronized (chromeDriver) {
            try {
                return chromeDriver.getPageSource();
            } catch (Exception e) {
                reload();
            }
        }
        return "";
    }

    public void reload() {
        synchronized (chromeDriver) {
            close();
            configDriver();
            chromeDriver.get(urlAfterReloading);
            try {
                chromeDriver.wait(waitAfterWait);
            } catch (InterruptedException ignored) {}
        }
    }

    @Override
    public void close() {

        if (chromeDriver != null) {
            chromeDriver.quit();

            try {
                Runtime.getRuntime().exec("pkill chrome");
            } catch(IOException ignored) { }
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
