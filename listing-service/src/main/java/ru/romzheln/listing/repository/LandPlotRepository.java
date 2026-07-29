package ru.romzheln.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.listing.model.entity.landplot.LandPlot;

public interface LandPlotRepository extends JpaRepository<LandPlot, Long> {
}
