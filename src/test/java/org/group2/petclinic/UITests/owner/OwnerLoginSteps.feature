Feature: Login as an owner
	An owner can login to the system

  Scenario: Login with the correct credentials (Positive scenario)
    Given that I am not logged in
    When I login as gfranklin with password gfranklin
    Then I can access the system as an owner
    
   Scenario: Login with incorrect credentials (Negative scenario)
    Given that I am not logged in
    When I login as gfranklin with password 123
    Then an error message is shown
    
  
