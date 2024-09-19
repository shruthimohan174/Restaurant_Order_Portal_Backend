package com.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.dto.MessageOutDto;
import com.orders.dto.OrderInDto;
import com.orders.dto.OrderOutDto;
import com.orders.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for {@link OrderController}.
 * <p>
 * This class sets up and executes unit tests for the {@code OrderController} class.
 * It uses mocked services and a mock MVC environment to test the controller's methods
 * without requiring actual service implementations or a live web server.
 * </p>
 */
public class OrderControllerTest {

  /**
   * Mock instance of {@link OrderService}.
   * <p>
   * This mock is used to simulate the behavior of the {@code OrderService} in tests.
   * It allows for predefined responses and verification of interactions with the mock service,
   * thus avoiding the need for a real service implementation during testing.
   * </p>
   */
  @Mock
  private OrderService orderService;

  /**
   * Instance of {@link OrderController} with dependencies injected.
   * <p>
   * This controller instance is created with the {@code OrderService} mock injected into it.
   * It allows the testing of the controller's logic and behavior in isolation from the actual service.
   * </p>
   */
  @InjectMocks
  private OrderController orderController;

  /**
   * MockMvc instance for performing and testing HTTP requests.
   * <p>
   * This instance is used to set up a mock MVC environment that simulates HTTP requests and responses
   * to test the {@code OrderController}. It helps verify that the controller methods handle requests
   * and responses correctly without needing a live server.
   * </p>
   */
  private MockMvc mockMvc;

  /**
   * Sets up the test environment before each test case.
   * <p>
   * This method initializes Mockito annotations and configures the {@code MockMvc} instance
   * for simulating HTTP interactions with the {@code OrderController}.
   * </p>
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
  }

  @Test
  void testPlaceOrder() throws Exception {
    OrderInDto orderInDto = new OrderInDto();
    orderInDto.setUserId(1);
    orderInDto.setDeliveryAddressId(1);
    orderInDto.setRestaurantId(1);
    orderInDto.setCartItems(Collections.emptyList());
    MessageOutDto response = new MessageOutDto("Order placed successfully");

    when(orderService.placeOrder(any(OrderInDto.class))).thenReturn(response);

    mockMvc.perform(post("/orders")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(orderInDto)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.message").value("Order placed successfully"));
  }

  @Test
  void testCancelOrder() throws Exception {
    int orderId = 1;
    MessageOutDto response = new MessageOutDto("Order canceled successfully");

    when(orderService.cancelOrder(anyInt())).thenReturn(response);

    mockMvc.perform(delete("/orders/cancel/" + orderId))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.message").value("Order canceled successfully"));
  }

  @Test
  void testGetOrdersByUserId() throws Exception {
    int userId = 1;
    OrderOutDto orderOutDto = new OrderOutDto();
    orderOutDto.setId(1);
    List<OrderOutDto> orders = Collections.singletonList(orderOutDto);

    when(orderService.getOrdersByUserId(anyInt())).thenReturn(orders);

    mockMvc.perform(get("/orders/user/" + userId))
      .andExpect(status().isOk());
  }

  @Test
  void testGetOrdersByRestaurantId() throws Exception {
    int restaurantId = 1;
    OrderOutDto orderOutDto = new OrderOutDto();
    orderOutDto.setId(1);
    List<OrderOutDto> orders = Collections.singletonList(orderOutDto);

    when(orderService.getOrdersByRestaurantId(anyInt())).thenReturn(orders);

    mockMvc.perform(get("/orders/restaurant/" + restaurantId))
      .andExpect(status().isOk());
  }

  @Test
  void testMarkOrderAsCompleted() throws Exception {
    int orderId = 1;
    int userId = 1;
    mockMvc.perform(post("/orders/complete/" + orderId + "/user/" + userId))
      .andExpect(status().isOk());
  }
}
