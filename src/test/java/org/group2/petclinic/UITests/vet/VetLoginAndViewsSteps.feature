Feature: View my assigned visits / Details of my assigned visits
	A vet can see a view with his assigned visits and a view with the details.

  Scenario: View my assigned visits with correct authority / Show details (Positive scenario)
    Given I log in to the system with user "vet1" with a valid password like a vet
    When I go to the visit views
    Then I can do logout like a vet
    
   Scenario: View my assigned visits with incorrect authority / Attempt to show details without authorization (Negative scenario)
    Given I am not logged in the system and I want login like a vet
    When I try to do login as user "vet1" that is a vet with an invalid password
    Then The login form is shown again and I'm not a vet
    
  
