package main;

import main.domain.Order;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface OrderDAO
{

    int addOrder(Order order);
    List<Order> getAllOrders();

}
