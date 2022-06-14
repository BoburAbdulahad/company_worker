package uz.bob.company_worker.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {

    @NotNull(message = "corpName should be not null")
    private String corpName;

    @NotNull(message = "directorName should be not null")
    private String directorName;

    @NotNull(message = "addressId should be not null")
    private Integer addressId;

}
