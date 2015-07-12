Feature: Registration page tests

  @Registration
  Scenario Outline: Create new acount positve test - (Test cases: 114364, 107253, part of 107254)
    Given create mail on mailinater <mail>
    And Browse to registration page
    When I enter publisher name <Publisher>, first name <fName>, last name <lName>, mail, password <password>, publisher type <pubType>
    And click submit
    And Verify mail sent
    Then click on link in mail
    And verify registration complete <password>

    Examples: 
      | Publisher    | fName | lName  | mail                        | password  | pubType          |
      | Auto company | Auto  | Mation | auto                        | 1q2w3e4r$ | Mobile publisher |
      | Auto company | Auto  | Mation | auto                        | 1q2w3e4r$ | Mobile publisher |

  @Registration
  Scenario Outline: Create new acount positve test - (Test cases: 107254)
    Given create mail on mailinater <mail>
    And Browse to registration page
    When I enter publisher name <Publisher>, first name <fName>, last name <lName>, mail, password <password>, publisher type <pubType>
    And validate the warning message in register <message>

    Examples: 
      | Publisher    | fName | lName  | mail                        | password  | pubType          | message                     |
      | Auto company | Auto  | Mation | autoCodefeul@mailinator.com | 1q2w3e4r$ | Mobile publisher | You are already registered. |
