package com.wcreators.ersstrategy.mapper;

import com.wcreators.ersstrategy.dto.CupRateDTO;
import com.wcreators.ersstrategy.model.CupRatePoint;
import com.wcreators.ersstrategy.model.Rate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CupRateMapper {

    CupRatePoint dtoToModel(CupRateDTO dto);

    @Mappings({
            @Mapping(target = "high", source = "sell"),
            @Mapping(target = "low", source = "sell"),
            @Mapping(target = "open", source = "sell"),
            @Mapping(target = "close", source = "sell"),
            @Mapping(target = "start", source = "createdDate"),
            @Mapping(target = "end", source = "createdDate")
    })
    CupRatePoint rateToCupPoint(Rate rate);
}
