Feature: View revenue by month / See all the characteristics of visits already made
	An admin can see a view with revenues by month order by quantity and a view that has all datas of the visits.

  Scenario: View revenue in dashboard with correct authority / Show list of all visits (Positive scenario)
    Given I log in to the system with user "admin1" with a valid password like an admin
    When I go to the estadistics views
    Then I can do logout like an admin
    
   Scenario: View revenue in dashboard with incorrect authority / Attempt to show the list of all visits without authorization (Negative scenario)
    Given I am not logged in the system and I want login like an admin
    When I try to do login as user "admin1" that is an admin with an invalid password
    Then the login form is shown again and I'm not an admin
    
  
