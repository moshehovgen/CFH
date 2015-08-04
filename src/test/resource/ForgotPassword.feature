Feature: Forgot password feature

  @Password
  Scenario Outline: Reset password flow - positive flow (test case: 107223)
    Given navigate to login page
    When click on forgot password
    And enter mail in forgot <mail>
    And click on send
    Then Verify mail for password sent to <mail>
    And click on password link in mail <link>
    And enter new password <password> <retypePassword>
    And verify pass change complete <mail> <password>

    Examples: 
      | mail                        | password   | link                   | retypePassword |
      | autoCodefuel@mailinator.com | 1q2w3e4r@$ | YES, RESET MY PASSWORD | 1q2w3e4r@$     |

  @Password
  Scenario Outline: Reset password flow - click send without mail (test case: 107225)
    Given navigate to login page
    When click on forgot password
    And click on send
    Then verify the warning message in forgot <message>

    Examples: 
      | message                        |
      | Please enter an email address. |

  @Password
  Scenario Outline: Reset password flow - negative tests (test cases: 107226, 107228, 107229, 107235)
    Given navigate to login page
    When click on forgot password
    And enter mail in forgot <mail>
    And click on send
    Then Verify mail for password sent to <mail>
    And click on password link in mail <link>
    And enter new password <password> <retypePassword>
    And verify the warning message in forgot <message>

    Examples: 
      | mail                        | password  | retypePassword | link                   | message                                          |
      | autoCodefuel@mailinator.com | qwe123456 | qwe12345       | YES, RESET MY PASSWORD | The password and confirmation do not match       |
      | autoCodefuel@mailinator.com | 123456    | 12345          | YES, RESET MY PASSWORD | The password must include at least 8 characters. |
      | autoCodefuel@mailinator.com | qwe123456 |                | YES, RESET MY PASSWORD | Please confirm your password.                    |
      | autoCodefuel@mailinator.com |           |                | YES, RESET MY PASSWORD | Please enter a password.                         |
