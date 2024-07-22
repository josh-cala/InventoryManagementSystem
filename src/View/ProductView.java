package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.concurrent.Flow;

import javax.swing.JFrame;

import Controller.ProductController;
import Model.ProductModel;
import View.Grid;

public class ProductView extends JFrame {

    // Members

    private final ProductController pdController;
    private final ProductModel pdModel;

    private final Grid grid;

    // Constructor.

    public ProductView() {
        pdModel = new ProductModel(this);
        pdController = new ProductController(this, pdModel);

        // Using similar values to the Main view, for consistency.
        grid = new Grid();

        // Start frames.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("CS 442 Project");
        this.setSize(1600,900);
		this.setLayout(new BorderLayout());

        pdModel.commandPanel.setLayout(new GridBagLayout());
        pdModel.listPanel.setLayout(new GridBagLayout());

        // Add buttons.
        grid.setGrid(0, 0);
        grid.setInset(10, 15, 10, 15);
        grid.setGrid(1, 0);
        pdModel.executeSearchButton.addActionListener(pdController.getProductButtonListener());
        pdModel.commandPanel.add(pdModel.executeSearchButton, grid);
        grid.setGrid(2, 0);
        pdModel.executeRefreshButton.addActionListener(pdController.getProductButtonListener());
        pdModel.commandPanel.add(pdModel.executeRefreshButton, grid);
        grid.setGrid(3, 0);
        pdModel.executeClearButton.addActionListener(pdController.getProductButtonListener());
        pdModel.commandPanel.add(pdModel.executeClearButton, grid);

        // Add status label
        grid.setGrid(4, 0);
        pdModel.commandPanel.add(pdModel.productStatusLabel, grid);

        // Add product id field.
        grid.setGrid(0, 1);
        grid.setInset(10, 15, 10, 15);
        pdModel.productIDTextField.setColumns(10);
        grid.setGrid(0, 2);
        pdModel.commandPanel.add(pdModel.productIDLabel, grid);
        grid.setGrid(1, 2);
        pdModel.commandPanel.add(pdModel.productIDTextField, grid);
        
        // Add product name field
        grid.setGrid(0, 3);
        pdModel.productNameFilterTextField.setColumns(10);
        pdModel.commandPanel.add(pdModel.productNameFilterLabel, grid);
        grid.setGrid(1, 3);
        pdModel.commandPanel.add(pdModel.productNameFilterTextField, grid);

        // Add min price field.
        grid.setGrid(0, 4);
        pdModel.productMinPriceFilterTextField.setColumns(10);
        pdModel.commandPanel.add(pdModel.productMinPriceFilterLabel, grid);
        grid.setGrid(1, 4);
        pdModel.commandPanel.add(pdModel.productMinPriceFilterTextField, grid);

        // Add max price field.
        grid.setGrid(0, 5);
        pdModel.productMaxPriceFilterTextField.setColumns(10);
        pdModel.commandPanel.add(pdModel.productMaxPriceFilterLabel, grid);
        grid.setGrid(1, 5);
        pdModel.commandPanel.add(pdModel.productMaxPriceFilterTextField, grid);

        // Add supplier id field.
        grid.setGrid(0, 6);
        pdModel.productSupplierSearchFilterTextField.setColumns(10);
        pdModel.commandPanel.add(pdModel.productSupplierSearchFilterLabel, grid);
        grid.setGrid(1, 6);
        pdModel.commandPanel.add(pdModel.productSupplierSearchFilterTextField, grid);

        // Add supplier name field
        grid.setGrid(0, 7);
        pdModel.productSupplierNameSearchFilterTextField.setColumns(10);
        pdModel.commandPanel.add(pdModel.productSupplierNameSearchFilterLabel, grid);
        grid.setGrid(1, 7);
        pdModel.commandPanel.add(pdModel.productSupplierNameSearchFilterTextField, grid);

        // Add minimum stock field
        grid.setGrid(0, 8);
        pdModel.productStockMinimumFilterTextField.setColumns(10);
        pdModel.commandPanel.add(pdModel.productStockMinimumFilterLabel, grid);
        grid.setGrid(1, 8);
        pdModel.commandPanel.add(pdModel.productStockMinimumFilterTextField, grid);

        

        this.add(pdModel.commandPanel, BorderLayout.WEST); // Add it to the view.

        // Add JTable panel.

        grid.setGrid(0, 0);
        pdModel.listPanel.add(pdModel.productTable, grid);

        this.add(pdModel.listPanel, BorderLayout.EAST);





    }


}
