package com.study.flashcardaibackend.service;

import com.study.flashcardaibackend.dao.SetRepository;
import com.study.flashcardaibackend.dto.SetCreationRequest;
import com.study.flashcardaibackend.entity.Set;
import com.study.flashcardaibackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetServiceImpl implements SetService {

    private SetRepository setRepository;

    @Autowired
    public SetServiceImpl(SetRepository setRepository) {
        this.setRepository = setRepository;
    }


    @Override
    public void addSet(User user, SetCreationRequest setCreationRequest) {

        Set set = new Set();
        set.setTitle(setCreationRequest.getTitle());
        set.setDescription(setCreationRequest.getDescription());
        set.setOwner(user);

        setRepository.save(set);
    }
}
