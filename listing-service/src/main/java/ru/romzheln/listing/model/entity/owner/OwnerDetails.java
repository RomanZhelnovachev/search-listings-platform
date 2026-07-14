package ru.romzheln.listing.model.entity.owner;

import jakarta.persistence.*;
import lombok.*;
import ru.romzheln.listing.model.enums.OwnerType;

@Entity
@Table(name = "owner_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OwnerDetails {

    @OneToOne
    @MapsId
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type")
    private OwnerType ownerType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact_data")
    private String contactData;
}
