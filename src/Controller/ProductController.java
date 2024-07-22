package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.sound.midi.MetaMessage;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Model.ProductModel;
import View.ProductView;
import Controller.SQL;

public class ProductController {
    
    // Members

    private final ProductView pdView;
    private final ProductModel pdModel;

    private final ProductButtonListener pdButtonListener;

    // Accessors and Mutators

    public ProductButtonListener getProductButtonListener() {
        return pdButtonListener;
    }

    // Constructors

    public ProductController(final ProductView getView, final ProductModel getModel) {
        this.pdView = getView;
        this.pdModel = getModel;
        pdButtonListener = new ProductButtonListener(getView, getModel);
    }
}

class ProductButtonListener implements ActionListener {

    // Members. 

    private final ProductView pdView;
    private final ProductModel pdModel;

    // Constructors

    public ProductButtonListener(final ProductView getView, final ProductModel getModel) {
        this.pdView = getView;
        this.pdModel = getModel;
    }

    public void clearFilters() {
        this.pdModel.productNameFilterTextField.setText("");
        this.pdModel.productMaxPriceFilterTextField.setText("");
        this.pdModel.productMinPriceFilterTextField.setText("");
        this.pdModel.productSupplierSearchFilterTextField.setText("");
        this.pdModel.productStockMinimumFilterTextField.setText("");
        this.pdModel.productIDTextField.setText("");
        pdModel.productSupplierNameSearchFilterTextField.setText("");
    }

    public void populateTable(boolean populateFlag) {

        String[] getFilters = new String[] {
            pdModel.productNameFilterTextField.getText(),
            pdModel.productMinPriceFilterTextField.getText(),
            pdModel.productMaxPriceFilterTextField.getText(),
            pdModel.productSupplierSearchFilterTextField.getText(),
            pdModel.productStockMinimumFilterTextField.getText(),
            pdModel.productIDTextField.getText(),
            pdModel.productSupplierNameSearchFilterTextField.getText()
        };

        // Obtain a 2D array of all data related to a query.
        try {

            String[][] GetData = SQL.getProductPopulation(getFilters, populateFlag);


            // Create a new table model with the new fetched data (even if it's empty) and assign the JTable a new table model. 
            String[] tempTableNames = { "PID", "Name", "MSRP", "Stock", "Supplier Name", "Supplier ID"};
            DefaultTableModel newTable = new DefaultTableModel(GetData, tempTableNames);
            pdModel.productTable.setModel(newTable);

            if (GetData.length == 0) {
                pdModel.productStatusLabel.setText("Search did not return any data, or failed. Please try again with different filters.");
                pdModel.productStatusLabel.setForeground(new Color(255,102,0));
            }
            else 
            {
                pdModel.productStatusLabel.setText("Search successful.");
                pdModel.productStatusLabel.setForeground(new Color(0,102,0));
            }
        }
        catch(Exception e) {
                pdModel.productStatusLabel.setText("Search failed due to an exception. Please try again with different filters.");
                pdModel.productStatusLabel.setForeground(new Color(153,0,0));
        }
        

        // Refresh the view, just in case.
        
        SwingUtilities.updateComponentTreeUI(pdView);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        final String getEvent = event.getActionCommand();

        /* There are three actions that can occur:
         * Search button (using 'Start Search')
         *  Executes an SQL query to populate the table.
         * Refresh button (using 'Start Unfiltered Search')
         *  Executes an SQL query to populate the table without considering filters.
         * Clear button (using 'Clear Filters')
         *  Clears all JTextField and JFormattedTextField objects of their contents.
         */
		
		if(getEvent.equals("Start Search"))
		{
			populateTable(false);
		}
		else if(getEvent.equals("Start Unfiltered Search"))
		{
            populateTable(true);
		}
		else if(getEvent.equals("Clear Filters"))
		{
			clearFilters();
		}

        SwingUtilities.updateComponentTreeUI(pdView);
        
    }

}
