package com.hl.shangou.pojo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WangEditDTO {
    private Integer error = 0;
    private String[] url;


    public static WangEditDTO ok(String[] url){
        return new WangEditDTO(0,url);
    }

}
