package com.flaxishop.flexishop.business.entity;

import com.flaxishop.flexishop.config.data.DBSchemas;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@ToString(of = "id", doNotUseGetters = true)
@EqualsAndHashCode(of = "id", doNotUseGetters = true, callSuper = false)
@Table(name = "product", schema = DBSchemas.SCHEMA_FLEXISHOP)
@Entity
public class Product {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "uuid", unique = true)
    private String uuid;


    @Column(name = "name")
    private String name;


    @Column(name = "description")
    private String description;


    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    private void prePersist() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
