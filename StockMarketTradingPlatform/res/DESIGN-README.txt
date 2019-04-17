Model:
Features which needed to be supported by model were:
1. create new portfolio
2. examine existing portfolio
3. Add stocks to existing portfolio
4. calculate cost bias of the portfolio on a given date
5. calculate value of the portfolio 
6. get portfolio list

Model is defined as IUser which controls all the portfolios and all functions provided by application.
The function signature for each feature is as follows:
1. create new portfolio
	void createPortfolio(String portfolioName) throws IllegalArgumentException;
	It takes in portfolio name which is a unique identifier of portfolio and it does not return any thing since it creates new portfolio and stores it. 
	Method throws an Illegal argument exception in case there is an error in portfolio name.
	
2. examine existing portfolio
	String getPortfolioContents(String portfolioName);
	It takes in portfolio name to for which contents will be returned. It returns a string which contains all information of contents of the given portfolio.
	Method throws IllegalArgument Exception in case there is an error in portfolio name or portfolio name is not found.
	
3. Add stocks to existing portfolio
	void makeATrade(String portfolioName, TradeType tradeType, Date date, String tickerSymbol,
                  String companyName, int quantity);
	This method takes in portfolio name on which trade is being made, a type of trade which is being performed(eg. buy/sell), date on which the trade is being performed, 
	ticker symbol of the company whose stocks are being traded, company name whose stocks are being traded and quantity of the stocks being trade.
	it does not return anything since it adds/removes stock from portfolio.
	Method throws Illegalargument exception if any parameter is invalid.
	
4. calculate cost bias of the portfolio on a given date
	float getPortfolioCostBias(String portfolioName, Date refDate);
	It takes in portfolio name to identify portfolio and reference date on which cost bias of portfolio needs to be calculated.
	
5. calculate value of the portfolio 
	float getPortfolioValue(String portfolioName, Date refDate);
	It takes in portfolio name to identify portfolio and reference date on which value of portfolio needs to be calculated.
	
6. get portfolio list
	List<String> getAllPortfolioNames();
	It returns all portfolio names created by a user as a list.

Model is further divided into elemental class IPortfolio. IUser maintains the list of all portfolio. creates an object of IPortfolio when ever a new Portfolio is created. 
IPortfolio provides all features exposed by IUser for individual portfolio. Portfolio implements Iportfolio which implements features provided by model at individual portfolio level.
It contains List of Istock object which represents Stocks currently present in the given portfolio. It also contains an inner clas StockAttribure which represents trade details of a particular stock.
When a Stock is added to Portfolio it adds it to a map with IStock as the key and adds its attributes to a list if stock already exist or creates a new entry in map.

IStock represents a listed company it is uniquely identified by its Ticker Symbol it also contains property of Company name which is optional. 
It also contains implementation to get its value on a given date from the API.
Value is determined by average of highest and lowest stock price on the given date.

Controller:

Controller follows command design pattern. It defines ICommand interface which contains two method one to execute which takes model and view andthe functionality and other to get description of the feature.
the features are divided into two levels one which will be performed at user level like create portfolio and others at portfolio level like buy shares, get cost bias, get value etc.
Portfolio level commands are defined by IPortfolioCommand interface which in addition to model and view will also take portfolio name on which the feature will be implemented. once any userlevel feature is selected
execute method of ICommand is called.

Controller mantains User level features in a map with option as the key and its corresponding ICommand object as its value
among user level features it contains Examine portfolio feature which will ask user to select a portfolio from existing list.
This feature displays all commands which can be executed on portfolio level. It mantains map of options presented for presented to user as key and IPortfolioCommand objects as its value.
once the feature is selected execute function of IportfolioCommand is called.

To display userlevel feature or portfolio level features corresponding map is iterated and each getDescription method is called and the result is displayed 
to user with the option which user needs to input.

All features depending on the state of application user is in (user level feature or portfolio level feature) its corresponding features are displayed. Option to return to user level feature is given from portfolio level features.
Option to exit the program is provided from user level features.

View:
the application supports text based view hence it defines an interface which contains two methods. first to take input from user and second to append output to any form of output.
To make the implementation abstracted from type of input and output provided to application and enable it for automated testing we initialise view with appendable and readable objects.

Changes in New Version:
Optimization in IStock to make the portfolio exportable. No impact on user since The interface supports all old methods.
New Methods in interface are added as a new version of interface so that any old user of interface is not affected by the change and all method functionality is intact.

Assignment 9:
Changes in Model:
New features of trade with commission and trade using investment amount are added to model using new version of interface.
to support feature of making a trade with commission new property has been added to stock which stores the commission fee payed during the trade
and while calculating the cost bias of the trade. User has an option to put commission as 0 however user cannot put negative commission.

New Feature of trade with investment amount is been supported by calculating the quantity of shares which can be bought from the investment amount.
logic considers maximum number of stocks which can be bought using the provided amount without going over the amount.
logic will throw an exception if the investment amount is lower than the price of 1 stock on the given day.

Export portfolio
user is given an option to export the created portfolio in a json file. User is asked the directory where the exported file needs to be saved.
if the directory is not present error is thrown. At the given location a new file is created with portfolio name as file name and .json extension.

Import portfolio
user is given an option to import previously saved portfolio as well as create a portfolio in the published format and import it to the application.
user needs to give the file path for the file which needs to be imported. if the file is blank or file is in a different format exceptions are thrown.

Changes in Controller:
All the features common to GUI and Text based controller are abstracted in IFeatures interface and default implementation is provided in Abstract controller interface.

Text based interface still follows command design pattern. new commands for export and import has been added to controller to support export and import feature.
User is asked to enter commission while buying stock to support trade with commissions. User is given an option to invest using Investment amount by giving quantity as 0.

A new controller has been added for GUI view. This controller also implements IController and IFeatures which contains all the features. It overrides the features to
provide its implementation of the features using abstract controller.

Changes in View:
No changes are done in Text view of the program.

A new View has been added to support GUI view of the program. It provides User interface to all the features provided by controller and takes input from user and provides
inputs to controller depending upon the feature called.

Assignment 10:
Changes in Model: There is no public facing change in model. All the features exposed earlier are still available and were tested to check continuity.

Changes in Controller:
The Feature to implement strategies is implemented in controller since the basic functions required to perform any investing strategy is buying stocks which is already exposed from model.
Each strategy has a different variant of investing money in stocks which eventually will lead up to buy one or multiple stocks once or recursively on various dates.
Looking at this attribute of the strategy we decided to add strategy in controller using Strategy design pattern.

To implement Strategy we have created IStrategy interface which exposes method to buy stock, taking in controller and view (in other words taking control of the application).
Buying single stock is also a strategy in





