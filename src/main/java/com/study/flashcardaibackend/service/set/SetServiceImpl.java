package com.study.flashcardaibackend.service.set;

import com.study.flashcardaibackend.constant.ErrorMessage;
import com.study.flashcardaibackend.dao.SetRepository;
import com.study.flashcardaibackend.dao.UserRepository;
import com.study.flashcardaibackend.dto.set.SetCreationRequestBodyDTO;
import com.study.flashcardaibackend.dto.set.SetUpdateRequestBodyDTO;
import com.study.flashcardaibackend.entity.set.SetEntity;
import com.study.flashcardaibackend.entity.user.UserEntity;
import com.study.flashcardaibackend.exception.HttpRuntimeException;
import com.study.flashcardaibackend.model.set.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    public Set createSet(SetCreationRequestBodyDTO setCreationBody, UUID userId) {
        UserEntity owner = userRepository.findById(userId).orElseThrow(() ->
                new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND)
        );

        SetEntity setEntity = new SetEntity();
        setEntity.setTitle(setCreationBody.getTitle());
        setEntity.setDescription(setCreationBody.getDescription());
        setEntity.setOwner(owner);
        SetEntity createdSet = setRepository.save(setEntity);
        return Set.fromEntity(createdSet);
    }

    @Override
    public Set updateSet(UUID setId, SetUpdateRequestBodyDTO setUpdateBody) {
        SetEntity setEntity = setRepository.findById(setId).get();
        if (setEntity.isDeleted()) {
            throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorMessage.SET_IS_DELETED);
        }

        if (setUpdateBody.getTitle() != null) setEntity.setTitle(setUpdateBody.getTitle());
        if (setUpdateBody.getDescription() != null) setEntity.setDescription(setUpdateBody.getDescription());
        SetEntity updatedSet = setRepository.save(setEntity);
        return Set.fromEntity(updatedSet);
    }

    @Override
    public void deleteSet(UUID setId) {
        SetEntity setEntity = setRepository.findById(setId).get();
        if (setEntity.isDeleted()) {
            throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorMessage.SET_IS_DELETED);
        }
        setEntity.setDeleted(true);
        setRepository.save(setEntity);
    }

    @Override
    public boolean isSetBelongToUser(UUID setId, UUID userId) {
        return setRepository.existsByIdAndOwnerId(setId, userId);
    }
}
