Feature: Codefuel Add Application

  @Application @Regression @Sanity
  Scenario Outline: Create new android/IOS App (test case: 107378 107362)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click Add button
    Then validate App created
    And validate properties are correct; <name>, <platform>, <packageID>, <category>

    Examples: 
      | username               | password  | name | icon  | platform | packageID      | category   |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | iconX | 1        | com.google.app | Automotive |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | iconX | 2        | com.yahoo.app  | Sports     |

  @Application @Regression
  Scenario Outline: Create app with diff parameters (107363 107364 107365 107366 107368)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click Add button
    Then validate App created

    Examples: 
      | username               | password  | name                  | icon  | platform | packageID                     | category                |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T363-auto             | iconX | 1        | auto.google.com               | Automotive              |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T363-#@&*auto         | iconX | 2        | auto.yahoo.com                | Sports                  |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T364-auto#@&*Longgggg | iconX | 1        | auto1122.yahoo33.com          | Education               |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T364-autoLonggggg#@&* | iconX | 2        | A1122B33C4451D                | Hobbies & Interests     |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T366-auto             | iconX | 1        | #Auto@yahoo&*SpecialChars     | Law, Gov't & Politics   |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T365-auto             | iconX | 2        | SpecialChars#@&*              | Science                 |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T369-auto             | iconX | 2        | 1122334451LongBundleIDlllllll | Style & Fashion         |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T368-auto             | iconX | 1        | 1122334451LongPackageIDllllll | Religion & Spirituality |

  @Application @Regression
  Scenario Outline: validate error message (test case:  107369 - 107374)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click Add button
    Then validate error message <errorMessage>

    Examples: 
      | username               | password  | name      | icon  | platform | packageID                | category               | errorMessage                            |
      | nofardi1@mailinator.com | 1q2w3e4r$ |           | iconX | 1        | Auto@yahoo&*SpecialChars | Non-Standard Content   | This field is required              |
      | nofardi1@mailinator.com | 1q2w3e4r$ |           | iconX | 2        | Auto@yahoo&*SpecialChars | Illegal Content        | This field is required              |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T372-auto | iconX | 1        |                          | Pets                   | This field is required |
      | nofardi1@mailinator.com | 1q2w3e4r$ | T371-auto | iconX | 1        |                          | Technology & Computing | This field is required |
    #  | nofardi1@mailinator.com | 1q2w3e4r$ | T374-auto | iconX | 2        | auto.bundleID.com        |                        | Please choose a Category                |
     # | nofardi1@mailinator.com | 1q2w3e4r$ | T373-auto | iconX | 1        | auto.packageID.com       |                        | Please choose a Category                |

  @Application @Regression
  Scenario Outline: Click cancel button (test case: 107375)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click cancel button
    Then Validate back to app list

    Examples: 
      | username               | password  | name | icon  | platform | packageID       | category   |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | iconX | 1        | com.google.app1 | Automotive |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | iconX | 2        | com.yahoo.app2  | Sports     |

  @Application @Regression @Sanity
  Scenario Outline: Create App and then edit information (Test case: 107499)
    Given User logged into the portal enter <username> and <password>
    When User select App tab and click on Add app button
    And Enter App <name> upload <icon> select <platform> Enter packageID <packageID> choose category <category>
    And Click Add button
    Then validate App created
    And edit app
    And change app name <newname> and category <newCategory>
    And click save edit
    And validate name and category changed to <newname> <newCategory>

    Examples: 
      | username               | password  | name | icon  | platform | packageID      | category   | newname            | newCategory   |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | iconX | 1        | com.google.app | Automotive | NewAuto            | Food & Drinks |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | iconX | 2        | com.yahoo.app  | Sports     | Pretty application | Uncategorized |
      

      
      
