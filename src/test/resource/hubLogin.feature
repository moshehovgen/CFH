Feature: Codefuel Login

  @Login @Regression @Sanity
  Scenario Outline: Login to Portal (test case: 107213)
    Given I browse to login page
    When I enter <username> and <password> first time
    Then validate login pass
    And User log out

    Examples: 
      | username               | password  |
      | nofardi1@mailinator.com | 1q2w3e4r$ |

  @Login @Regression
  Scenario Outline: Login negative (test cases: 107214, 107217, 107215, 107218, 107258)
    Given I browse to login page
    When I enter <username> and <password> first time
    Then validate warning message <message>

    Examples: 
      | username               | password  | message                            |
      | badUserName@perion.com | 123456    | The email or password is incorrect |
      | nofardi1@mailinator.com |           | Please enter your password         |
      | skip                   | 1q2w3e4r$ | Please enter an email              |
      | nofardi1@mailinator.com | 123456    | The email or password is incorrect |
      | badUserName@perion.com | 1q2w3e4r$ | The email or password is incorrect |
      | skip                   |           | Please enter your password         |

 
