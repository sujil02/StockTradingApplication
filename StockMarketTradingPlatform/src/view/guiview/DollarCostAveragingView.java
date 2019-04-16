package view.guiview;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import controller.IFeatures;

public class DollarCostAveragingView extends JPanel {
  private JTextField dollarAmountInvested;
  private JLabel dollarAmountInvestLabel;
  private JLabel portfolioNameLabel;
  private JLabel stockTickerSymbolLabel;
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
  private JLabel durationLable;
  private JLabel startDateLabel;
  private JLabel endDateLable;
  private JTextField duration;
  private JButton executeStrategy;
  private JButton importStrategy;


  public DollarCostAveragingView() {
    strategy = new HashMap<>();
    this.setLayout(new GridLayout(7, 1, 10, 10));
    setDollarAmountInvestment();
    setWeightsCheckBox();
    setStockEntryFeilds();
    setDateCapturePanel();
    displayArea = new JTextArea(50000, 50);
    JScrollPane display = new JScrollPane(displayArea
            , JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(display);
    setExportStrategyButton();
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
        enterWeights.setEnabled(false);
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
    Container pane = new JPanel(new GridLayout(1, 4, 5, 5));
    dollarAmountInvestLabel = new JLabel("Enter the amount to be invested in dollars $");
    pane.add(dollarAmountInvestLabel);
    dollarAmountInvested = new JTextField();
    pane.add(dollarAmountInvested);
    this.add(pane);
  }

  private void setStockEntryFeilds() {
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
    this.add(pane);
  }


  public void setFeatures(IFeatures features) {
    exportStrategy.addActionListener(l -> {
      try {
        features.exportStrategy(
                getPathFromChooser(JFileChooser.DIRECTORIES_ONLY));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    executeStrategy.addActionListener(l-> {
      try {
        features.executeStrategy(features);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    addChoice.addActionListener(l -> {
      addToCombinationToMap();
    });
  }

  private void addToCombinationToMap() {
    String tickerSymbol = stockTickerSymbolText.getText();
    if (tickerSymbol == null) {
      JOptionPane.showMessageDialog(new JFrame(), "Ticker Symbol cannot be null", "Dialog", JOptionPane.ERROR_MESSAGE);
    }
    float weight = 0.0f;
    try {
      if (stockWeightText.getText().equalsIgnoreCase("")) {
        strategy.put(tickerSymbol, 0.0f);
        recalculateWeight();
      } else {
        weight = Float.parseFloat(stockWeightText.getText());
        strategy.put(tickerSymbol, weight);
      }
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(new JFrame(), "Invalid weight entry", "Dialog", JOptionPane.ERROR_MESSAGE);
      stockWeightText.setText("");
    }
    display();
  }

  private void recalculateWeight() {
    float newValue = Float.valueOf(100 / strategy.size());
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
      clearSymbolAndWeight();
    }
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

  public void executeBackCleanUP() {
    enterWeights.setEnabled(true);
  }

  public void setPortfolioName(String selectedPortfolio) {
    this.selectedPortfolio = selectedPortfolio;
    portfolioNameLabel = new JLabel("Selected Portfolio is : " + selectedPortfolio);
    this.add(portfolioNameLabel, 0);

  }

  public Map<String, Object> getStrategyFeild() {
    try {
      Map<String, Object> parameters = new HashMap<>();
      parameters.put("portfolioName", selectedPortfolio);
      parameters.put("tickerSymbols", strategy);
      float investment = Float.parseFloat(dollarAmountInvested.getText());
      parameters.put("investmentAmount",investment);
      //float commission = Float.parseFloat()
    } catch (NumberFormatException e){
      throw new IllegalArgumentException("Invalid parameter");
    }
    return null;
  }
}
