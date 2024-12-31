package com.flaxishop.flexishop.business.entity;

import com.flaxishop.flexishop.config.data.DBSchemas;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@ToString(of = "id", doNotUseGetters = true)
@EqualsAndHashCode(of = "id", doNotUseGetters = true, callSuper = false)
@Table(name = "order_item", schema = DBSchemas.SCHEMA_FLEXISHOP)
@Entity
public class OrderItem {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "uuid", unique = true)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @PrePersist
    private void prePersist() {
        this.totalPrice = price.multiply(new BigDecimal(quantity));
        this.setUuid(UUID.randomUUID().toString());
    }
}
