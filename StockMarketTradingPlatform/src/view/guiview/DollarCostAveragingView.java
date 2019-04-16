package view.guiview;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import controller.IFeatures;

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
  private JButton exportStrategy;
  private String selectedPortfolio;


  public DollarCostAveragingView(String portfolioName) {
    selectedPortfolio = portfolioName;
    strategy = new HashMap<>();
    this.setLayout(new GridLayout(6, 1));
    portfolioNameLabel = new JLabel("Selected Portfolio is : " + portfolioName);
    this.add(portfolioNameLabel);
    setDollarAmountInvestment();
    setWeightsCheckBox();
    setStockEntryFeilds();
    setDateCapturePanel();
    displayArea = new JTextArea(50000,50);
    this.add(displayArea);
    setExportStrategyButton();
  }

  private void setExportStrategyButton() {
    Container pane = new Container();
    pane.setLayout(new FlowLayout());
    exportStrategy = new JButton("Export Strategy");
    pane.add(exportStrategy);
    this.add(pane);
  }

  private void setDateCapturePanel() {
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


  public void setFeatures( IFeatures features) {
    exportStrategy.addActionListener(l->{
        try {
          features.exportPortfolio(selectedPortfolio,
                  getPathFromChooser(JFileChooser.DIRECTORIES_ONLY));
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
    Double weight = 0.0;
    try {
      weight = Double.parseDouble(stockWeightText.getText());
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(new JFrame(), "Invalid weight entry", "Dialog", JOptionPane.ERROR_MESSAGE);
      stockWeightText.setText("");
    }
    strategy.put(tickerSymbol, weight);
    display();
  }

  private void display() {
    displayArea.append("Current Strategy pattern : \n");
    for(Map.Entry<String,Double> entry: strategy.entrySet()){
      displayArea.append(entry.getKey()+"\t"+entry.getValue());
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
}
