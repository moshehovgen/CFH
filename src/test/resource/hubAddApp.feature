Feature: Codefuel Add Application

  @Application @Regression @Sanity
  Scenario Outline: Create new android/IOS App (test case: 107378 107362)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> select <platform> Enter packageID <packageID>
    And Click Add button
    Then validate App created
    And validate properties are correct; <name>, <platform>, <packageID>

    Examples: 
      | username                | password  | name | platform | packageID      |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | 1        | com.google.app |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | 2        | com.yahoo.app  |

  @Application @Regression
  Scenario Outline: Create app with diff parameters (107363 107364 107365 107366 107368)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> select <platform> Enter packageID <packageID>
    And Click Add button
    Then validate App created

    Examples: 
      | username                | password  | name                  | platform | packageID                     |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T363-auto             | 1        | auto.google.com               |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T363-#@&*auto         | 2        | auto.yahoo.com                |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T364-auto#@&*Longgggg | 1        | auto1122.yahoo33.com          |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T364-autoLonggggg#@&* | 2        | A1122B33C4451D                |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T366-auto             | 1        | #Auto@yahoo&*SpecialChars     |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T365-auto             | 2        | SpecialChars#@&*              |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T369-auto             | 2        | 1122334451LongBundleIDlllllll |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T368-auto             | 1        | 1122334451LongPackageIDllllll |

  @Application @Regression
  Scenario Outline: validate error message (test case:  107369 - 107374)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> select <platform> Enter packageID <packageID>
    And Click Add button
    Then validate error message <errorMessage>

    Examples: 
      | username                | password  | name      | platform | packageID                | errorMessage           |
      | nofardi1@mailinator.com | 1q2w3e4r$ |           | 1        | Auto@yahoo&*SpecialChars | This field is required |
      | nofardi1@mailinator.com | 1q2w3e4r$ |           | 2        | Auto@yahoo&*SpecialChars | This field is required |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T372-auto | 1        |                          | This field is required |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T371-auto | 1        |                          | This field is required |

  @Application @Regression
  Scenario Outline: Click cancel button (test case: 107375)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> select <platform> Enter packageID <packageID>
    And Click cancel button
    Then Validate back to app list

    Examples: 
      | username                | password  | name | platform | packageID       |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | 1        | com.google.app1 |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | 2        | com.yahoo.app2  |

  @Application @Regression @Sanity
  Scenario Outline: Create App and then edit information (Test case: 107499)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> select <platform> Enter packageID <packageID>
    And Click Add button
    Then validate App created
    And edit app
    And change app name <newname>
    And click save edit
    And validate name changed to <newname>

    Examples: 
      | username                | password  | name | platform | packageID      | newname            |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | 1        | com.google.app | NewAuto            |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | 2        | com.yahoo.app  | Pretty application |

  @Application @Regression
  Scenario Outline: Deactive app (Test case: 107407)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> select <platform> Enter packageID <packageID>
    And Click Add button
    Then validate App created
    And validate App active
    And deactive app
    And validate app deactive

    Examples: 
      | username                | password  | name | platform | packageID      |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | 1        | com.google.app |
