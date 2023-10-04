package testrunner;

import com.github.javafaker.Faker;
import config.EmployeeModel;
import config.Setup;
import io.qameta.allure.Allure;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.DashboardPage;
import pages.LoginPage;
import utils.Utils;

import java.io.IOException;

public class DashboardTestRunner extends Setup {

    LoginPage loginPage;
    DashboardPage dashboardPage;

    @BeforeTest(groups = "smoke")
    public void doLogin() throws IOException, ParseException {
        loginPage = new LoginPage(driver);
        JSONArray adminArray = Utils.readJSONList("./src/test/resources/admin.json");
        JSONObject adminObj = (JSONObject) adminArray.get(0);
        loginPage.doLogin(adminObj.get("username").toString(), adminObj.get("password").toString());
        dashboardPage =new DashboardPage(driver);
        dashboardPage.menuItems.get(1).click();   //click pim
    }

    @Test(priority = 1,description = "Admin created new user successfully")
    public void createUser() throws InterruptedException, IOException, ParseException {
        dashboardPage = new DashboardPage(driver);

        Faker faker =new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String employeeId = String.valueOf(faker.random().nextInt(6000,7000));
        String username = faker.name().username();
        String password = "user"+faker.internet().password(8,12,true,true,true);

        EmployeeModel model = new EmployeeModel();
        model.setFirstname(firstName);
        model.setLastname(lastName);
        model.setEmployeeId(employeeId);
        model.setUsername(username);
        model.setPassword(password);
        dashboardPage.createUser(model);

        Thread.sleep(7000);
        //String textTitleExcepted = driver.findElement(By.xpath("//*[contains(text(),\"Personal Details\")]")).getText();
        String textTitleExcepted = driver.findElement(By.xpath("//h6[normalize-space()='Personal Details']")).getText();
        if(textTitleExcepted.contains("Personal Details")){
            Utils.saveEmployeeInfo(model);
        }

        Allure.description("Admin created new user successfully");
    }

    @Test(priority = 2, description = "Admin try to create user without username")
    public void createUserWithoutUserName() throws InterruptedException {
        Faker faker =new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String employeeId = String.valueOf(faker.random().nextInt(6000,7000));
        String password = faker.internet().password(8,12,true,true,true);

        EmployeeModel model = new EmployeeModel();
        model.setFirstname(firstName);
        model.setLastname(lastName);
        model.setEmployeeId(employeeId);
        model.setPassword(password);
        dashboardPage.createUserWithoutUsername(model);

        Thread.sleep(5000);

        String actual = driver.findElement(By.className("oxd-input-field-error-message")).getText();
        String excepted = "Required";
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actual,excepted);
        softAssert.assertEquals(driver.findElements(By.className("oxd-text--h6")).get(1).getText(),"Add Employee");
        softAssert.assertAll();
    }

    @Test(priority = 3,description = "Admin try to create user with invalid username")
    public void createUserWithInvalidUsername() throws InterruptedException {
        dashboardPage = new DashboardPage(driver);

        Faker faker =new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String employeeId = String.valueOf(faker.random().nextInt(6000,7000));
        String username = "mnh";
        String password = faker.internet().password(8,12,true,true,true);

        EmployeeModel model = new EmployeeModel();
        model.setFirstname(firstName);
        model.setLastname(lastName);
        model.setEmployeeId(employeeId);
        model.setUsername(username);
        model.setPassword(password);
        dashboardPage.createUser(model);

        Thread.sleep(5000);

        String actual = driver.findElement(By.className("oxd-input-field-error-message")).getText();
        String excepted = "Should be at least 5 characters";
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actual,excepted);
        softAssert.assertAll();
    }
    @Test(priority = 4, description = "Admin try to create user with invalid password")
    public void createUserWithInvalidPassword() throws InterruptedException {
        Faker faker =new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String employeeId = String.valueOf(faker.random().nextInt(6000,7000));
        String username = faker.name().username();
        String password = "mQ2";

        EmployeeModel model = new EmployeeModel();
        model.setFirstname(firstName);
        model.setLastname(lastName);
        model.setEmployeeId(employeeId);
        model.setUsername(username);
        model.setPassword(password);

        dashboardPage.createUser(model);
        Thread.sleep(2000);

        String actual = driver.findElement(By.className("oxd-input-field-error-message")).getText();
        String excepted = "Should have at least 7 characters";
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actual,excepted);
        softAssert.assertAll();
    }
    @Test(priority = 5, description = "Admin try to create a user with a password that mismatched between the password and confirm password fields.")
    public void createUserWithMismatchPassword() throws InterruptedException {
        dashboardPage = new DashboardPage(driver);

        Faker faker =new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String employeeId = String.valueOf(faker.random().nextInt(6000,7000));
        String username = faker.name().username();
        String password = "u1"+faker.internet().password(8,12,true,true,true);

        EmployeeModel model = new EmployeeModel();
        model.setFirstname(firstName);
        model.setLastname(lastName);
        model.setEmployeeId(employeeId);
        model.setUsername(username);
        model.setPassword(password);
        dashboardPage.createUserWithMismatchPassword(model);

        Thread.sleep(2000);

        String actual = driver.findElement(By.className("oxd-input-field-error-message")).getText();
        String excepted = "Passwords do not match";
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actual,excepted);
        softAssert.assertAll();
    }

    @Test(priority = 6, description = "Admin successfully search user by Employee Id", groups = "smoke")
    public void searchByEmployeeId() throws IOException, ParseException, InterruptedException {
        dashboardPage = new DashboardPage(driver);
        JSONArray empArray = Utils.readJSONList("./src/test/resources/employees.json");
        JSONObject empObj = (JSONObject) empArray.get(empArray.size()-1);
        String employeeId = empObj.get("employeeId").toString();
        dashboardPage.searchByEmployeeId(employeeId);
        Thread.sleep(1500);

        String messageActual = driver.findElements(By.className("oxd-table-cell")).get(1).getText();
        Thread.sleep(1500);
        System.out.println(messageActual);
        Assert.assertTrue(messageActual.contains(employeeId));

        Allure.description("Admin successfully search user by Employee Id");
    }

    @Test(priority = 7, description = "Admin can not search user by wrong employee id")
    public void searchByWrongEmployeeId() throws InterruptedException {
        dashboardPage = new DashboardPage(driver);
        String employeeId ="15001";
        dashboardPage.searchByEmployeeId(employeeId);
        Thread.sleep(1500);

        String messageActual = driver.findElement(By.className("oxd-toast")).getText();
        Thread.sleep(1500);
        String messageExcepted ="No Records Found";
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(messageActual.contains(messageExcepted));
        softAssert.assertAll();
    }
    @Test(priority = 8, description = "Admin successfully search user by employee name")
    public void searchByEmployeeName() throws IOException, ParseException, InterruptedException {
        dashboardPage = new DashboardPage(driver);
        JSONArray empArray = Utils.readJSONList("./src/test/resources/employees.json");
        JSONObject empObj = (JSONObject) empArray.get(empArray.size()-1);
        String firstName = empObj.get("firstName").toString();
        dashboardPage.searchByEmployeeName(firstName);
        Thread.sleep(1500);

        Allure.description("Admin Successfully Search User by Employee Name");
    }

    @Test(priority = 9, description = "Admin can not search user by wrong employee name")
    public void searchByWrongEmployeeName() throws InterruptedException {
        dashboardPage = new DashboardPage(driver);
        Faker faker =new Faker();
        String firstName = faker.name().firstName();
        dashboardPage.searchByEmployeeName(firstName);
        Thread.sleep(1500);

        String textActual = driver.findElement(By.className("oxd-input-field-error-message")).getText();
        String textExcepted ="Invalid";
        Assert.assertEquals(textActual,textExcepted);
    }

    @Test(priority = 10, description = "Admin successfully logs out", groups = "smoke")
    public void logout(){
        loginPage = new LoginPage(driver);
        loginPage.doLogout();
        String textActual = driver.findElement(By.className("orangehrm-login-title")).getText();
        String textExcepted ="Login";
        Assert.assertEquals(textActual,textExcepted);

        Allure.description("Admin Successfully Logout");
    }

}