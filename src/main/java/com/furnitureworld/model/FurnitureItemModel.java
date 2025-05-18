package com.furnitureworld.model;

/**
 * Represents a single furniture item in the application.
 * This class holds details about a furniture product, such as its ID, name,
 * category, price, and description.
 */
public class FurnitureItemModel {
    private int furniture_id; // Unique identifier for the furniture item
    private String furniture_name; // Name of the furniture item
    private int category_id; // Foreign key referencing the category
    private float price; // Price of the furniture item
    private String category_name; // Name of the category (often joined from a categories table)
    // private String imageFilename; // REMOVED - Image filename is no longer part of this model
    private String description; // Detailed description of the furniture item

    /**
     * Default constructor.
     */
    public FurnitureItemModel() {}

    /**
     * Parameterized constructor to initialize a FurnitureItemModel object.
     * @param furniture_id The ID of the furniture.
     * @param furniture_name The name of the furniture.
     * @param category_id The ID of the category this furniture belongs to.
     * @param price The price of the furniture.
     * @param category_name The name of the category.
     * @param description A description of the furniture.
     */
    public FurnitureItemModel(int furniture_id, String furniture_name, int category_id, float price, String category_name, String description) { // REMOVED imageFilename
        this.furniture_id = furniture_id;
        this.furniture_name = furniture_name;
        this.category_id = category_id;
        this.price = price;
        this.category_name = category_name;
        this.description = description;
    }

    // Getter and setter methods for each field provide access and modification capabilities.

    public int getFurniture_id() {
        return furniture_id;
    }
    public void setFurniture_id(int furniture_id) {
        this.furniture_id = furniture_id;
    }

    public String getFurniture_name() {
        return furniture_name;
    }
    public void setFurniture_name(String furniture_name) {
        this.furniture_name = furniture_name;
    }

    public int getCategory_id() {
        return category_id;
    }
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    // public String getImageFilename() { return imageFilename; } // REMOVED
    // public void setImageFilename(String imageFilename) { this.imageFilename = imageFilename; } // REMOVED

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the FurnitureItemModel object.
     * Useful for logging and debugging. The description is truncated for brevity.
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "FurnitureItemModel{" +
                "furniture_id=" + furniture_id +
                ", furniture_name='" + furniture_name + '\'' +
                ", category_id=" + category_id +
                ", price=" + price +
                ", category_name='" + category_name + '\'' +
                // ", imageFilename='" + imageFilename + '\'' + // REMOVED
                // Display a truncated version of the description for conciseness in logs
                ", description='" + (description != null ? description.substring(0, Math.min(description.length(), 20)) + "..." : "null") + '\'' +
                '}';
    }
}