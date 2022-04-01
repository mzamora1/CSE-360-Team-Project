/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart.implementation;

/**
 *
 * @author Daniel Burgin
 */
public class FoodItem {
    private String itemName;
    private double itemPrice;
    private int itemQuantity;
    
    public FoodItem(String name, double price, int quantity){
        this.itemName = name;
        this.itemPrice = price;
        this.itemQuantity = quantity;
    }
    
    public FoodItem(String name, double price){
        this.itemName = name;
        this.itemPrice = price;
        this.itemQuantity = 1;
    }

    @Override
    public String toString() {
        if(itemQuantity == 1){
           return itemName + ", Price: $" + itemPrice + '\n'; 
        }else{
           return itemName + ", Price: $" + itemPrice + ", Quantity = " + itemQuantity + '\n';
        }
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }
    
     public int getItemQuantity() {
        return itemQuantity;
    }
     
    public void updateQuantity(int quantity){
        this.itemQuantity = quantity;
    }
}
