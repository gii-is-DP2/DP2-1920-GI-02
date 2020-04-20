Feature: Add a new pet
	An owner can add a new pet

  Scenario: Add a new pet with all required data (Positive scenario)
    Given that I am logged in as an owner
    When I add a new pet with all required data
    Then the pet is added
    
   Scenario: Attempt to add a pet without data (Negative scenario)
    Given that I am logged in as an owner
    When I attempt to add a pet without data
    Then an error message is shown indicating that the pet could not be added
