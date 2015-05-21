Feature: LightHouse Add Application

  @Application
  Scenario Outline: Creat new android App (test case: 107378)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click Add button
    Then validate App created

    Examples: 
      | username                | password  | name | icon  | platform | packageID  | category        |
      | orantest@mailinator.com | 1q2w3e4r$ | Auto | iconX | 1        | 1122334455 | Automotive      |
      | orantest@mailinator.com | 1q2w3e4r$ | Auto | iconX | 2        | 1122334455 | Illegal Content |
