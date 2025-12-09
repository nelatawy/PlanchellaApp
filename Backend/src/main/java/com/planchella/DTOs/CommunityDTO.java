package com.planchella.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunityDTO {
    public Long id;
    public String name;
    public CommunityDTO(){}
}
