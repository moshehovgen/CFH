Feature: LightHouse Add Application

  @Application
  Scenario Outline: Create new android/IOS App (test case: 107378 107362)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click Add button
    Then validate App created

    Examples: 
      | username                | password  | name | icon  | platform | packageID       | category   |
      | orantest@mailinator.com | 1q2w3e4r$ | Auto | iconX | 1        | com.google.app1 | Automotive |
      | orantest@mailinator.com | 1q2w3e4r$ | Auto | iconX | 2        | com.yahoo.app2  | Sports     |

  @Application
  Scenario Outline: Create app with diff parameters (107363 107364 107365 107366 107368)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click Add button
    Then validate App created

    Examples: 
      | username                | password  | name                  | icon  | platform | packageID                     | category                |
      | orantest@mailinator.com | 1q2w3e4r$ | T363-auto             | iconX | 1        | auto.google.com               | Automotive              |
      | orantest@mailinator.com | 1q2w3e4r$ | T363-#@&*auto         | iconX | 2        | auto.yahoo.com                | Sports                  |
      | orantest@mailinator.com | 1q2w3e4r$ | T364-auto#@&*Longgggg | iconX | 1        | auto1122.yahoo33.com          | Education               |
      | orantest@mailinator.com | 1q2w3e4r$ | T364-autoLonggggg#@&* | iconX | 2        | A1122B33C4451D                | Hobbies & Interests     |
      | orantest@mailinator.com | 1q2w3e4r$ | T366-auto             | iconX | 1        | #Auto@yahoo&*SpecialChars     | Law, Gov't & Politics   |
      | orantest@mailinator.com | 1q2w3e4r$ | T365-auto             | iconX | 2        | SpecialChars#@&*              | Science                 |
      | orantest@mailinator.com | 1q2w3e4r$ | T369-auto             | iconX | 1        | 1122334451LongPackageIDllllll | Style & Fashion         |
      | orantest@mailinator.com | 1q2w3e4r$ | T368-auto             | iconX | 1        | 1122334451LongBundleIDlllllll | Religion & Spirituality |

  @Application
  Scenario Outline: validate error message (test case:  107369 - 107374)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click Add button
    Then validate error message <errorMessage>

    Examples: 
      | username                | password  | name      | icon  | platform | packageID                 | category               | errorMessage                            |
      | orantest@mailinator.com | 1q2w3e4r$ |           | iconX | 1        | #Auto@yahoo&*SpecialChars | Non-Standard Content   | please provide an app name              |
      | orantest@mailinator.com | 1q2w3e4r$ |           | iconX | 2        | #Auto@yahoo&*SpecialChars | Illegal Content        | please provide an app name              |
      | orantest@mailinator.com | 1q2w3e4r$ | T372-auto | iconX | 1        |                           | Pets                   | please provide a Package ID / Bundle ID |
      | orantest@mailinator.com | 1q2w3e4r$ | T371-auto | iconX | 1        |                           | Technology & Computing | please provide a Package ID / Bundle ID |
      | orantest@mailinator.com | 1q2w3e4r$ | T374-auto | iconX | 2        | auto.bundleID.com         |                        | please choose a Category                |
      | orantest@mailinator.com | 1q2w3e4r$ | T373-auto | iconX | 1        | auto.packageID.com        |                        | please choose a Category                |
      
  
  @Application
  Scenario Outline: Click cancel button (test case: 107375)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click cancel button
    Then validate fields cleared

    Examples: 
      | username                | password  | name | icon  | platform | packageID       | category   |
      | orantest@mailinator.com | 1q2w3e4r$ | Auto | iconX | 1        | com.google.app1 | Automotive |
      | orantest@mailinator.com | 1q2w3e4r$ | Auto | iconX | 2        | com.yahoo.app2  | Sports     |
