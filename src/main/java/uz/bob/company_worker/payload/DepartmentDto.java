package uz.bob.company_worker.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {

    @NotNull(message = "name is null")
    private String name;

    @NotNull(message = "companyId is null")
    private Integer companyId;
}
