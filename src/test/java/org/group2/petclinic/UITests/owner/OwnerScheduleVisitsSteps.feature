Feature: Schedule a new visit
	An owner can schedule a new visit

  Scenario: Schedule a correct visit (Positive scenario)
    Given that I am logged in as an owner
    When I schedule a correct visit
    Then the visit shows up in my schedule
    
   Scenario: Attempt to schedule a visit in the past (Negative scenario)
    Given that I am logged in as an owner
    When I attempt to schedule a visit in the past
    Then an error message is shown indicating that visits cannot be in the past.
    
  
