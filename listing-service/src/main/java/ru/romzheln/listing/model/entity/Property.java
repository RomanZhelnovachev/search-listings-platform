//package ru.romzheln.listing.model.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//import ru.romzheln.listing.model.enums.HouseClass;
//import ru.romzheln.listing.model.enums.PropertyType;
//
//import java.time.Instant;
//
//@Entity
//@Table(name = "property")
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Builder
//public class Property {
//
//    @Id
//    @Column(name = "id", nullable = false, unique = true)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private Long id;
//
//    @Column(name = "location", nullable = false)
//    private Location location;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "complex_id")
//    private ResidentialComplex complex;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "type", nullable = false)
//    private PropertyType type;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "developer_id")
//    private Developer developer;
//
//    @Embedded
//    private Amenities amenities;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "houseClass")
//    private HouseClass houseClass;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "area_id")
//    private Area area;
//
//    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
//    private PropertyPhysicalDetails physicalDetails;
//
//    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
//    private PropertyFinancialDetails financialDetails;
//
//    @Column(name = "created_at", nullable = false)
//    @CreationTimestamp
//    private Instant createdAt;
//
//    @Column(name = "updated_at")
//    @UpdateTimestamp
//    private Instant updatedAt;
//}
