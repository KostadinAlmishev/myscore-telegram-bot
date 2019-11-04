package ru.myscore.service;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class BrowserService implements AutoCloseable {

    private long waitAfterWait = 10 * 1000;
    private ChromeDriver chromeDriver = null;

    public BrowserService() {
        configDriver();
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
            chromeDriver.navigate().refresh();
            try {
                chromeDriver.wait(waitAfterWait);
            } catch (InterruptedException ignored) {}
        }
    }

    @Override
    public void close() throws Exception {

        if (chromeDriver != null) {
            // Don't close browser
            chromeDriver.quit();
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
