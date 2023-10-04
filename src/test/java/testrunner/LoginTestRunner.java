package testrunner;

import config.Setup;
import io.qameta.allure.Allure;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import utils.Utils;

import java.io.IOException;

public class LoginTestRunner extends Setup {

    LoginPage loginPage;

    @Test(priority = 1, description = "Admin try to login with wrong credentials")
    public void doLoginWithWrongCreds() {
        loginPage = new LoginPage(driver);
        loginPage.doLogin("Admin", "WrongPass");
        String textActual = driver.findElement(By.className("oxd-alert-content-text")).getText();
        Assert.assertTrue(textActual.contains("Invalid credentials"));

    }

    @Test(priority = 2, description = "Admin successfully login with valid credentials", groups = "smoke")
    public void doLogin() throws IOException, ParseException {
        loginPage = new LoginPage(driver);

        JSONArray adminArray = Utils.readJSONList("./src/test/resources/admin.json");
        JSONObject adminObj = (JSONObject) adminArray.get(0);
        if ((System.getProperty("username") != null) && (System.getProperty("password") != null)) {
            loginPage.doLogin(System.getProperty("username"), System.getProperty("password"));
        } else {
            loginPage.doLogin(adminObj.get("username").toString(), adminObj.get("password").toString());
        }
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(driver.findElement(By.className("oxd-userdropdown-img")).isDisplayed());
        softAssert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
        softAssert.assertAll();

        Allure.description("Admin Login Successfully");
    }

}