package kr.co.preq.domain.preq.dto;

import kr.co.preq.domain.preq.entity.Preq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreqMapper {
    public PreqResult toResponseDto(Preq preq) {
        if (preq == null) return null;

        PreqResult.PreqResultBuilder preqResponseDto = PreqResult.builder();
        preqResponseDto.id(preq.getId());
        preqResponseDto.question(preq.getQuestion());

        return preqResponseDto.build();
    }
}
