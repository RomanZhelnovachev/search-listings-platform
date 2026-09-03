package ru.romzheln.search_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romzheln.search_service.model.embeded.PendingEntityStateKey;
import ru.romzheln.search_service.model.state.PendingEntityState;

public interface StateRepository extends JpaRepository<PendingEntityState, PendingEntityStateKey> {}
