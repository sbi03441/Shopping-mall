package com.b2.prj02.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_idx")
    private Long cartId;

    @ManyToOne
    @JoinColumn(name="user_idx")
    private User userId;

    @Column(name="total_amount")
    private Long totalAmount;

    @Column(name="total_price")
    private Long totalPrice;

    @Column(name="total_quantity")
    private Long totalQuantity;


}
