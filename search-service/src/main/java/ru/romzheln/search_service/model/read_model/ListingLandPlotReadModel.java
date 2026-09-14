package ru.romzheln.search_service.model.read_model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.romzheln.search_service.model.embeded.LandPlot;

@Entity
@Table(name = "listing_land_plot_search")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ListingLandPlotReadModel extends ReadModel{

  @Embedded private LandPlot landPlot;
}
