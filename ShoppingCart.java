/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart.implementation;

import java.util.ArrayList;

/**
 *
 * @author Daniel Burgin
 */
public class ShoppingCart {
    
    private ArrayList<FoodItem> cart;
    private int totalPrice;
    private int numofItems;

    public ShoppingCart() {
        cart = new ArrayList<FoodItem>();
        totalPrice = 0;
    }
    
    //adda a food item to array list
    public void addItem(FoodItem itemToAdd){
        this.cart.add(itemToAdd);
    }
    
    //remove a food item from the array list
    public void removeItem(FoodItem itemToDelete){
      this.cart.remove(itemToDelete);
    }
    
    public void printCart(){
        for(int i = 0; i < cart.size(); i++){
            System.out.print(cart.get(i));
        }
        
        System.out.println('\n' + "Summary: " + numOfItems() + " items, Total Prrice of : $" + totalPrice() + '\n');
    }
    
    public int numOfItems(){
        int totalNum = 0;
        for (FoodItem foodItem : cart){
            totalNum+= foodItem.getItemQuantity();
        }
        return totalNum;
    }
    
    public double totalPrice(){
        double totalPrice = 0;
        for (FoodItem foodItem : cart){
            totalPrice+= (foodItem.getItemPrice() *foodItem.getItemQuantity() );
        }
        return totalPrice;
        
    }
}
