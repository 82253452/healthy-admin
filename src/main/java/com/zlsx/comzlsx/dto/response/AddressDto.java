package com.zlsx.comzlsx.dto.response;

import com.zlsx.comzlsx.domain.Address;
import lombok.Data;

import java.util.List;

@Data
public class AddressDto extends Address {
    private List<Address> children;
}
