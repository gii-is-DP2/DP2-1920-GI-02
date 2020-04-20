Feature: Add prescription
	A vet can add a prescription to a diagnosis.

  Scenario: Add prescription with correct parameters (Positive scenario)
    Given I'm authenticated as a vet
    When I go to my visit list view, details of a visit and add a prescription to the diagnosis of the visit
    Then The prescription is saved
    
   Scenario: Add diagnosis with incorrect parameters (Negative scenario)
    Given I am authenticated as a vet
    When I go to my visit list view, details of a visit and add a prescription to the diagnosis of the visit
    Then The prescription form is shown again with the errors