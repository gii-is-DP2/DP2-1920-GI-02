Feature: View all unpaid visits 
	An 

  Scenario: See the list of unpaid visits with the secretary role  (Positive scenario)
    Given I log in to the system with user "secretary2" with a valid password like a secretary
    When I go to the visits view
    Then I can do logout like a secretary
    
   Scenario: See the list of unpaid visits with incorrect authority  (Negative scenario)
    Given I am not logged in the system and I want login like a secretary
    When I try to do login as user "secretary2" that is a secretary with an invalid password
    Then the login form is shown again and I'm not a secretary
    
  
