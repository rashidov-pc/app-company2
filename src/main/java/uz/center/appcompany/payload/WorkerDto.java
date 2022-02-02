package uz.center.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {
    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String street;

    @NotNull
    private String homeNumber;

    @NotNull
    private Integer departmentId;
}
