Feature: Add medicine
	An admin can add a medicine to the list.

  Scenario: Add medicine with correct parameters (Positive scenario)
    Given I'm logged as an admin
    When I go to the medicine list view and add a medicine with correct parameters
    Then The medicine is saved
    
   Scenario: Add medicine with incorrect parameters (Negative scenario)
    Given I am logged as an admin
    When I go to the medicine list view and add a medicine with incorrect parameters
    Then The medicine form is shown again with the errors