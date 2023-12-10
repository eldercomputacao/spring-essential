package com.elderpereira.springessential.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperUtil {
    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static <D, T> D map(final T object, Class<D> outClass) {
        return modelMapper.map(object, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> objectList, Class<D> outCLass) {
        return objectList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }
}
