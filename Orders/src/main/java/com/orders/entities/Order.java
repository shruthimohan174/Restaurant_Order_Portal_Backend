package com.orders.entities;

import com.sun.xml.internal.ws.wsdl.writer.document.http.Address;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer userId;

  private Integer addressId;

  private OrderStatus orderStatus;

  private String orderDetails;

  private LocalDateTime createdAt;

  private Address deliveryAddress;

}
