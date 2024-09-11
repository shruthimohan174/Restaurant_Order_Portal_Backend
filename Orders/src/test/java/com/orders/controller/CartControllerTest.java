package com.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.dto.CartInDto;
import com.orders.dto.MessageOutDto;
import com.orders.entities.Cart;
import com.orders.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartControllerTest {

  @Mock
  private CartService cartService;

  @InjectMocks
  private CartController cartController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
  }

  @Test
  void testAddItemToCart() throws Exception {
    CartInDto cartInDto = new CartInDto();
    cartInDto.setUserId(1);
    cartInDto.setFoodItemId(1);
    cartInDto.setRestaurantId(1);
    cartInDto.setPrice(BigDecimal.valueOf(10.00));

    MessageOutDto response = new MessageOutDto("Cart added successfully");

    when(cartService.addItemToCart(any(CartInDto.class))).thenReturn(response);

    mockMvc.perform(post("/cart/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(cartInDto)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.message").value("Cart added successfully"));
  }

  @Test
  void testUpdateItemQuantity() throws Exception {
    MessageOutDto response = new MessageOutDto("Cart updated successfully");

    when(cartService.updateCartItemQuantity(1, 2)).thenReturn(response);

    mockMvc.perform(put("/cart/update/1")
        .param("quantityChange", "2"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.message").value("Cart updated successfully"));
  }

  @Test
  void testRemoveItemFromCart() throws Exception {
    MessageOutDto response = new MessageOutDto("Cart item removed successfully");

    when(cartService.removeItemFromCart(1)).thenReturn(response);

    mockMvc.perform(delete("/cart/remove/1"))
      .andExpect(jsonPath("$.message").value("Cart item removed successfully"));
  }

  @Test
  void testClearCartAfterOrderPlaced() throws Exception {
    MessageOutDto response = new MessageOutDto("Cart cleared after order placed");

    when(cartService.clearCartAfterOrderPlaced(1, 1)).thenReturn(response);

    mockMvc.perform(delete("/cart/clear/user/1/restaurant/1"))
      .andExpect(jsonPath("$.message").value("Cart cleared after order placed"));
  }

  @Test
  void testGetCartByUserId() throws Exception {
    Cart cart = new Cart();
    cart.setUserId(1);
    List<Cart> cartItems = Collections.singletonList(cart);

    when(cartService.getCartByUserId(1)).thenReturn(cartItems);

    mockMvc.perform(get("/cart/user/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].userId").value(1));
  }

  @Test
  void testGetCartById() throws Exception {
    Cart cart = new Cart();
    cart.setUserId(1);

    when(cartService.getCartById(1)).thenReturn(cart);

    mockMvc.perform(get("/cart/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.userId").value(1));
  }

  @Test
  void testGetCartByUserIdAndRestaurantId() throws Exception {
    Cart cart = new Cart();
    cart.setUserId(1);
    cart.setRestaurantId(1);
    List<Cart> cartItems = Collections.singletonList(cart);

    when(cartService.getCartItemsByUserIdAndRestaurantId(1, 1)).thenReturn(cartItems);

    mockMvc.perform(get("/cart/user/1/restaurant/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].userId").value(1))
      .andExpect(jsonPath("$[0].restaurantId").value(1));
  }
}
