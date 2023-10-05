# HRM Suite Automation with TestNG
### This project is an automation test suite for OrangeHRM, a human resource management system, using Selenium and TestNG. The suite includes positive and negative test cases for various functionalities.
### Test Cases
https://docs.google.com/spreadsheets/d/1Rx3KBm7UJY9BBOL42Hj3nYj4uoaME5Ry/edit#gid=1630449902
## Tools and Technology Used
- IntelliJ IDEA
- Selenium Webdriver
- TestNG Framework
- Gradle
- Allure

## Prerequisites
- JDK
- Gradle

## How to run this project
- Clone this repository
- Open Terminal
- Give the following command to run Smoke Test Suite: gradle clean test -Pfilesuite="SmokeMasterSuite.xml"
- Give the following command to run Regression Test Suite: gradle clean test -Pfilesuite="RegressionMasterSuite.xml"
- To generate Allure Report use these commands: allure generate allure-results --clean -output & allure serve allure-results

## Allure Report for Regression Test
![RegressionTestAllureReport](https://github.com/Moonmoon838/Automate_Webform_JUNIT/assets/143262452/cf21b649-85a6-47c2-9a27-77eeef186bbb)
![RegresionTestAllureReport01](https://github.com/Moonmoon838/Automate_Webform_JUNIT/assets/143262452/303cfdae-4933-4b7f-8891-f78dc803e365)

## Automation Video for Regression Test
https://github.com/Moonmoon838/Automate_Webform_JUNIT/assets/143262452/86c8746d-3adb-448d-885f-8d12c6746975

## Automation Video for Smoke Test
https://github.com/Moonmoon838/Automate_Webform_JUNIT/assets/143262452/d04dc00b-6fd8-47ca-9bc6-3bdba255aaa2
