package com.flaxishop.flexishop.business.entity;

import com.flaxishop.flexishop.config.data.DBSchemas;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@ToString(of = "id", doNotUseGetters = true)
@EqualsAndHashCode(of = "id", doNotUseGetters = true, callSuper = false)
@Table(name = "store", schema = DBSchemas.SCHEMA_FLEXISHOP)
@Entity
public class Store {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "logo_path")
    private String logoPath;

    @Column(name = "store_url", unique = true)
    private String storeUrl;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;


    @PrePersist
    private void prePersist() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
