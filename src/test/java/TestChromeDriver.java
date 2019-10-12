import org.jsoup.nodes.Element;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestChromeDriver {
    private WebDriver driver;
/*
    @Before
    @Ignore
    public void setUp() {
        String pathToChromeDriver = "lib/chromedriver";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);

        driver = new ChromeDriver();
    }

    @After
    @Ignore
    public void tearDown() {
        driver.close();
    }

 */

    @Test
    @Ignore
    public void testConnection() {
        driver.navigate().to("http://google.com");
        assert(driver.getPageSource().length() > 0);
    }

    @Test
    @Ignore
    public void testElement() {
        String html = "<div id=\"g_3_ELk02Zb4\" title=\"Подробности матча!\" class=\"event__match event__match--live event__match--twoLine\">\n" +
                " <div class=\"event__check\"></div>\n" +
                " <div class=\"event__stage\">\n" +
                "  Перерыв\n" +
                " </div>\n" +
                " <div class=\"event__participant event__participant--home\">\n" +
                "  Фуэрса Рехия\n" +
                " </div>\n" +
                " <div class=\"event__participant event__participant--away\">\n" +
                "  Ураканес\n" +
                " </div>\n" +
                " <div class=\"event__score event__score--home\">\n" +
                "  79\n" +
                " </div>\n" +
                " <div class=\"event__score event__score--away\">\n" +
                "  75\n" +
                " </div>\n" +
                " <div class=\"event__part event__part--home event__part--1\">\n" +
                "  31\n" +
                " </div>\n" +
                " <div class=\"event__part event__part--away event__part--1\">\n" +
                "  14\n" +
                " </div>\n" +
                " <div class=\"event__part event__part--home event__part--2\">\n" +
                "  15\n" +
                " </div>\n" +
                " <div class=\"event__part event__part--away event__part--2\">\n" +
                "  26\n" +
                " </div>\n" +
                " <div class=\"event__part event__part--home event__part--3\">\n" +
                "  24\n" +
                " </div>\n" +
                " <div class=\"event__part event__part--away event__part--3\">\n" +
                "  20\n" +
                " </div>\n" +
                " <div class=\"event__part event__part--home event__part--4\">\n" +
                "  9\n" +
                " </div>\n" +
                " <div class=\"event__part event__part--away event__part--4\">\n" +
                "  15\n" +
                " </div>\n" +
                " <span class=\"event__live--icon icon icon--live active active-bet\" data-bookmaker-id=\"453\"></span>\n" +
                "</div>";

        Element el = new Element(html);
        String a_participant = el.selectFirst("div.event__participant--home").ownText();
        String b_participant = el.selectFirst("div.event__participant--away").ownText();

        // int a_firstQuarter = Integer.parseInt(el.selectFirst("div.event__part--home.event__part--1").ownText());
        // int b_firstQuarter = Integer.parseInt(el.selectFirst("div.event__part--home.event__part--2").ownText());

        // int a_secondQuarter = Integer.parseInt(el.selectFirst("div.event__part--home.event__part--2").ownText());
        // int b_secondQuarter = Integer.parseInt(el.selectFirst("div.event__part--home.event__part--2").ownText());

        // int a_thirdQuarter = Integer.parseInt(el.selectFirst("div.event__part--home.event__part--3").ownText());
        // int b_thirdQuarter = Integer.parseInt(el.selectFirst("div.event__part--home.event__part--3").ownText());

        System.out.println(a_participant);
        System.out.println(b_participant);

        assert(a_participant.equals("Фуэрса Рехия"));
        assert(b_participant.equals("Ураканес"));
    }
}
