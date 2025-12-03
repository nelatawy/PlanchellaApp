package com.planchella.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.planchella.domain.Membership;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserDTO {

    public Long id;
    public String name;
    public String picUrl;
    public String accountUrl;
    public List<Membership>  memberships;
    public  UserDTO() {}

}
