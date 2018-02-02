package cricket.scoring;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ScoreboardPage {

    @FindBy(id="backspace")
    private WebElement backspaceButton;

    @FindBy(id="score")
    private WebElement scoreField;

    @FindBy(id="scorecard")
    private WebElement scorecardField;
    
    private final WebDriver driver;


    public ScoreboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void eraseLastEntry() {
        backspaceButton.click();
    }

    public Result enterScore(char entry) {
        By key;
        if (entry == '.') {
            key = By.id("key-dot");
        } else {
            key = By.id("key" + entry);
        }
        driver.findElement(key).click();
        return getResult();
    }

    public Result getResult() {
        String score = scoreField.getText();
        String card = scorecardField.getText();
        return new Result(card, score);
    }
}
