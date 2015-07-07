Feature: Registration page tests

@Registration
  Background: create mail acount on mailinator
    Given create mail on mailinater <mail>
    
@Registration
  Scenario Outline: Create new acount positve test - (Test cases: 114364)
    And Browse to registration page
    When I enter publisher name <Publisher>, first name <fName>, last name <lName>, mail <mail>, password <password>, publisher type <pubType>
    And click submit
    And Verify mail sent to <mail>
    Then click on registration complete
    And verify registration complete

    Examples: 
      | Publisher    | fName | lName   | mail                  | password | pubType          |
      | Nofs company | Nofar | Diamant | nofard@mailinator.com | 123456   | Mobile publisher |
      | Nofs companr | Nofgr | Digmant | nogrd@mailinator.com  | 123556   | Mobile publisher |
