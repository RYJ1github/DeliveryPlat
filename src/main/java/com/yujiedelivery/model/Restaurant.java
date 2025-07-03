package com.yujiedelivery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "restaurants")
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    private String description;
    private String address;
    private String phoneNumber;
    private String cuisineType;
    private Double rating;
    private Boolean isOpen = true;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    private Double deliveryFee;
    private Integer deliveryTime; // in minutes
    private Double minimumOrderAmount;
} 