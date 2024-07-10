package com.study.flashcardaibackend.service;

import com.study.flashcardaibackend.dao.SetRepository;
import com.study.flashcardaibackend.dto.SetRequest;
import com.study.flashcardaibackend.entity.Set;
import com.study.flashcardaibackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SetServiceImpl implements SetService {

    private SetRepository setRepository;

    @Autowired
    public SetServiceImpl(SetRepository setRepository) {
        this.setRepository = setRepository;
    }


    @Override
    public Set createSet(User user, SetRequest setRequest) {

        Set set = new Set();
        set.setTitle(setRequest.getTitle());
        set.setDescription(setRequest.getDescription());
        set.setOwner(user);

        return setRepository.save(set);
    }

    @Override
    public Set getSet(User user, UUID setId) {
        Set set = setRepository.findById(setId).orElseThrow(() -> new RuntimeException("Set not found"));

        if (set.isDeleted()) {
            throw new RuntimeException("Set not found");
        }

        if (!set.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        return set;
    }

    @Override
    public Set updateSet(User user, SetRequest setRequest, UUID setId) {

        Set set = getSet(user, setId);

        set.setTitle(setRequest.getTitle());
        set.setDescription(setRequest.getDescription());

        return setRepository.save(set);


    }

    @Override
    public void deleteSet(User user, UUID setId) {

        Set set = getSet(user, setId);

        set.setDeleted(true);
        setRepository.save(set);
    }
}
