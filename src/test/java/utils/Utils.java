package utils;

import config.EmployeeModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
    public static void saveEmployeeInfo(EmployeeModel model) throws IOException, ParseException {
        String file ="./src/test/resources/employees.json";
        JSONParser jsonParser =new JSONParser();
        JSONArray empArray = (JSONArray) jsonParser.parse(new FileReader(file));
        JSONObject empObj = new JSONObject();
        empObj.put("firstName", model.getFirstname());
        empObj.put("lastName", model.getLastname());
        empObj.put("employeeId",model.getEmployeeId());
        empObj.put("username", model.getUsername());
        empObj.put("password", model.getPassword());

        empArray.add(empObj);

        FileWriter writer =new FileWriter(file);
        writer.write(empArray.toJSONString());
        writer.flush();
        writer.close();
    }

    public static JSONArray readJSONList(String fileName) throws IOException, ParseException {
        JSONParser jsonParser =new JSONParser();
        JSONArray empArray = (JSONArray) jsonParser.parse(new FileReader(fileName));
        return empArray;
    }

    public static void scroll(WebDriver driver, int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(" + x + "," + y + ")");
    }
}