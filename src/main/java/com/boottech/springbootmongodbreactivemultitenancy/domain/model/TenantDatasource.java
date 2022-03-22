package com.boottech.springbootmongodbreactivemultitenancy.domain.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <h2>TenantDatasource</h2>
 *
 * @author aek
 * <p>
 * Description:
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TenantDatasource {

    @NotNull
    @NotBlank
    private String id;
    @NotNull
    @NotBlank
    private String database;
    @NotNull
    private int port;
    @NotNull
    @NotBlank
    private String host;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;

}
