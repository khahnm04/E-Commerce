package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.inventory.BranchRequest;
import com.khahnm04.ecommerce.dto.response.inventory.BranchResponse;
import com.khahnm04.ecommerce.entity.inventory.Branch;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    Branch toEntity(BranchRequest request);

    BranchResponse toResponse(Branch branch);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBranch(@MappingTarget Branch branch, BranchRequest request);

}
