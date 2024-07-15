package com.study.flashcardaibackend.service.set;

import com.study.flashcardaibackend.dao.SetRepository;
import com.study.flashcardaibackend.dao.UserRepository;
import com.study.flashcardaibackend.dto.set.SetCreationBodyDTO;
import com.study.flashcardaibackend.dto.set.SetUpdateBodyDTO;
import com.study.flashcardaibackend.entity.set.SetEntity;
import com.study.flashcardaibackend.model.set.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class SetServiceImpl implements SetService {

    private final SetRepository setRepository;
    private final UserRepository userRepository;

    @Autowired
    public SetServiceImpl(SetRepository setRepository, UserRepository userRepository) {
        this.setRepository = setRepository;
        this.userRepository = userRepository;
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
        setEntity.setOwner(userRepository.getReferenceById(userId));
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
}
