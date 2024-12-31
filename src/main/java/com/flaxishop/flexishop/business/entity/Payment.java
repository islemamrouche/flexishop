package com.flaxishop.flexishop.business.entity;

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
@Table(name = "payment", schema = DBSchemas.SCHEMA_FLEXISHOP)
@Entity
public class Payment {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

    
        @Column(name = "uuid", unique = true)
        private String uuid;

        @ManyToOne
        @JoinColumn(name = "order_id", referencedColumnName = "id")
        private Order order;

        @Column(name = "payment_date")
        @Temporal(TemporalType.TIMESTAMP)
        private Date paymentDate;

        @Column(name = "amount")
        private BigDecimal amount;

        @Column(name = "payment_method")
        private String paymentMethod;

        @Column(name = "status")
        private String status;  // e.g., "PENDING", "COMPLETED", etc.

        @PrePersist
        private void prePersist() {
                if (this.paymentDate == null) {
                        this.paymentDate = new Date();
                }
                this.setUuid(UUID.randomUUID().toString());
        }
}
