package com.kim.dibt.core.utils.business;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomModelMapper {
    private final ModelMapper modelMapper;

    // standard model mapper
    public ModelMapper of() {
        this.modelMapper
                .getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return this.modelMapper;
    }

    // ignore if the field is null
    public ModelMapper ofLoose() {
        this.modelMapper
                .getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return this.modelMapper;
    }

    //TODO this  method will be changed
    // all fields must be matched
    public ModelMapper ofStrict() {
        this.modelMapper
                .getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setPropertyCondition(Conditions.isNotNull());
        return this.modelMapper;
    }


}
