package com.restaurants.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test class for {@link FoodItemUpdateInDto}.
 * <p>
 * This class tests the functionality of the {@link FoodItemUpdateInDto}
 * class, including its getters, setters, and overridden methods such as
 * {@code toString()}, {@code equals()}, and {@code hashCode()}.
 * </p>
 */
public class FoodItemUpdateInDtoTest {

  /**
   * Tests the getters and setters of {@link FoodItemUpdateInDto}.
   * <p>
   * Verifies that all getter and setter methods correctly handle
   * the {@code itemName}, {@code description}, {@code price}, and
   * {@code isVeg} fields.
   * </p>
   */
  @Test
  public void testGettersAndSetters() {
    FoodItemUpdateInDto dto = new FoodItemUpdateInDto();

    assertNull(dto.getItemName());
    String itemName = "Dummy Item";
    dto.setItemName(itemName);
    assertEquals(itemName, dto.getItemName());

    assertNull(dto.getDescription());
    String description = "Dummy description";
    dto.setDescription(description);
    assertEquals(description, dto.getDescription());

    assertNull(dto.getPrice());
    BigDecimal price = new BigDecimal("0.00");
    dto.setPrice(price);
    assertEquals(price, dto.getPrice());

    assertNull(dto.getIsVeg());
    Boolean isVeg = false;
    dto.setIsVeg(isVeg);
    assertEquals(isVeg, dto.getIsVeg());
  }

  /**
   * Tests the {@code toString()} method of {@link FoodItemUpdateInDto}.
   * <p>
   * Verifies that the {@code toString()} method returns a string representation
   * of the object that includes all the fields.
   * </p>
   */
  @Test
  public void testToString() {
    FoodItemUpdateInDto dto = new FoodItemUpdateInDto();
    dto.setItemName("Dummy Item");
    dto.setDescription("Dummy description");
    dto.setPrice(new BigDecimal("0.00"));
    dto.setIsVeg(false);

    String expected = "FoodItemUpdateInDto(itemName=Dummy Item, description=Dummy description, isVeg=false, price=0.00)";
    assertEquals(expected, dto.toString());
  }

  /**
   * Tests the {@code equals()} and {@code hashCode()} methods of {@link FoodItemUpdateInDto}.
   * <p>
   * Verifies that two objects with the same field values are considered equal
   * and have the same hash code. Also checks that objects with different field
   * values are not considered equal and have different hash codes.
   * </p>
   */
  @Test
  public void testEqualsAndHashCode() {
    FoodItemUpdateInDto dto1 = buildFoodItemUpdateInDto("Dummy Item",
      "Dummy description", new BigDecimal("0.00"), false);
    FoodItemUpdateInDto dto2 = buildFoodItemUpdateInDto("Dummy Item",
      "Dummy description", new BigDecimal("0.00"), false);

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    dto2.setItemName("Different Item");
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildFoodItemUpdateInDto("Dummy Item", "Different description", new BigDecimal("0.00"), false);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildFoodItemUpdateInDto("Dummy Item", "Dummy description", new BigDecimal("1.00"), false);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto2 = buildFoodItemUpdateInDto("Dummy Item", "Dummy description", new BigDecimal("0.00"), true);
    assertNotEquals(dto1, dto2);
    assertNotEquals(dto1.hashCode(), dto2.hashCode());

    dto1 = new FoodItemUpdateInDto();
    dto2 = new FoodItemUpdateInDto();
    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
  }

  /**
   * Helper method to build a {@link FoodItemUpdateInDto} instance with specified values.
   *
   * @param itemName the item name
   * @param description the item description
   * @param price the item price
   * @param isVeg whether the item is vegetarian
   * @return a {@link FoodItemUpdateInDto} instance with the specified values
   */
  private FoodItemUpdateInDto buildFoodItemUpdateInDto(final String itemName, final String description,
                                                       final BigDecimal price, final Boolean isVeg) {
    FoodItemUpdateInDto dto = new FoodItemUpdateInDto();
    dto.setItemName(itemName);
    dto.setDescription(description);
    dto.setPrice(price);
    dto.setIsVeg(isVeg);
    return dto;
  }
}
