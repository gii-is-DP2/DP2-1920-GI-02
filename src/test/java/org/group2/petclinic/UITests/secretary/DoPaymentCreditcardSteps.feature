Feature: Validate credit card
	A secretary do a payment that owner pays by creditcard

  Scenario: Do a payment with a valid creditcard   (Positive scenario)
    Given I log with "secretary2" and see unpaid visits view
    When Do a payment of a visit selecting creditcard and introduce a valid one
    Then The payment is saved with valid creditcard
    
   Scenario:  Do a payment with a invalid creditcard  (Negative scenario)
    Given I log with "secretary2" and see unpaid visits view
    When Do a payment of a visit selecting creditcard and introduce a invalid one
    Then The payment doesn't save because is invalid creditcard
