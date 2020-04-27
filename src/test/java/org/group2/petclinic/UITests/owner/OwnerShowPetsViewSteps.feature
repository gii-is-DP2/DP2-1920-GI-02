Feature: Show the pets view
	An owner can show the pets view

  Scenario: Show the pets view as an owner (Positive scenario)
    Given that I am logged in as an owner
    When I click the link to show the pets view
    Then the pets view is shown
    
   Scenario: Attempt show the pets view as an admin (Negative scenario)
    Given that I am logged in as an admin
    When I attempt to open the pets view
    Then an error message is shown indicating that the view is forbidden
    
  
