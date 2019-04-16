package view.guiview;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import controller.IFeatures;
import model.IPortfolioV2;
import model.IStock;
import model.TradeType;

/**
 * This class represents the GUI based view. Allows several object to be created and displayed on
 * the screen as the functionalitites. Also responsible to respond on several button clicks having
 * different action listener implementations.
 */

public class JFrameView extends JFrame implements IJFrameView {
  private final Container userPane;
  private final Container managePane;
  private final Container dollarCostAverageStrategyPane;
  private DollarCostAveragingView dollarPnel;
  // Two main containers of the UI screen.
  private final JLabel currentPortfolio;
  private JButton createPortfolio;
  private JButton exitButton;
  private JButton managePortfolio;
  private JTextField createPortfolioInput;
  private JTextField selectPortfolioInput;
  private JTextField companyNameInput;
  private JTextField tickerSymbolInput;
  private JTextField quantityInput;
  private JTextField commissionInput;
  private JLabel companyNameLabel;
  private JLabel tickerSymbolLabel;
  private JLabel refDateLabel;
  private JLabel commissionLabel;
  private JLabel quantityLabel;
  private JButton buyStockButton;
  private JButton getCostBiasButton;
  private JButton getValueButton;
  private JButton displayContentButton;
  private JButton backButton;
  private JButton importButton;
  private JButton exportButton;
  private String selectedPortfolio;
  private JRadioButton buyButton;
  private JRadioButton costBiasButton;
  private JRadioButton valueButton;
  private JRadioButton quantityButton;
  private JRadioButton investAmountButton;
  private JTextArea displayArea;
  private JTextArea portfolioDisplayArea;
  private JTextField month;
  private JTextField day;
  private JTextField year;
  private JTextField hour;
  private JTextField min;
  private JTextField sec;
  private JLabel monthLabel;
  private JLabel dayLabel;
  private JLabel yearLabel;
  private JLabel hourLabel;
  private JLabel minLabel;
  private JLabel secLabel;
  private JButton createPortfolioWithStrategy;
  private JButton backFromStrategy;

  /**
   * GUI based view constructor. Creates a master container representing the basic GUI design
   * having to main fram pane the managePane and the userpane.
   */
  public JFrameView() {
    super();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setMinimumSize(new Dimension(900, 600));
    userPane = new JPanel();
    managePane = new JPanel();
    dollarCostAverageStrategyPane = new JPanel();
    userPane.setLayout(new GridLayout(4, 1, 10, 10));
    managePane.setLayout(new GridLayout(5, 1, 10, 10));
    dollarCostAverageStrategyPane.setLayout(new BorderLayout(10,10));
    this.add(userPane);
    setCreatePortfolioPane(userPane);
    setSelectPortfolioPane(userPane);
    seeAllAvailablePortfolioList(userPane);
    setExitImportPortfolioPane(userPane);
    currentPortfolio = new JLabel("Portfolio Selected:");
    setTaskSelectionPane(managePane);
    setInputPane(managePane);
    setButtonPane(managePane);
    setDisplayArea(managePane);
    setFooterPane(managePane);
    displayBuyStockFields();
    displayQuantity();
    pack();
    setVisible(true);
    managePane.setVisible(false);
    setStrategyPanel();
    registerBasicActionListners();
  }

  private void setStrategyPanel() {
    dollarPnel = new DollarCostAveragingView();
    backFromStrategy = new JButton("Back to Create and Manage Portfolios.");
    dollarCostAverageStrategyPane.add(dollarPnel);
    dollarCostAverageStrategyPane.setVisible(false);
  }

  private void registerBasicActionListners() {
    buyButton.addActionListener(l -> displayBuyStockFields());
    costBiasButton.addActionListener(l -> displayCostBiasContent());
    valueButton.addActionListener(l -> displayGetValueContent());
    quantityButton.addActionListener(l -> displayQuantity());
    investAmountButton.addActionListener(l -> displayInvestmentAmount());
    backButton.addActionListener(l -> {
      hideManagePane();
      showUserPane();
    });
    backFromStrategy.addActionListener(l->{
      hideManagePane();
      hideStrategyPane();
      dollarPnel.executeBackCleanUP();
      showUserPane();
    });
  }


  private void setSelectPortfolioPane(Container parentPanel) {
    Container pane = new JPanel();
    pane.setLayout(new BorderLayout(10, 10));
    JLabel selectPortfolioDisplay = new JLabel("Enter the portfolio \n name to be managed.");
    pane.add(selectPortfolioDisplay, BorderLayout.WEST);
    selectPortfolioInput = new JTextField(10);
    pane.add(selectPortfolioInput, BorderLayout.CENTER);
    managePortfolio = new JButton("Manage Portfolio");
    managePortfolio.setActionCommand("Manage Portfolio Button");
    pane.add(managePortfolio, BorderLayout.EAST);
    pane.setSize(5, 5);
    parentPanel.add(pane);
  }

  private void setExitImportPortfolioPane(Container parentPanel) {
    Container pane = new JPanel();
    pane.setLayout(new FlowLayout());
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit Button");
    importButton = new JButton("Import");
    pane.add(importButton);
    pane.add(exitButton);
    parentPanel.add(pane);
  }

  private void setCreatePortfolioPane(Container parentPanel) {
    Container pane = new JPanel();
    pane.setLayout(new BorderLayout(10, 10));
    JLabel createPortfolioDisplay = new JLabel("Enter the portfolio \n name to be created.");
    pane.add(createPortfolioDisplay, BorderLayout.WEST);
    createPortfolioInput = new JTextField(10);
    pane.add(createPortfolioInput, BorderLayout.CENTER);
    createPortfolio = new JButton("Create Portfolio");
    createPortfolioWithStrategy = new JButton("Create Portfolio Using Strategy");
    createPortfolio.setActionCommand("Create Portfolio Button");
    pane.add(createPortfolio, BorderLayout.EAST);
    pane.add(createPortfolioWithStrategy, BorderLayout.SOUTH);
    pane.setSize(10, 10);
    parentPanel.add(pane);
  }

  private void seeAllAvailablePortfolioList(Container parentPanel) {
    portfolioDisplayArea = new JTextArea();
    portfolioDisplayArea.setEditable(false);
    JScrollPane display = new JScrollPane(portfolioDisplayArea
            , JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    parentPanel.add(display);
  }

  private void setTaskSelectionPane(Container parentPanel) {
    Container pane = new JPanel();
    pane.setLayout(new GridLayout(1, 2));
    pane.add(currentPortfolio);
    Container radioPanel = new JPanel();
    radioPanel.setLayout(new GridLayout(1, 3));
    buyButton = new JRadioButton("BUY Stocks", true);
    costBiasButton = new JRadioButton("Get Cost Bias");
    valueButton = new JRadioButton("Get Value");
    ButtonGroup taskSelection = new ButtonGroup();
    taskSelection.add(buyButton);
    taskSelection.add(costBiasButton);
    taskSelection.add(valueButton);
    radioPanel.add(buyButton);
    radioPanel.add(costBiasButton);
    radioPanel.add(valueButton);
    pane.add(radioPanel);
    parentPanel.add(pane);

  }

  private void setInputPane(Container parentPanel) {
    Container pane = new JPanel();
    pane.setLayout(new GridLayout(3, 4, 5, 5));
    companyNameLabel = new JLabel("Company Name(Optional)");
    pane.add(companyNameLabel);
    companyNameInput = new JTextField();
    pane.add(companyNameInput);
    tickerSymbolLabel = new JLabel("Ticker Symbol(Mandatory)");
    pane.add(tickerSymbolLabel);
    tickerSymbolInput = new JTextField();
    pane.add(tickerSymbolInput);
    quantityLabel = new JLabel("Quantity");
    pane.add(quantityLabel);
    quantityInput = new JTextField();
    pane.add(quantityInput);
    refDateLabel = new JLabel("Purchase Date and Time(24 Hour format)");
    pane.add(refDateLabel);
    insertDateFields(pane);
    commissionLabel = new JLabel("Commission");
    pane.add(commissionLabel);
    commissionInput = new JTextField();
    pane.add(commissionInput);
   // dollarCostAverageStrategyPane.add(new DollarCostAveragingView(selectedPortfolio),BorderLayout.CENTER);
    setTradeSelectionPane(pane);
    parentPanel.add(pane);
  }

  private void setTradeSelectionPane(Container parentPanel) {
    Container radioPanel = new JPanel();
    radioPanel.setLayout(new GridLayout(2, 1, 5, 5));
    quantityButton = new JRadioButton("Quantity", true);
    investAmountButton = new JRadioButton("Investment Amount");
    ButtonGroup tradeSelection = new ButtonGroup();
    tradeSelection.add(quantityButton);
    tradeSelection.add(investAmountButton);
    radioPanel.add(quantityButton);
    radioPanel.add(investAmountButton);
    parentPanel.add(radioPanel);

  }

  private void insertDateFields(Container parentPanel) {
    Container datePane = new JPanel();
    datePane.setLayout(new GridLayout(2, 6));
    month = new JTextField();
    datePane.add(month);
    day = new JTextField();
    datePane.add(day);
    year = new JTextField();
    datePane.add(year);
    hour = new JTextField();
    datePane.add(hour);
    min = new JTextField();
    datePane.add(min);
    sec = new JTextField();
    datePane.add(sec);
    monthLabel = new JLabel();
    monthLabel.setText("Month");
    datePane.add(monthLabel);
    dayLabel = new JLabel();
    dayLabel.setText("Day");
    datePane.add(dayLabel);
    yearLabel = new JLabel();
    yearLabel.setText("Year");
    datePane.add(yearLabel);
    hourLabel = new JLabel();
    hourLabel.setText("Hour");
    datePane.add(hourLabel);
    minLabel = new JLabel();
    minLabel.setText("Min");
    datePane.add(minLabel);
    secLabel = new JLabel();
    secLabel.setText("Sec");
    datePane.add(secLabel);
    parentPanel.add(datePane);
  }

  private void setButtonPane(Container parentPanel) {
    Container pane = new JPanel();
    pane.setLayout(new GridLayout(2, 2));
    buyStockButton = new JButton("Buy Stocks");
    pane.add(buyStockButton);
    getCostBiasButton = new JButton("Get Cost Bias");
    pane.add(getCostBiasButton);
    getValueButton = new JButton("Get Value");
    pane.add(getValueButton);
    displayContentButton = new JButton("Display Portfolio Contents");
    pane.add(displayContentButton);
    parentPanel.add(pane);
  }

  private void setDisplayArea(Container parentPanel) {
    displayArea = new JTextArea();
    displayArea.setEditable(false);
    JScrollPane displayPane = new JScrollPane(displayArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    parentPanel.add(displayPane);
  }

  private void setFooterPane(Container parentPanel) {
    Container pane = new JPanel();
    pane.setLayout(new FlowLayout());
    exportButton = new JButton("Export");
    pane.add(exportButton);
    backButton = new JButton("Back");
    pane.add(backButton);
    parentPanel.add(pane);
  }

  private void displayBuyStockFields() {
    getCostBiasButton.setVisible(false);
    getValueButton.setVisible(false);
    buyStockButton.setVisible(true);
    refDateLabel.setText("Purchase Date and Time");
    refDateLabel.setVisible(true);
    month.setVisible(true);
    day.setVisible(true);
    year.setVisible(true);
    monthLabel.setVisible(true);
    dayLabel.setVisible(true);
    yearLabel.setVisible(true);
    setBuyInputs(true);

  }

  private void displayCostBiasContent() {
    buyStockButton.setVisible(false);
    getValueButton.setVisible(false);
    refDateLabel.setText("Reference Date");
    setBuyInputs(false);

    getCostBiasButton.setVisible(true);
    refDateLabel.setVisible(true);
    month.setVisible(true);
    day.setVisible(true);
    year.setVisible(true);
    monthLabel.setVisible(true);
    dayLabel.setVisible(true);
    yearLabel.setVisible(true);
  }

  private void displayGetValueContent() {
    getCostBiasButton.setVisible(false);
    buyStockButton.setVisible(false);
    refDateLabel.setText("Reference Date");
    setBuyInputs(false);

    getValueButton.setVisible(true);
    refDateLabel.setVisible(true);
    month.setVisible(true);
    day.setVisible(true);
    year.setVisible(true);
    monthLabel.setVisible(true);
    dayLabel.setVisible(true);
    yearLabel.setVisible(true);
  }

  private void setBuyInputs(boolean visibility) {
    companyNameLabel.setVisible(visibility);
    companyNameInput.setVisible(visibility);
    tickerSymbolLabel.setVisible(visibility);
    tickerSymbolInput.setVisible(visibility);
    quantityLabel.setVisible(visibility);
    quantityInput.setVisible(visibility);
    commissionLabel.setVisible(visibility);
    commissionInput.setVisible(visibility);
    hour.setVisible(visibility);
    min.setVisible(visibility);
    sec.setVisible(visibility);
    hourLabel.setVisible(visibility);
    minLabel.setVisible(visibility);
    secLabel.setVisible(visibility);
  }

  private void displayQuantity() {
    quantityLabel.setText("Quantity");
  }

  private void displayInvestmentAmount() {
    quantityLabel.setText("Investment Amount");
  }


  public void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
  }

  public void showSuccessMessage(String message) {
    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
            JOptionPane.INFORMATION_MESSAGE);
  }

  public void hideUserPane() {
    userPane.setVisible(false);
    this.remove(userPane);
  }

  @Override
  public void showManagePortfolioPane(String portfolioName) {
    managePane.setVisible(true);
    updateCurrentPortfolio(portfolioName);
    this.add(managePane);
  }

  public void hideManagePane() {
    managePane.setVisible(false);
    this.remove(managePane);
  }

  private void hideStrategyPane() {
    dollarCostAverageStrategyPane.setVisible(false);
    //dollarCostAverageStrategyPane.remove();
    this.remove(dollarCostAverageStrategyPane);
  }

  public void showUserPane() {
    userPane.setVisible(true);
    this.add(userPane);
  }

  @Override
  public Map<String, Object> getStrategyFields() {
    return dollarPnel.getStrategyFeild();
  }

  private void updateCurrentPortfolio(String portfolioName) {
    selectedPortfolio = portfolioName;
    currentPortfolio.setText("Portfolio Selected: " + portfolioName);
  }

  /**
   * Accept the set of callbacks from the controller, and hook up as needed to various things in
   * this view.
   *
   * @param features the set of feature callbacks as a Features object
   */
  @Override
  public void setFeatures(IFeatures features) {
    dollarPnel.setFeatures(features);
    //process createPortfolioInput is tied to the echo button
    createPortfolio.addActionListener(l -> {
      features.createPortfolio(createPortfolioInput.getText());
      List<String> portfolioNames = features.getAllPortfolioNames();
      displayPortfolioNames(portfolioNames);
      clearScreenOneTextFeild();
    });
    createPortfolioWithStrategy.addActionListener(l -> {
      if (features.createPortfolio(createPortfolioInput.getText())) {
        List<String> portfolioNames = features.getAllPortfolioNames();
        selectedPortfolio = createPortfolioInput.getText();
        displayPortfolioNames(portfolioNames);
        enableStrategyScreen(features);
      }
    });
    managePortfolio.addActionListener(l -> {
      features.validatePortfolioName(selectPortfolioInput.getText());
      clearScreenOneTextFeild();
    });
    buyStockButton.addActionListener(new BuyStockButtonListener(features));
    displayContentButton.addActionListener(new DisplayPortfolioButtonListener(features));
    getValueButton.addActionListener(new GetValueButtonListener(features));
    getCostBiasButton.addActionListener((new CostBiasButtonListener(features)));
    importButton.addActionListener(new ImportPortfolioButtonListener(features));
    exportButton.addActionListener(l -> {
      try {
        features.exportPortfolio(selectedPortfolio,
                getPathFromChooser(JFileChooser.DIRECTORIES_ONLY));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    exitButton.addActionListener(l -> features.exitProgram());
  }

  private void enableStrategyScreen(IFeatures features) {
    hideUserPane();
    dollarCostAverageStrategyPane.add(backFromStrategy,BorderLayout.SOUTH);
    dollarCostAverageStrategyPane.setVisible(true);
    dollarPnel.setPortfolioName(selectedPortfolio);
    this.add(dollarCostAverageStrategyPane);
  }

  private void clearScreenOneTextFeild() {
    createPortfolioInput.setText("");
    selectPortfolioInput.setText("");
    displayArea.setText("");
  }

  private String getPathFromChooser(int jFileChooser) {
    JFileChooser chooser;
    //Default directory as desktop
    chooser = new JFileChooser(new File(System.getProperty("user.home") + "\\Desktop"));
    chooser.setDialogTitle("Select Location");
    chooser.setFileSelectionMode(jFileChooser);
    chooser.setAcceptAllFileFilterUsed(false);
    if (chooser.showSaveDialog(new JDialog()) == JFileChooser.APPROVE_OPTION) {
      return chooser.getSelectedFile().getPath();
    }
    return null;
  }

  private Date getdate(String text) {
    Date date = null;
    try {
      SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
      df.setLenient(false);
      date = df.parse(text);
    } catch (ParseException e) {
      showErrorMessage("Invalid Date. Try Again.");
      return null;
    }
    return date;
  }

  private class BuyStockButtonListener implements ActionListener {
    IFeatures features;

    private BuyStockButtonListener(IFeatures f) {
      this.features = f;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (tickerSymbolInput.getText().isEmpty()) {
        showErrorMessage("Ticker symbol cannot be empty");
        return;
      }
      Date date = null;
      try {
        float commission = Float.parseFloat(commissionInput.getText());
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        df.setLenient(false);
        String dateInput = String.format("%s/%s/%s %s:%s:%s"
                , month.getText()
                , day.getText()
                , year.getText()
                , hour.getText()
                , min.getText(),
                sec.getText());
        date = df.parse(dateInput);

        if (quantityButton.isSelected()) {
          int quantity = Integer.parseInt(quantityInput.getText());
          if (features.buyStocks(selectedPortfolio, TradeType.BUY, date,
                  tickerSymbolInput.getText(), companyNameInput.getText()
                  , quantity, commission)) {
            showSuccessMessage("Trade completed successfully");
          }
        } else {
          float investmentAmount = Float.parseFloat(quantityInput.getText());
          if (features.buyStocks(selectedPortfolio, TradeType.BUY, date,
                  tickerSymbolInput.getText(), companyNameInput.getText()
                  , investmentAmount, commission)) {
            showSuccessMessage("Trade completed successfully");
          }
        }
        clearTextFields();

      } catch (ParseException exception) {
        showErrorMessage("Invalid Date. Try Again.");
      } catch (NumberFormatException ex) {
        showErrorMessage("Invalid numeric Inputs please check");
      }
    }
  }


  private class DisplayPortfolioButtonListener implements ActionListener {
    IFeatures features;

    private DisplayPortfolioButtonListener(IFeatures f) {
      this.features = f;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      displayArea.setText("");
      IPortfolioV2 portfolio = features.getPortfolioContents(selectedPortfolio);
      StringBuilder contents = new StringBuilder();
      for (IStock stock : portfolio.getOwnedStocks()) {
        contents.append("Company Name :- " + stock.getCompanyName()
                + "\t Ticker Symbol :- " + stock.getTickerSymbol());
        contents.append("\n");
        contents.append("Quantity :" + stock.getQuantity());
        contents.append("\n");
        contents.append("Cost Price :$" + stock.getCostPrice());
        contents.append("\n");
        contents.append("Purchase Date : " + stock.getPurchaseDate());
        contents.append("\n");
        contents.append("*******************************************");
        contents.append("\n");
      }
      displayArea.append(contents.toString());
    }
  }

  private class ImportPortfolioButtonListener implements ActionListener {
    IFeatures features;

    private ImportPortfolioButtonListener(IFeatures f) {
      this.features = f;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      String fileID = getPathFromChooser(JFileChooser.FILES_ONLY);
      try {
        features.importPortfolio(fileID);
        List<String> portfolioNames = features.getAllPortfolioNames();
        displayPortfolioNames(portfolioNames);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }

  private class CostBiasButtonListener implements ActionListener {
    IFeatures features;

    private CostBiasButtonListener(IFeatures f) {
      this.features = f;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      displayArea.setText("");
      String dateInput = String.format("%s/%s/%s %s:%s:%s"
              , month.getText()
              , day.getText()
              , year.getText()
              , hour.getText()
              , min.getText(),
              sec.getText());
      Date ref = getdate(dateInput);
      float costBias = features.getPortfolioCostBias(selectedPortfolio, ref);
      displayArea.append("Cost Bias: $" + costBias);
      clearTextFields();
    }
  }

  private class GetValueButtonListener implements ActionListener {
    IFeatures features;

    private GetValueButtonListener(IFeatures f) {
      this.features = f;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      displayArea.setText("");
      String dateInput = String.format("%s/%s/%s %s:%s:%s"
              , month.getText()
              , day.getText()
              , year.getText()
              , hour.getText()
              , min.getText(),
              sec.getText());
      Date ref = getdate(dateInput);
      float portfolioValue = features.getPortfolioValue(selectedPortfolio, ref);
      displayArea.append("Portfolio Value: $" + portfolioValue);
      clearTextFields();
    }
  }


  private void clearTextFields() {
    companyNameInput.setText("");
    tickerSymbolInput.setText("");
    quantityInput.setText("");
    commissionInput.setText("");
    month.setText("");
    day.setText("");
    year.setText("");
    hour.setText("");
    min.setText("");
    sec.setText("");
  }

  private void displayPortfolioNames(List<String> portfolioNames) {
    portfolioDisplayArea.setText("");
    portfolioDisplayArea.append("Current Portfolios:\n");
    for (String portfolio : portfolioNames) {
      portfolioDisplayArea.append(portfolio + "\n");
    }
  }


}
