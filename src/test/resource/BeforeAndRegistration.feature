Feature: Registration page tests

  @BeforeAll @Regression
  Scenario Outline: Add a new user: register, login and add one app
    Given Browse to registration page
    And verify registration window
    When begin I enter publisher name <Publisher>, first name <fName>, last name <lName>, mail <mail>, password <password>, publisher type <pubType>
    And click submit
    And create mail on mailinater for registration <mail>
    And Verify mail for register sent to <mail>
    Then click on link in mail <link>
    And validate registration complete in before <password>
    And add first app

    Examples: 
      | Publisher    | fName | lName  | mail | password  | pubType          | link                      |
      | Auto company | Auto  | Mation | auto | 1q2w3e4r$ | Mobile publisher | SURE, ACTIVATE MY ACCOUNT |

  @Registration @Sanity @Regression
  Scenario Outline: Create new acount positve test - (Test cases: 114364, 107253, part of 107254)
    Given Browse to registration page
    When I enter publisher name <Publisher>, first name <fName>, last name <lName>, mail <mail>, password <password>, publisher type <pubType>
    And click submit
    And create mail on mailinater for registration <mail>
    And Verify mail for register sent to <mail>
    Then click on link in mail <link>
    And verify registration complete <password>

    Examples: 
      | Publisher    | fName | lName  | mail | password  | pubType          | link                      |
      | Auto company | Auto  | Mation | auto | 1q2w3e4r$ | Mobile publisher | SURE, ACTIVATE MY ACCOUNT |
      | Auto company | Auto  | Mation | auto | 1q2w3e4r$ | Mobile publisher | SURE, ACTIVATE MY ACCOUNT |

  @Registration @Regression
  Scenario Outline: Create new acount positve test - (Test cases: 107254)
    And Browse to registration page
    When I enter publisher name <Publisher>, first name <fName>, last name <lName>, mail <mail>, password <password>, publisher type <pubType>
    And validate the warning message <message>

    Examples: 
      | Publisher    | fName | lName  | mail                        | password  | pubType          | message                     |
      | Auto company | Auto  | Mation | autoCodefuel@mailinator.com | 1q2w3e4r$ | Mobile publisher | You are already registered. |
