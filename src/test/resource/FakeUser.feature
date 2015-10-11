Feature: Fake user feature

  @Fake @Regression
  Scenario: Login with super user and verify access to the users related
    Given I enter the login page
    When I enter "automationSuper@mailinator.com" and "1q2w3e4r$" first time
    And choose acount name: "Automation test"
    And choose user name: "autocodefuel1@mailinator.com"
    And super user: "automationSuper@mailinator.com" logged as "autocodefuel1"
    Then user select app tab
    And verify applications are located
