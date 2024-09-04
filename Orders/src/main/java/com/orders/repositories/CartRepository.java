package com.orders.repositories;

import com.orders.entities.Cart;

import java.util.List;

public interface CartRepository {

  List<Cart> findByUserId(Integer userId);

}
