package ru.romzheln.listing.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.romzheln.listing.model.entity.listing.Image;
import ru.romzheln.listing.model.entity.listing.MortgageProgram;
import ru.romzheln.listing.model.entity.listing.Promotion;
import ru.romzheln.listing.model.entity.owner.Owner;
import ru.romzheln.listing.model.entity.property.Property;
import ru.romzheln.listing.service.*;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ListingEntityResolver {

    private final OwnerService ownerService;
    private final PropertyService propertyService;
    private final PromotionService promotionService;
    private final MortgageProgramService mortgageProgramService;
    private final ImageService imageService;

    public Owner getOwner(Long id){
        return ownerService.findOwnerById(id);
    }

    public Property getProperty(Long id){
        return propertyService.findPropertyById(id);
    }

    public Promotion getPromotion(Long id){
        return promotionService.getPromotionById(id);
    }

    public Set<MortgageProgram> getMortgagePrograms(Set<Long> mortgageProgramIds){
        return mortgageProgramService.findAll(mortgageProgramIds);
    }

    public Set<Image> getImages(Set<Long> imageIds){
        return imageService.findAll(imageIds);
    }
}
