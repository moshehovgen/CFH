Feature: LightHouse Add Application

  @Application
  Scenario Outline: Create new android/IOS App (test case: 107378 107362)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click Add button
    Then validate App created

    Examples: 
      | username               | password  | name | icon  | platform | packageID       | category   |
      | ronen.yurik@perion.com | 1q2w3e4r$ | Auto | iconX | 1        | com.google.app1 | Automotive |
      | ronen.yurik@perion.com | 1q2w3e4r$ | Auto | iconX | 2        | com.yahoo.app2  | Sports     |

  @Application
  Scenario Outline: Create app with diff parameters (107363 107364 107365 107366 107368)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click Add button
    Then validate App created

    Examples: 
      | username               | password  | name                  | icon  | platform | packageID                     | category                |
      | ronen.yurik@perion.com | 1q2w3e4r$ | T363-auto             | iconX | 1        | auto.google.com               | Automotive              |
      | ronen.yurik@perion.com | 1q2w3e4r$ | T363-#@&*auto         | iconX | 2        | auto.yahoo.com                | Sports                  |
      | ronen.yurik@perion.com | 1q2w3e4r$ | T364-auto#@&*Longgggg | iconX | 1        | auto1122.yahoo33.com          | Education               |
      | ronen.yurik@perion.com | 1q2w3e4r$ | T364-autoLonggggg#@&* | iconX | 2        | A1122B33C4451D                | Hobbies & Interests     |
      | ronen.yurik@perion.com | 1q2w3e4r$ | T366-auto             | iconX | 1        | #Auto@yahoo&*SpecialChars     | Law, Gov't & Politics   |
      | ronen.yurik@perion.com | 1q2w3e4r$ | T365-auto             | iconX | 2        | SpecialChars#@&*              | Science                 |
      | ronen.yurik@perion.com | 1q2w3e4r$ | T369-auto             | iconX | 2        | 1122334451LongBundleIDlllllll | Style & Fashion         |
      | ronen.yurik@perion.com | 1q2w3e4r$ | T368-auto             | iconX | 1        | 1122334451LongPackageIDllllll | Religion & Spirituality |

  @Application
  Scenario Outline: validate error message (test case:  107369 - 107374)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click Add button
    Then validate error message <errorMessage>

    Examples: 
      | username               | password  | name      | icon  | platform | packageID                | category               | errorMessage                            |
      | ronen.yurik@perion.com | 1q2w3e4r$ |           | iconX | 1        | Auto@yahoo&*SpecialChars | Non-Standard Content   | Please provide an App Name              |
      | ronen.yurik@perion.com | 1q2w3e4r$ |           | iconX | 2        | Auto@yahoo&*SpecialChars | Illegal Content        | Please provide an App Name              |
      | ronen.yurik@perion.com | 1q2w3e4r$ | T372-auto | iconX | 1        |                          | Pets                   | Please provide a Package ID / Bundle ID |
      | ronen.yurik@perion.com | 1q2w3e4r$ | T371-auto | iconX | 1        |                          | Technology & Computing | Please provide a Package ID / Bundle ID |
      | ronen.yurik@perion.com | 1q2w3e4r$ | T374-auto | iconX | 2        | auto.bundleID.com        |                        | Please choose a Category                |
      | ronen.yurik@perion.com | 1q2w3e4r$ | T373-auto | iconX | 1        | auto.packageID.com       |                        | Please choose a Category                |

  @Application
  Scenario Outline: Click cancel button (test case: 107375)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click cancel button
    Then Validate back to app list

    Examples: 
      | username               | password  | name | icon  | platform | packageID       | category   |
      | ronen.yurik@perion.com | 1q2w3e4r$ | Auto | iconX | 1        | com.google.app1 | Automotive |
      | ronen.yurik@perion.com | 1q2w3e4r$ | Auto | iconX | 2        | com.yahoo.app2  | Sports     |
