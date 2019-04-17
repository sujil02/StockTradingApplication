The jar file independent of any locations and has its dependencies contained in it.
To run the jar file open command promp and execute below command to start a text based view mode.
java - jar Assingment-10.jar TEXT
Or just apply command below command to start the default GUI based View.
java - jar Assingment-10.jar

How to navigate through the application.

(Note :- This is a single user menu driven application.)

Once the jar is running the screen would display following details -:
Select from following options
1       Create New Portfolio --> Allows the user to create a new portfolio for this instance.
2       Manage Portfolios --> Manage the existing portfolios.
3       Buy Stocks using Dollar Cost Averaging Strategy
4       Import Existing portfolio --> Imports a portfolio from a location.
5       Import and execute existing strategy --> Create a new portfolio and executing an importing strategy.
6       Quit --> Terminate the application and stops the execution of the jar.

If option 1 is selected it prompts you to enter the portfolio name
eg. "Enter new Portfolio Name"
 and when done show a message Portfolio successfully created.

 If Option 2 is selected it expects you to select a portfolio from the existing list.
 Once selected allows following options.

Selected Portfolio: "xyz"
1       Display contents of this portfolio --> Command displays all the contents of the portfolio.
2       Buy Shares on this portfolio --> This command would allow the user to buy a few shares to this portfolio using single buy or by using strategy.
3       Get Cost Bias of this Portfolio --> Evaluates the cost price on a given date.
4       Get current value of this portfolio. --> Evaluates the value of the portfolio on the given date.
5       Export Current Portfolio --> Export a portfolio as a json file at the specified location.
6       Select new portfolio --> If you want to switch between portfolios.
7       Back --> move back to the previous state.
Select option


On selection of option 2
1       Buy Stocks using no Strategy on this portfolio <-- Basic buy operation
2       Buy Stocks using Dollar Cost Averaging Strategy <-- Buy using basic strategy.
3       Back <-- back to previous level.

The program would respond with an appropriate error message when user enters invalid inputs.



***************************************************


GUI based sample screen shoots are added in the res folder under screen shoots directory.
