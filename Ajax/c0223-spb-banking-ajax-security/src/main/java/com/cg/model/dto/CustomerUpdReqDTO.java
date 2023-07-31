package com.cg.model.dto;

import com.cg.model.Customer;
import com.cg.model.LocationRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.Valid;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CustomerUpdReqDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    @Valid
    private LocationRegionUpdReqDTO locationRegion;

    public Customer toCustomer(Long customerId, LocationRegion locationRegion) {
        return new Customer()
                .setId(customerId)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setLocationRegion(locationRegion)
                ;
    }
}
