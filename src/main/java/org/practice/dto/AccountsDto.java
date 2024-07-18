package org.practice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message = "Account Number can't be empty or null")
    @Size(min = 8, max = 12, message = "The length of account number should be between 8 and 12")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "Account Type can't be empty or null")
    private String accountType;

    @NotEmpty(message = "Branch Address can't be empty or null")
    private String branchAddress;
}
