package uz.bob.company_worker.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressDto {

    @NotNull(message = "street should be not null")
    private String street;

    @NotNull(message = "homeNumber should be not null")
    private String homeNumber;

}
