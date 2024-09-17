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

/**
 * Unit test for {@link CartController}.
 * <p>
 * This class sets up and runs unit tests for the {@code CartController} class,
 * using mock services and a mock MVC environment to test the controller's behavior
 * without requiring a live web server or actual service implementations.
 * </p>
 */
public class CartControllerTest {

  /**
   * Mock instance of {@link CartService}.
   * <p>
   * This mock service is used to simulate the behavior of the real {@code CartService} in tests.
   * It allows us to define specific responses and verify interactions without invoking actual service logic.
   * </p>
   */
  @Mock
  private CartService cartService;

  /**
   * Instance of {@link CartController} with dependencies injected.
   * <p>
   * This controller instance is created with the {@code CartService} mock injected into it.
   * It allows us to test the controller's behavior with predefined responses from the mocked service.
   * </p>
   */
  @InjectMocks
  private CartController cartController;

  /**
   * MockMvc instance for performing and testing HTTP requests.
   * <p>
   * This instance is used to set up the mock MVC environment for simulating HTTP requests and responses
   * to test the behavior of the {@code CartController}. It allows for comprehensive testing of controller methods
   * and their interactions with the mock service.
   * </p>
   */
  private MockMvc mockMvc;

  /**
   * Sets up the test environment before each test case.
   * <p>
   * This method initializes Mockito annotations and configures the {@code MockMvc} instance
   * for simulating HTTP interactions with the {@code CartController}.
   * </p>
   */
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
    cartInDto.setPrice(BigDecimal.valueOf(11.00));

    MessageOutDto response = new MessageOutDto("Cart added successfully");

    when(cartService.addItemToCart(any(CartInDto.class))).thenReturn(response);

    mockMvc.perform(post("/cart")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(cartInDto)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.message").value("Cart added successfully"));
  }

  @Test
  void testUpdateItemQuantity() throws Exception {
    int cartItemId = 1;
    MessageOutDto response = new MessageOutDto("Cart updated successfully");

    when(cartService.updateCartItemQuantity(1, 2)).thenReturn(response);

    mockMvc.perform(put("/cart/update/" + cartItemId)
        .param("quantityChange", "2"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.message").value("Cart updated successfully"));
  }

  @Test
  void testRemoveItemFromCart() throws Exception {
    int cartItemId = 1;
    MessageOutDto response = new MessageOutDto("Cart item removed successfully");

    when(cartService.removeItemFromCart(1)).thenReturn(response);

    mockMvc.perform(delete("/cart/remove/" + cartItemId))
      .andExpect(jsonPath("$.message").value("Cart item removed successfully"));
  }

  @Test
  void testClearCartAfterOrderPlaced() throws Exception {
    int userId = 1;
    int restaurantId = 1;
    MessageOutDto response = new MessageOutDto("Cart cleared after order placed");

    when(cartService.clearCartAfterOrderPlaced(1, 1)).thenReturn(response);

    mockMvc.perform(delete("/cart/clear/user/" + userId + "/restaurant/" + restaurantId))
      .andExpect(jsonPath("$.message").value("Cart cleared after order placed"));
  }

  @Test
  void testGetCartByUserId() throws Exception {
    int userId = 1;
    Cart cart = new Cart();
    cart.setUserId(1);
    List<Cart> cartItems = Collections.singletonList(cart);

    when(cartService.getCartByUserId(1)).thenReturn(cartItems);

    mockMvc.perform(get("/cart/user/" + userId))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].userId").value(1));
  }

  @Test
  void testGetCartById() throws Exception {
    int cartId = 1;
    Cart cart = new Cart();
    cart.setUserId(1);

    when(cartService.getCartById(1)).thenReturn(cart);

    mockMvc.perform(get("/cart/" + cartId))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.userId").value(1));
  }

  @Test
  void testGetCartByUserIdAndRestaurantId() throws Exception {
    int userId = 1;
    int restaurantId = 1;
    Cart cart = new Cart();
    cart.setUserId(1);
    cart.setRestaurantId(1);
    List<Cart> cartItems = Collections.singletonList(cart);

    when(cartService.getCartItemsByUserIdAndRestaurantId(1, 1)).thenReturn(cartItems);

    mockMvc.perform(get("/cart/user/" + userId + "/restaurant/" + restaurantId))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].userId").value(1))
      .andExpect(jsonPath("$[0].restaurantId").value(1));
  }
}
