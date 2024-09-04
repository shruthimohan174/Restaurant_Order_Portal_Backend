package com.restaurants.constants;

/**
 * Contains constant values used across the restaurant application.
 */
public final class RestaurantConstants {

  /**
   * Error message indicating that a restaurant was not found with the given ID.
   */
  public static final String RESTAURANT_NOT_FOUND = "Restaurant not found with this id";

  /**
   * Error message indicating that a food category was not found with the given ID.
   */
  public static final String CATEGORY_NOT_FOUND = "No Food category found with this id";

  /**
   * Error message indicating that a food item was not found with the given ID.
   */
  public static final String FOOD_ITEM_NOT_FOUND = "No Food items found with this id";

  /**
   * Error message indicating that there was an error processing the image.
   */
  public static final String ERROR_PROCESSING_IMAGE = "Failed to process the image";

  /**
   * Error message indicating that a food category already exists for the given restaurant.
   */
  public static final String CATEGORY_ALREADY_EXISTS = "Category already exists for this restaurant";

  /**
   * Error message indicating that a food item already exists for the given restaurant.
   */
  public static final String ITEM_ALREADY_EXISTS = "Food item already exists for this restaurant";

  /**
   * Error message indicating that the uploaded file type is invalid.
   */
  public static final String INVALID_FILE_TYPE = "Only JPEG and PNG files are allowed.";

  /**
   * Error message indicating that the user is not authorized to add a restaurant.
   */
  public static final String NOT_RESTAURANT_OWNER = "User is not authorized to add a restaurant";

  /**
   * Success message indicating that a food item was added successfully.
   */
  public static final String FOOD_ITEM_ADDED_SUCCESSFULLY = "Food item added successfully";

  /**
   * Success message indicating that a food item was updated successfully.
   */
  public static final String FOOD_ITEM_UPDATED_SUCCESSFULLY = "Food item updated successfully";

  /**
   * Success message indicating that a food category was added successfully.
   */
  public static final String FOOD_CATEGORY_ADDED_SUCCESSFULLY = "Food category added successfully";

  /**
   * Success message indicating that a food category was updated successfully.
   */
  public static final String FOOD_CATEGORY_UPDATED_SUCCESSFULLY = "Food category updated successfully";

  /**
   * Success message indicating that a restaurant was added successfully.
   */
  public static final String RESTAURANT_ADDED_SUCCESSFULLY = "Restaurant added successfully";

  // Private constructor to prevent instantiation
  private RestaurantConstants() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
