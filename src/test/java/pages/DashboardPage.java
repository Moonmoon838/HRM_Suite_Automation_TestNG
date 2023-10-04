package pages;

import config.EmployeeModel;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DashboardPage {
    @FindBy(className = "oxd-main-menu-item--name")
    public List<WebElement> menuItems;

    @FindBy(className = "oxd-button")
    List<WebElement> buttons;

    @FindBy(className = "oxd-input")
    List<WebElement> formTextFields;

    @FindBy(className = "oxd-switch-input")
    WebElement btnSwitch;

    @FindBy(css = "[type=submit]")
    WebElement submit;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    WebElement search;

    public DashboardPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void createUser(EmployeeModel model) {
        menuItems.get(1).click();
        buttons.get(2).click();     //click add user
        formTextFields.get(1).sendKeys(model.getFirstname()); //firstname
        formTextFields.get(3).sendKeys(model.getLastname());   //lastname
        formTextFields.get(4).sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        formTextFields.get(4).sendKeys(model.getEmployeeId());   //employeeId
        btnSwitch.click();      //click login details switch
        formTextFields.get(5).sendKeys(model.getUsername());       //username
        formTextFields.get(6).sendKeys(model.getPassword());    //pass
        formTextFields.get(7).sendKeys(model.getPassword());   //confirm pass
        buttons.get(1).click();     // save data
    }

    public void createUserWithoutUsername(EmployeeModel model) {
        menuItems.get(1).click();   //click pim
        buttons.get(2).click();     //click add user
        formTextFields.get(1).sendKeys(model.getFirstname()); //firstname
        formTextFields.get(3).sendKeys(model.getLastname());   //lastname
        formTextFields.get(4).sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        formTextFields.get(4).sendKeys(model.getEmployeeId());   //employeeId
        btnSwitch.click();      //click login details switch
        formTextFields.get(6).sendKeys(model.getPassword()); //password
        formTextFields.get(7).sendKeys(model.getPassword()); //confirm password
        buttons.get(1).click(); // save data
    }

    public void createUserWithMismatchPassword(EmployeeModel model) {
        menuItems.get(1).click();
        buttons.get(2).click(); //click add user
        formTextFields.get(1).sendKeys(model.getFirstname()); //firstname
        formTextFields.get(3).sendKeys(model.getLastname());   //lastname
        formTextFields.get(4).sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        formTextFields.get(4).sendKeys(model.getEmployeeId()); //employeeId
        btnSwitch.click(); //click login details switch
        formTextFields.get(5).sendKeys(model.getUsername());       //username
        formTextFields.get(6).sendKeys(model.getPassword());    //pass
        formTextFields.get(7).sendKeys("wete");   //confirm pass
        buttons.get(1).click();     // save data
    }


    public void searchByEmployeeId(String employeeId) {
        menuItems.get(1).click();   //click pim
        formTextFields.get(1).sendKeys(employeeId);  // employeeId
        submit.click(); //click search
    }

    public void searchByEmployeeName(String firstname) {
        menuItems.get(8).click();
        search.click();
        search.sendKeys(firstname);
        search.sendKeys(Keys.ARROW_DOWN);
        search.sendKeys(Keys.ENTER);
        submit.click();
    }
}