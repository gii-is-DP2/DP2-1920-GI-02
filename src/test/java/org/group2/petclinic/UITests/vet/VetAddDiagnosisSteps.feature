Feature: Add diagnosis
	A vet can add a diagnosis to a past visit.

  Scenario: Add diagnosis with correct parameters (Positive scenario)
    Given I'm logged as a vet
    When I go to my visit list view, details of a visit and add a diagnosis to the visit with correct parameters
    Then The diagnosis is saved
    
   Scenario: Add diagnosis with incorrect parameters (Negative scenario)
    Given I am logged as a vet
    When I go to my visit list view, details of a visit and add a diagnosis to the visit with incorrect parameters
    Then The diagnosis form is shown again with the errors