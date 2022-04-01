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
public class CartImplementation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FoodItem burger = new FoodItem("burger", 5.99, 1);
        FoodItem bigBurger = new FoodItem("big burger", 5.99, 3);
        
        ShoppingCart cart = new ShoppingCart();
        
        cart.addItem(burger);
        cart.addItem(bigBurger);
        
        cart.printCart();
        
        
    }
    
}
