package uz.bob.company_worker.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class WorkerDto {

    @NotNull(message = "name don't be null")
    private String name;

    @NotNull(message = "phoneNumber don't be null")
    private String phoneNumber;

    @NotNull(message = "addressId don't be null")
    private Integer addressId;

    @NotNull(message = "departmentsIds don't be null")
    private List<Integer> departmentsIds;
}
