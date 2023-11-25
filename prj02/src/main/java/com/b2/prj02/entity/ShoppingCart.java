package com.b2.prj02.entity;

import com.b2.prj02.entity.product.ProductEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
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
    @JoinColumn(name="user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name="product_id")
    private ProductEntity productId;

    @Column(name = "amount")
    private Long amount;



}
