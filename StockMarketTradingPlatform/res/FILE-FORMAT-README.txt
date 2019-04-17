************* Portfolio JSON explanation ********************************
This is a sample json file which can reffered to create a sample portfolio design by a user.

The json expects the object of type porfolio having respective feilds.
Refer to the sample json written below with left tag as key name and right tag as values.

{
  "portfolioName": 'Name of the portfolio to be created',
  "ownedStocks": [ --->  All the stocks in the portfolio.
    {
      "companyName": --> Company name
      "tickerSymbol": "Company ticker symbol",
      "quantity": quantity of the stocks to be bought,
      "costPrice": total cost of the transaction,
      "purchaseDate": Valid Date on which the transaction was done,
      "commission": commision fees
    },
	
	// Add multiple entries of the sample stock design to have multile stocks.
  ]
}

************* Strategy JSON explanation ********************************

{
  "tickerSymbols": { All the stocks which this strategy is going to invest in in a key value pair format. Example as below.
    "MSFT": 0.33,
    "GOOG": 0.33,
    "FB": 0.33
  },
  "totalQuantity": 0, <---- Place holder if investment needs to scaled from investment amount based to quantity based.
  "commission": 5.0, <---- Commision charge on each transaction.
  "investmentAmount": 4000.0, <--- Total amount to be invested.
  "startDate": "01/04/2019 16:00:00", <-- Start date of the strategy in MM/dd/YYYY HH:mm:ss format.
  "endDate": "04/16/2019 16:00:00", <-- End date of the strategy in MM/dd/YYYY HH:mm:ss format.
  "period": 30 <--- After how many days this strategy should be executed.
}