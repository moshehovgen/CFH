@Dashboard
Feature: Codefuel dashboard

  Background: Login to portal and add an app
    Given User logged into the portal enter dashboard "nofardi1@mailinator.com" and "1q2w3e4r$"

  # No need to add an application because I'll need a pre- configured app in order to perform the comparison
  Scenario Outline: Mixed filters tests (Test cases: 116832, 116833, 116834, 116835)
    When choose Period <Period>, Country <Country>, and app <App>
    And click Go
    And verify aplitools comparison works for: Period <Period>, Country <Country>, and app <App>

    Examples: 
      | Period       | Country       | App        |
      | Last 7 days  | United States | Top 5 Apps |
      | Last 30 days | Brazil        | Mation     |
      | Yesterday    | Check All     | Mation     |
