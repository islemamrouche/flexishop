package com.flaxishop.flexishop.business.entity;

import com.flaxishop.flexishop.business.enums.Status;
import com.flaxishop.flexishop.config.data.DBSchemas;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@ToString(of = "id", doNotUseGetters = true)
@EqualsAndHashCode(of = "id", doNotUseGetters = true, callSuper = false)
@Table(name = "orders", schema = DBSchemas.SCHEMA_FLEXISHOP)
@Entity
public class Order {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "uuid", unique = true)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.PENDING;

    @PrePersist
    private void prePersist() {
        if (this.orderDate == null) {
            this.orderDate = new Date();
        }
        this.setUuid(UUID.randomUUID().toString());
    }
}
