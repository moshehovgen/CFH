@Dashboard
Feature: Codefuel dashboard

  Background: Login to portal and add an app
    Given User logged into the portal enter "nofardi1@mailinator.com" and "1q2w3e4r$"

  @Dashboard
  Scenario Outline: Mixed filters tests (Test cases: 116832, 116833, 116834, 116835)
    When choose Period <Period>, Country <Country>, and app <App>
    And click Go

    Examples: 
      | Period       | Country   | App    |
      | Last 7 days  | USA       | Mation |
      | Last 30 days | Brazil    | Mation |
      | Yesterday    | Check All | Mation |
