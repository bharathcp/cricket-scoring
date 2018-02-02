package cricket.scoring;

import java.io.IOException;
import java.net.URL;

import org.concordion.api.extension.Extension;
import org.concordion.ext.ScreenshotExtension;
import org.concordion.ext.selenium.SeleniumScreenshotTaker;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(ConcordionRunner.class)
public class BasicScoring  {
	
    @Extension
    public ScreenshotExtension screenshotExtension = new ScreenshotExtension();
    
	private WebDriver driver;
    private String url;

    private ScoreboardPage scoreboardPage;

    @Before
    public void setup() throws IOException {
        driver = new ChromeDriver();
        screenshotExtension.setScreenshotTaker(new SeleniumScreenshotTaker(driver));
        url = readURLFromSystemProperties();
        if (url == null) {
            url = getLocalFileURL();
        }
    }

    @After
    public void tearDown() {
        driver.close();
    }

    public void startInnings() {
        System.out.println("Opening " + url);
	    driver.get(url);
	    scoreboardPage = new ScoreboardPage(driver);
	}
	
    public void startInnings(String entry) {
        startInnings();
        enterScore(entry);
    }
    
    public Result enterScore(String entry) {
        Result score = null;
        for (char c : entry.trim().toCharArray()) {
            score = enterScore(c);
        }
        return score;
    }
    
    public Result enterScore(char entry) {
        return scoreboardPage.enterScore(entry);
    }
    
    public void eraseLastEntry() {
        scoreboardPage.eraseLastEntry();
    }
    
    public void eraseLastnEntries(int n) {
        for (int i=0; i<n; i++) {
            eraseLastEntry();
        }
    }
    
    public Result getResult() {
        return scoreboardPage.getResult();
    }

    private String readURLFromSystemProperties() {
        return System.getProperty("cricket.url");
    }
    
    private String getLocalFileURL() {
        URL pageUrl = this.getClass().getClassLoader().getResource("calc.html");
        if (pageUrl == null) {
            throw new RuntimeException("Local calc.html page not found");
        }
        return pageUrl.toString();
    }
}