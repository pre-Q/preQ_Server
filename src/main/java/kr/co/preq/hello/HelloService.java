package kr.co.preq.hello;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.preq.global.common.util.response.ErrorCode;
import kr.co.preq.hello.dto.HelloResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HelloService {
	private final HelloRepository helloRepository;

	@Transactional
	public HelloResponseDto create(String name) {
		Hello hello = Hello.from(name);
		hello = helloRepository.save(hello);
		HelloResponseDto savedHelloDto = HelloResponseDto.of(hello.getId(), hello.getName());
		return savedHelloDto;
	}

	@Transactional(readOnly = true)
	public HelloResponseDto read(Long id) {
		Hello hello = helloRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_ID.getMessage()));
		if (hello.isDeleted() == true) {
			throw new IllegalArgumentException(ErrorCode.BAD_PARAMETER.getMessage());
		}

		HelloResponseDto helloDto = HelloResponseDto.of(hello.getId(), hello.getName());
		return helloDto;
	}

	@Transactional
	public HelloResponseDto update(Long id, String name) {
		Hello hello = helloRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_ID.getMessage()));
		if (hello.isDeleted() == true) {
			throw new IllegalArgumentException(ErrorCode.BAD_PARAMETER.getMessage());
		}
		hello.setName(name);
		Hello updatedHello = helloRepository.save(hello);
		HelloResponseDto helloDto = HelloResponseDto.of(updatedHello.getId(), updatedHello.getName());
		return helloDto;
	}

	@Transactional
	public HelloResponseDto softDelete(Long id) {
		Hello hello = helloRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_ID.getMessage()));
		// hello.softDelete();
		hello = helloRepository.save(hello);
		HelloResponseDto helloDto = HelloResponseDto.of(hello.getId(), hello.getName());
		return helloDto;
	}
}
