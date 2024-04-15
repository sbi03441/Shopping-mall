package com.b2.prj02.entity;

import com.b2.prj02.entity.product.ProductEntity;
import com.b2.prj02.user.entity.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "shopping_cart")
public class ShoppingCart extends FieldEntity{

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
