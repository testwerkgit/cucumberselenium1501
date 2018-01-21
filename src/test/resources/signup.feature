Feature: Make a new user account for website PHPtravels

  Scenario: Make a new login for a single user
    Given I open the register page at 'http://www.phptravels.net/register'
    When I make a new account for user 'johndoe@whoami.com'
    Then the welcome message 'Hi, John Doe' is displayed

  Scenario: Make a new login for user by use of Datatable
    Given I open the register page at 'http://www.phptravels.net/register'
    When I make a new account for user with this data
      | field     | value              |
      | firstname | Jane               |
      | lastname  | Doe                |
      | phone     | 0612345678         |
      | email     | jane.doe@email.com |
      | password  | passwordjane       |
    Then the welcome message is displayed

  Scenario Outline: Make several new logins by use of abstract scenario
    Given I open the register page at 'http://www.phptravels.net/register'
    When I make the following account for <firstname>, <lastname>, <phone>, <email>, <password>, <passwordrepeat>
    Then the welcome message is displayed

    Examples:
      | firstname | lastname   | phone      | email                       | password  | passwordrepeat |
      | Peter     | Parker     | 0698765432 | peter.parker@marvel.com     | spider123 | spider123      |
      | Guybrush  | Threepwood | 08006547   | wannabepirate@lucasarts.com | monkey    | monkey         |
      | Arthur    | Dent       | 42         | arthur@theguide.unvrs       | hitch     | hitch          |
      | Matt      | Trakker    | 911        | thunderhawk@mask.gov        | scott     | scott          |
