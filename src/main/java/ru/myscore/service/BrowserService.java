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
        chromeDriver.get(url);
        try {
            Thread.sleep(waitAfterWait);
        } catch (InterruptedException ignored) {

        }

        return chromeDriver.getPageSource();
    }

    public String getCurrSource() {
        return chromeDriver.getPageSource();
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
