Feature: Login to the Automation Practice Application

#
#@Regression @TC_001
#Scenario Outline: Create Account Functionality
#Given Open the browser
#Given Create New Account <EmailId> <FirstName> <LastName>
#Given Login to the application <EmailId>
#And Tab Validation
#
#Examples:
#|EmailId						| FirstName| LastName|
#|TestInt1234y@quipuinterview.com|	Jijo   |	Kumar|
#
#@Regression @TC_002
#Scenario Outline: Browse Products Functionality
#Given Open the browser
#Given Login to the application <EmailId>
#And Find the list of products
#And Find the lowest price
#
#Examples:
#|EmailId						| FirstName| LastName|
#|TestInt1234y@quipuinterview.com|	Jijo   |	Kumar|

@Regression @TC_003
Scenario Outline: Add To Cart Functionality
Given Open the browser
Given Login to the application <EmailId>
And Add to the Cart


Examples:
|EmailId						| FirstName| LastName|
|TestInt1234y@quipuinterview.com|	Jijo   |	Kumar|

#
#@Regression
#Scenario: Login for Invalid scenario
#And Enter UserName 'crmsfa1'
#And Enter Password 'crmsfa'
#When Click on Login button
#Then Error page should be displayed