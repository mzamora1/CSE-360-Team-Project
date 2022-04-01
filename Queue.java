package cart.implementation;

import java.util.ArrayList;

public class Queue {
        private ArrayList<customer> queue;


        public Queue() {
                queue = new ArrayList();
        }

        public void addToQueue(customer user){
                queue.add(user);
        }

        public void removeFromQueue(customer user){
                queue.remove(user);
        }
        public int postionInQueue(customer user){
                return queue.indexOf(user);
        }

        public int excpectedWaitTime(customer user){
                //made 25 minutes the ammount of time it takes to complete an order
                return positionInQueue(user) * 11;

        }


        }
