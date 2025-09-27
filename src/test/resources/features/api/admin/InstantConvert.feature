@InstantConvert
Feature: Instant Convert
  As a Mobee user
  I want to access Instant Convert feature
  So that I can convert assets easily

  @Access-InstantConvert
  Scenario: Access Instant Convert via Trade
    Given I set API endpoint for Instant Convert
    When I send a GET request to Instant Convert endpoint
    Then I receive status code 200
    And I get valid data for Instant Convert

  @Below-Minimum
  Scenario: Convert with Amount Below Minimum
    Given I set API endpoint for Instant Convert
    When I send a POST request with amount below minimum
    Then I receive status code 400
    And I receive error message "Minimum amount is 18"

  @Above-Maximum
  Scenario: Convert with Amount Above Maximum Limit
    Given I set API endpoint for Instant Convert
    When I send a POST request with amount above maximum
    Then I receive status code 400
    And I receive error message "Maximum amount is 1646589808.61802928 IDR"

  @Negative-Amount
  Scenario: Convert with Negative Amount
    Given I set API endpoint for Instant Convert
    When I send a POST request with negative amount
    Then I receive status code 400
    And I receive error message "Amount must be greater than 0"

  @Unsupported-Currency
  Scenario: Convert with Unsupported Currency Pair
    Given I set API endpoint for Instant Convert
    When I send a POST request with unsupported currency pair
    Then I receive status code 400
    And I receive error message "Unsupported asset"

  @Zero-Amount
  Scenario: Convert with Zero Amount
    Given I set API endpoint for Instant Convert
    When I send a POST request with zero amount
    Then I receive status code 400
    And I receive error message "amount_from: cannot be blank; amount_to: cannot be blank."