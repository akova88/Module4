package com.cg.model.dto;

import com.cg.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CustomerUpdResDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private BigDecimal balance;
    private LocationRegionUpdResDTO locationRegion;


}
