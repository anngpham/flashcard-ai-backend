package com.study.flashcardaibackend.dto.set;

import com.study.flashcardaibackend.dto.common.SuccessResponseDTO;
import com.study.flashcardaibackend.model.set.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetUpdateResponseDTO extends SuccessResponseDTO<Set> {
    public SetUpdateResponseDTO(Set data) {
        super(data);
    }

    @Override
    public Set getData() {
        Set set = super.getData();
        set.setIsDeleted(null); // remove isDeleted field from response for security reasons
        return set;
    }
}
