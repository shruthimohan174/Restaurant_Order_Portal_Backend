package com.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.dto.OrderInDto;
import com.orders.dto.MessageOutDto;
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

class OrderControllerTest {

  @Mock
  private OrderService orderService;

  @InjectMocks
  private OrderController orderController;

  private MockMvc mockMvc;

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

    mockMvc.perform(post("/orders/place")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(orderInDto)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.message").value("Order placed successfully"));
  }

  @Test
  void testCancelOrder() throws Exception {
    MessageOutDto response = new MessageOutDto("Order canceled successfully");

    when(orderService.cancelOrder(anyInt())).thenReturn(response);

    mockMvc.perform(delete("/orders/cancel/1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.message").value("Order canceled successfully"));
  }

  @Test
  void testGetOrdersByUserId() throws Exception {
    OrderOutDto orderOutDto = new OrderOutDto();
    orderOutDto.setId(1);
    List<OrderOutDto> orders = Collections.singletonList(orderOutDto);

    when(orderService.getOrdersByUserId(anyInt())).thenReturn(orders);

    mockMvc.perform(get("/orders/user/1"))
      .andExpect(status().isOk());
  }

  @Test
  void testGetOrdersByRestaurantId() throws Exception {
    OrderOutDto orderOutDto = new OrderOutDto();
    orderOutDto.setId(1);
    List<OrderOutDto> orders = Collections.singletonList(orderOutDto);

    when(orderService.getOrdersByRestaurantId(anyInt())).thenReturn(orders);

    mockMvc.perform(get("/orders/restaurant/1"))
      .andExpect(status().isOk());
  }

  @Test
  void testMarkOrderAsCompleted() throws Exception {
    mockMvc.perform(post("/orders/complete/1/user/1"))
      .andExpect(status().isOk());
  }
}
