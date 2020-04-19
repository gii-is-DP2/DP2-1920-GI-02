Feature: Register a payment with credit card or cash
	A secretary do a payment that owner pays by cash

  Scenario: Do a payment with cash   (Positive scenario)
    Given I log with "secretary2" and go to the unpaid visits view
    When Do a payment of a visit selecting cash and the positive quantity
    Then The payment is saved
    
   Scenario: Do a payment with cash introducing a negative quantity  (Negative scenario)
    Given I log with "secretary2" and go to the unpaid visits view neg
    When Do a payment of a visit selecting cash and the negative quantity
    Then The payment doesn't save
