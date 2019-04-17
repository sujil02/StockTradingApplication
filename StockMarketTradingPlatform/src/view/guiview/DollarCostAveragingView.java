package view.guiview;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.IFeatures;

/**
 * This class represents the GUI based view for creating and executing strategies. Allows several
 * object to be created and displayed on the screen as the functionalitites. Also responsible to
 * respond on several button clicks having different action listener implementations.
 */
public class DollarCostAveragingView extends JPanel {
  private JTextField dollarAmountInvested;
  private JTextField commissionAmount;
  private JLabel portfolioNameLabel;
  private JLabel stockWeightLabel;
  private JTextField stockTickerSymbolText;
  private JTextField stockWeightText;
  private JCheckBox enterWeights;
  private JButton addChoice;
  private JTextArea displayArea;
  private Map<String, Float> strategy;
  private JButton exportStrategy;
  private String selectedPortfolio;
  private JXDatePicker startDatePicker;
  private JXDatePicker endDatePicker;
  private JTextField duration;
  private JButton executeStrategy;
  private JButton importStrategy;


  /**
   * GUI based view constructor. Creates a DollarCostAverage pane which will be loaded in Main
   * view.
   */
  public DollarCostAveragingView() {
    strategy = new HashMap<>();
    this.setLayout(new GridLayout(8, 1, 10, 10));
    setDollarAmountInvestment();
    setWeightsCheckBox();
    setStockEntryFeilds();
    setDateCapturePanel();
    displayArea = new JTextArea(50000, 50);
    displayArea.setEditable(false);
    JScrollPane display = new JScrollPane(displayArea
            , JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(display);
    setExportStrategyButton();
    portfolioNameLabel = new JLabel("Selected Portfolio is : ");
  }

  private void setExportStrategyButton() {
    Container pane = new Container();
    pane.setLayout(new FlowLayout());
    exportStrategy = new JButton("Export Strategy");
    executeStrategy = new JButton("Execute Strategy");
    importStrategy = new JButton("Import Strategy");
    pane.add(exportStrategy);
    pane.add(executeStrategy);
    pane.add(importStrategy);
    this.add(pane);
  }

  private void setDateCapturePanel() {
    JLabel durationLable;
    JLabel startDateLabel;
    JLabel endDateLable;
    Container pane = new JPanel(new GridLayout(1, 6, 5, 5));
    startDateLabel = new JLabel("Strategy Start Date");
    startDatePicker = new JXDatePicker();
    pane.add(startDateLabel);
    pane.add(startDatePicker);
    endDateLable = new JLabel("Strategy End Date");
    endDatePicker = new JXDatePicker();
    pane.add(endDateLable);
    pane.add(endDatePicker);
    durationLable = new JLabel("Execute in every (days):");
    duration = new JTextField();
    pane.add(durationLable);
    pane.add(duration);
    this.add(pane);
  }

  private void setWeightsCheckBox() {
    Container pane = new JPanel(new GridLayout());
    enterWeights = new JCheckBox("Do u want to enter weights");
    enterWeights.setEnabled(true);
    enterWeights.addActionListener(l -> {
      if (enterWeights.isSelected()) {
        //enterWeights.setEnabled(false);
        stockWeightText.setVisible(true);
        stockWeightLabel.setVisible(true);
      } else {
        stockWeightText.setVisible(false);
        stockWeightLabel.setVisible(false);
      }
    });
    pane.add(enterWeights);
    this.add(pane);
  }

  private void setDollarAmountInvestment() {
    JLabel commissionAmountLabel;
    JLabel dollarAmountInvestLabel;
    Container pane = new JPanel(new GridLayout(1, 4, 5, 5));
    dollarAmountInvestLabel = new JLabel("Investment Amount in dollars $");
    pane.add(dollarAmountInvestLabel);
    dollarAmountInvested = new JTextField();
    pane.add(dollarAmountInvested);
    commissionAmountLabel = new JLabel("Commission");
    commissionAmount = new JTextField();
    pane.add(commissionAmountLabel);
    pane.add(commissionAmount);
    this.add(pane);
  }

  private void setStockEntryFeilds() {
    JLabel stockTickerSymbolLabel;

    Container pane = new JPanel(new GridLayout(1, 5, 5, 5));
    stockTickerSymbolLabel = new JLabel("Ticker symbol");
    stockTickerSymbolText = new JTextField(20);
    stockWeightLabel = new JLabel("Weight");
    stockWeightText = new JTextField(3);
    stockWeightText.setVisible(false);
    stockWeightLabel.setVisible(false);
    addChoice = new JButton("ADD");
    pane.add(stockTickerSymbolLabel);
    pane.add(stockTickerSymbolText);
    pane.add(stockWeightLabel);
    pane.add(stockWeightText);
    pane.add(addChoice);
    JLabel addStockMessage;
    JLabel addStockMessage2;
    this.add(pane);
    addStockMessage = new JLabel(" ** To change any added stock values " +
            "please re-enter the same stock symbol and click add.");
    addStockMessage2 = new JLabel(" **To drop any value please enter weight as Negative.");
    Container pane2 = new JPanel(new GridLayout(2, 1));
    pane2.add(addStockMessage);
    pane2.add(addStockMessage2);
    this.add(pane2);
  }


  /**
   * Get the set of feature callbacks that the view can use.
   *
   * @param features the set of feature callbacks as a Features object
   */
  public void setFeatures(IFeatures features) {
    exportStrategy.addActionListener(l -> {
      try {
        String filePath = getPathFromChooser(JFileChooser.DIRECTORIES_ONLY);
        if (filePath != null) {
          features.exportStrategy(filePath);
          executeBackCleanUP();
        }
      } catch (IOException e) {
        showerrorMessages("Invalid file path entry");
      }
    });
    executeStrategy.addActionListener(l -> {
      try {
        features.executeStrategy(features);
        executeBackCleanUP();
      } catch (IOException e) {
        showerrorMessages("Invalid file path entry");
      }
    });
    importStrategy.addActionListener(l -> {
      try {
        String filePath = getPathFromChooser(JFileChooser.FILES_ONLY);
        if (filePath != null) {
          features.importStrategy(filePath, selectedPortfolio);
        }
      } catch (IOException e) {
        showerrorMessages("Invalid file path entry");
      }
    });
    addChoice.addActionListener(l -> {
      enterWeights.setEnabled(false);
      addToCombinationToMap();
    });
  }

  private void addToCombinationToMap() {
    String tickerSymbol = stockTickerSymbolText.getText();
    if (tickerSymbol == null) {
      JOptionPane.showMessageDialog(new JFrame(), "Ticker Symbol cannot be null",
              "Dialog", JOptionPane.ERROR_MESSAGE);
    }
    float weight = 0.0f;
    try {
      if (stockWeightText.getText().equalsIgnoreCase("")) {
        strategy.put(tickerSymbol, 100.0f);
        recalculateWeight();
      } else {
        weight = Float.parseFloat(stockWeightText.getText());
        if (weight >= 0) {
          strategy.put(tickerSymbol, weight);
        } else {
          strategy.remove(tickerSymbol);
        }
      }
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(new JFrame(), "Invalid weight entry",
              "Dialog", JOptionPane.ERROR_MESSAGE);
      stockWeightText.setText("");
    }
    display();
  }

  private void recalculateWeight() {
    float newValue = 100.0f / strategy.size();
    for (Map.Entry<String, Float> entry : strategy.entrySet()) {
      entry.setValue(newValue);
    }
  }

  private void display() {
    displayArea.setText("");
    displayArea.append("Current Strategy pattern : \n");
    for (Map.Entry<String, Float> entry : strategy.entrySet()) {
      displayArea.append(entry.getKey() + "\t" + entry.getValue());
      displayArea.append("\n");
    }
    clearSymbolAndWeight();
  }

  private void clearSymbolAndWeight() {
    stockTickerSymbolText.setText("");
    stockWeightText.setText("");
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

  /**
   * Clears the entered data in the text field.
   */
  public void executeBackCleanUP() {
    strategy = new HashMap<>();
    dollarAmountInvested.setText("");
    commissionAmount.setText("");
    stockTickerSymbolText.setText("");
    stockWeightText.setText("");
    displayArea.setText("");
    enterWeights.setEnabled(true);
    duration.setText("");
  }

  /**
   * Sets the selected portfolio name in top of view.
   *
   * @param selectedPortfolio selected portfolio name.
   */
  public void setPortfolioName(String selectedPortfolio) {
    this.selectedPortfolio = selectedPortfolio;
    portfolioNameLabel.setText("Selected Portfolio is : " + selectedPortfolio);
    this.add(portfolioNameLabel, 0);

  }

  /**
   * collects all the parameters entered by user and creates a dictionary with all the parameters.
   *
   * @return Map with all the parameters. Following are the keys used in the map to retrieve the
   * parameters. portfolioName, tickerSymbols, investmentAmount, commission, startDate, endDate,
   * frequency.
   */
  public Map<String, Object> getStrategyFeild() {
    Map<String, Object> parameters = new HashMap<>();
    try {
      parameters.put("portfolioName", selectedPortfolio);
      Map<String, Float> tickerSymbols = new HashMap<>();
      for (String tickerSymbol : strategy.keySet()) {
        tickerSymbols.put(tickerSymbol, strategy.get(tickerSymbol) / 100);
      }
      parameters.put("tickerSymbols", tickerSymbols);
      float investment = Float.parseFloat(dollarAmountInvested.getText());
      parameters.put("investmentAmount", investment);
      float commission = Float.parseFloat(commissionAmount.getText().isEmpty()
              ? "0" : commissionAmount.getText());
      parameters.put("commission", commission);
      Date startDate = startDatePicker.getDate();
      parameters.put("startDate", startDate);
      Date endDate = endDatePicker.getDate();
      parameters.put("endDate", endDate);
      int frequency = 1;
      if (!duration.getText().isEmpty()) {
        frequency = Integer.parseInt(duration.getText());
      }
      parameters.put("frequency", frequency);

    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid parameter");
    }
    return parameters;
  }

  private void showerrorMessages(String message) {
    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
            JOptionPane.ERROR_MESSAGE);
  }
}
