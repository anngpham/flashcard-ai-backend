package com.study.flashcardaibackend.service.set;

import com.study.flashcardaibackend.dao.SetRepository;
import com.study.flashcardaibackend.dto.set.SetCreationBodyDTO;
import com.study.flashcardaibackend.dto.set.SetUpdateBodyDTO;
import com.study.flashcardaibackend.entity.set.SetEntity;
import com.study.flashcardaibackend.entity.user.UserEntity;
import com.study.flashcardaibackend.model.set.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class SetServiceImpl implements SetService {

    private final SetRepository setRepository;

    @Autowired
    public SetServiceImpl(SetRepository setRepository) {
        this.setRepository = setRepository;
    }

    @Override
    public Set getSet(UUID setId) {
        Optional<SetEntity> setEntity = setRepository.findById(setId);
        return setEntity.map(Set::fromEntity).orElse(null);
    }

    @Override
    public Set createSet(SetCreationBodyDTO setCreationBody, UUID userId) {
        SetEntity setEntity = new SetEntity();
        setEntity.setTitle(setCreationBody.getTitle());
        setEntity.setDescription(setCreationBody.getDescription());
        setEntity.setOwner(UserEntity.getReferenceById(userId));
        SetEntity createdSet = setRepository.save(setEntity);
        return Set.fromEntity(createdSet);
    }

    @Override
    public Set updateSet(UUID setId, SetUpdateBodyDTO setUpdateBody) {
        SetEntity setEntity = setRepository.findById(setId).get();
        if (setUpdateBody.getTitle() != null) setEntity.setTitle(setUpdateBody.getTitle());
        if (setUpdateBody.getDescription() != null) setEntity.setDescription(setUpdateBody.getDescription());
        SetEntity updatedSet = setRepository.save(setEntity);
        return Set.fromEntity(updatedSet);
    }

    @Override
    public void deleteSet(UUID setId) {
        SetEntity setEntity = setRepository.findById(setId).get();
        setEntity.setDeleted(true);
        setRepository.save(setEntity);
    }

    @Override
    public Page<Set> getListOfSetByOwnerId(UUID userId, String search, Pageable pageable) {
//        return setRepository.findByOwnerIdAndIsDeletedIsFalseAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(userId, search, search, pageable);
        return setRepository.getListOfSetsByOwnerId(userId, search.toUpperCase(), pageable);
    }

}
