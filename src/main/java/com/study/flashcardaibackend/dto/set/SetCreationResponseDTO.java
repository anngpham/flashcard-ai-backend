package com.study.flashcardaibackend.dto.set;

import com.study.flashcardaibackend.dto.common.ResponseDTO;
import com.study.flashcardaibackend.model.set.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetCreationResponseDTO extends ResponseDTO<Set> {
    @Override
    public Set getData() {
        Set set = super.getData();
        set.setIsDeleted(null); // remove isDeleted field from response for security reasons
        return set;
    }

    public SetCreationResponseDTO(Set data) {
        super(data);
    }

    public SetCreationResponseDTO(Set data, boolean isSuccess) {
        super(data, isSuccess);
    }
}
