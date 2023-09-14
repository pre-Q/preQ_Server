package kr.co.preq.domain.applicationChild.entity;

import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
	private static final String SPLIT_CHAR = ";";

	@Override
	public String convertToDatabaseColumn(List<String> stringList) {
		if (stringList == null || stringList.isEmpty())
			return null;
		return String.join(SPLIT_CHAR, stringList);
	}

	@Override
	public List<String> convertToEntityAttribute(String string) {
		if (string == null || string.isBlank())
			return null;
		return Arrays.asList(string.split(SPLIT_CHAR));
	}
}