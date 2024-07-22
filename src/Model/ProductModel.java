package Model;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Label;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;
import javax.xml.parsers.DocumentBuilder;

import Controller.SQL;
import View.ProductView;

public class ProductModel {
    // Class members

    private final ProductView pdView;
    //private final JLabel productHeaderLabel;

    // Buttons for executing certain commands.
    
    public final JButton executeSearchButton;
    public final JButton executeRefreshButton;
    public final JButton executeClearButton;

    // Text fields for search filters.
    // Includes respective labels.

    public final JTextField productNameFilterTextField;
    public final JLabel productNameFilterLabel;

    public final JFormattedTextField productMinPriceFilterTextField;
    public final JLabel productMinPriceFilterLabel;

    public final JFormattedTextField productMaxPriceFilterTextField;
    public final JLabel productMaxPriceFilterLabel;

    public final JTextField productSupplierSearchFilterTextField;
    public final JLabel productSupplierSearchFilterLabel;
    
    public final JTextField productSupplierNameSearchFilterTextField;
    public final JLabel productSupplierNameSearchFilterLabel;

    public final JFormattedTextField productStockMinimumFilterTextField;
    public final JLabel productStockMinimumFilterLabel;

    public final JFormattedTextField productIDTextField;
    public final JLabel productIDLabel;
    

    // JTable declarations

    public final JTable productTable;

    // More J-stuff.

    public final JLabel productStatusLabel;

    // JPanel declarations

    public final JPanel filterPanel;
    public final JPanel listPanel;
    public final JPanel commandPanel;

    public final JPanel primaryPanel;

    public void addContainer(final Container getContainer) {
        primaryPanel.add(getContainer);
    }

    // constructor
    public ProductModel(final ProductView getView) {

        // Initializing everything.
        this.pdView = getView;

        executeSearchButton = new JButton("Start Search");
        executeRefreshButton = new JButton("Start Unfiltered Search");
        executeClearButton = new JButton("Clear Filters");

        primaryPanel = new JPanel();
        filterPanel = new JPanel();
        listPanel = new JPanel();
        commandPanel = new JPanel();

        productNameFilterLabel = new JLabel("Product Name");
        productMinPriceFilterLabel = new JLabel("Minimum Price");
        productMaxPriceFilterLabel = new JLabel("Maximum Price");
        productSupplierSearchFilterLabel = new JLabel("Supplier ID");
        productSupplierNameSearchFilterLabel = new JLabel("Supplier Name");
        productStockMinimumFilterLabel = new JLabel("Minimum In Stock");
        productIDLabel = new JLabel("Product ID");

        productNameFilterTextField = new JTextField();
        productMinPriceFilterTextField = new JFormattedTextField();
        productMaxPriceFilterTextField = new JFormattedTextField();
        productSupplierSearchFilterTextField = new JTextField();
        productSupplierNameSearchFilterTextField = new JTextField();
        productStockMinimumFilterTextField = new JFormattedTextField();
        productIDTextField = new JFormattedTextField();

        productStatusLabel = new JLabel("No data. Attempt a product search to populate product table.");

        String[][] pdTableData = {}; // Starts empty; populated later through SQL data fetching.
        String[] pdTableNames = { "PID", "Name", "MSRP", "Stock", "Supplier Name", "Supplier ID", "Supplier Name"}; // For naming the table columns.
        productTable = new JTable(pdTableData, pdTableNames);
    }
}


