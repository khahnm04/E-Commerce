package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.user.AddressUserRequest;
import com.khahnm04.ecommerce.dto.response.user.AddressUserResponse;
import com.khahnm04.ecommerce.entity.user.Address;
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
