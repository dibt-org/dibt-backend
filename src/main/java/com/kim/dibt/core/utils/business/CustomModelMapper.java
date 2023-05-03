package com.kim.dibt.core.utils.business;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A utility class that provides methods for mapping objects using ModelMapper.
 */
@Component
@RequiredArgsConstructor
public class CustomModelMapper {

    /**
     * The ModelMapper instance to be used for mapping objects.
     */
    private final ModelMapper modelMapper;


    /**
     * Configures the ModelMapper instance to use the standard matching strategy and returns it.
     * The standard matching strategy tries to match properties between the source and target types based on their names.
     *
     * @return The ModelMapper instance with the standard matching strategy configured.
     */
    public ModelMapper ofStandard() {
        configureMapping(MatchingStrategies.STANDARD);
        return this.modelMapper;
    }

    /**
     * Configures the ModelMapper instance to use the loose matching strategy and returns it.
     * The loose matching strategy allows the mapper to match properties with slightly different names and also ignores null values when mapping.
     *
     * @return The ModelMapper instance with the loose matching strategy configured.
     */
    public ModelMapper ofLoose() {
        configureMapping(MatchingStrategies.LOOSE);
        return this.modelMapper;
    }

    /**
     * Configures the ModelMapper instance to use the strict matching strategy and returns it.
     * The strict matching strategy requires exact property name matches between source and target types and also ignores null values when mapping.
     *
     * @return The ModelMapper instance with the strict matching strategy configured.
     */
    public ModelMapper ofStrict() {
        configureMapping(MatchingStrategies.STRICT);
        this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return this.modelMapper;
    }

    /**
     * Maps a list of objects of type S to a list of objects of type T using the provided ModelMapper instance.
     *
     * @param source      The list of objects of type S to be mapped.
     * @param targetClass The class of objects to be mapped to (type T).
     * @param <S>         The source type.
     * @param <T>         The target type.
     * @return A list of objects of type T mapped from the list of objects of type S.
     */
    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream()
                .map(element -> this.ofStandard().map(element, targetClass))
                .toList();
    }

    /**
     * Sets the matching strategy and ignores ambiguity for the ModelMapper instance.
     *
     * @param matchingStrategy The matching strategy to be set.
     */
    private void configureMapping(MatchingStrategy matchingStrategy) {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(matchingStrategy);
    }
}

