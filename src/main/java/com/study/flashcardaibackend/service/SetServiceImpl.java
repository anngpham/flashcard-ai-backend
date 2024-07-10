package com.study.flashcardaibackend.service;

import com.study.flashcardaibackend.dao.SetRepository;
import com.study.flashcardaibackend.dao.UserRepository;
import com.study.flashcardaibackend.dto.SetRequest;
import com.study.flashcardaibackend.entity.Set;
import com.study.flashcardaibackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SetServiceImpl implements SetService {

    private SetRepository setRepository;

    private UserRepository userRepository;

    @Autowired
    public SetServiceImpl(SetRepository setRepository, UserRepository userRepository) {
        this.setRepository = setRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Set createSet(User user, SetRequest setRequest) {

        User owner = userRepository.findById(user.getId()).get();

        Set set = new Set();
        set.setTitle(setRequest.getTitle());
        set.setDescription(setRequest.getDescription());
        set.setOwner(user);

        return setRepository.save(set);
    }

    @Override
    public Set getSet(User user, UUID setId) {

        if(!isUserAccessibleWithSet(user, setId))
            throw new RuntimeException("Set not found");

        return setRepository.findById(setId).get();
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

    @Override
    public boolean isUserAccessibleWithSet(User user, UUID setId) {
        Optional<Set> set = setRepository.findById(setId);

        if (set.isEmpty())
            return false;
        else if(set.get().isDeleted())
            return false;
        else return set.get().getOwner().getId().equals(user.getId());
    }
}
