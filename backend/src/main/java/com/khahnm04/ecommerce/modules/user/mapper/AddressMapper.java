package com.khahnm04.ecommerce.modules.user.mapper;

import com.khahnm04.ecommerce.modules.user.dto.request.AddressUserRequest;
import com.khahnm04.ecommerce.modules.user.dto.response.AddressUserResponse;
import com.khahnm04.ecommerce.modules.user.entity.Address;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "addressType", constant = "HOME")
    @Mapping(target = "isDefault", constant = "false")
    Address toAddress(AddressUserRequest request);

    AddressUserResponse toAddressResponse(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAddress(@MappingTarget Address address, AddressUserRequest request);

}
