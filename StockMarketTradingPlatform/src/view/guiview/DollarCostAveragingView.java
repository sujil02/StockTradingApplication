package view.guiview;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class DollarCostAveragingView extends JPanel {
  private JTextField dollarAmountInvested;
  private JLabel dollarAmountInvestLable;
  private JLabel portfolioNameLabel;
  private JLabel stockTickerSymbolLabel;
  private JLabel stockWeightLabel;
  private JTextField stockTickerSymbolText;
  private JTextField stockWeightText;
  private JCheckBox enterWeights;
  private JButton addChoice;
  private JTextArea displayArea;
  private Map<String, Double> strategy;


  public DollarCostAveragingView(String portfolioName) {
    strategy = new HashMap<>();
    this.setLayout(new GridLayout(5, 1));
    portfolioNameLabel = new JLabel("Selected Portfolio is : " + portfolioName);
    this.add(portfolioNameLabel);
    setDollarAmountInvestment();
    setWeightsCheckBox();
    setStockEntryFeilds();
    registerBasicActionListners();
    displayArea = new JTextArea();
    this.add(displayArea);
  }

  private void setWeightsCheckBox() {
    Container pane = new JPanel(new GridLayout());
    enterWeights = new JCheckBox("Do u want to enter weights");
    pane.add(enterWeights);
    this.add(pane);
  }

  private void setDollarAmountInvestment() {
    Container pane = new JPanel(new GridLayout(1, 4, 5, 5));
    dollarAmountInvestLable = new JLabel("Enter the amount to be invested in dollars $");
    pane.add(dollarAmountInvestLable);
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
    addChoice = new JButton("ADD");
    pane.add(stockTickerSymbolLabel);
    pane.add(stockTickerSymbolText);
    pane.add(stockWeightLabel);
    pane.add(stockWeightText);
    pane.add(addChoice);
    this.add(pane);
  }


  private void registerBasicActionListners() {
    addChoice.addActionListener(l -> {
      addToCombinationToMap();
    });
  }

  private void addToCombinationToMap() {
    String tickerSymbol = stockTickerSymbolText.getText();
    if (tickerSymbol == null) {
      JOptionPane.showMessageDialog(new JFrame(), "Ticker Symbol cannot be null", "Dialog", JOptionPane.ERROR_MESSAGE);
    }
    Double weight = 0.0;
    try {
      weight = Double.parseDouble(stockWeightText.getText());
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(new JFrame(), "Invalid weight entry", "Dialog", JOptionPane.ERROR_MESSAGE);
      stockWeightText.setText("");
    }
    strategy.put(tickerSymbol, weight);
  }
}
