#Author: Diego Juarez

Feature: LDassoc calculation 

  User Story: 12345
	Description: This scenario tests this

@progression
Scenario: LDassoc tool calculation using example GWAS data and calculate buttons
Given a user is on the LDLink homepage 
When the user clicks on 'LDassoc' link
And enables 'Use example GWAS data'
And clicks on 'Calculate' button 
Then user should see P-values and Regional LD Plot 
