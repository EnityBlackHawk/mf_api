package org.utfpr.mf.mf_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.utfpr.mf.enums.DefaultInjectParams;

@NoArgsConstructor
@Data
public class BindValueDTO {
    private String key;
    private Object value;

    public void setKey(DefaultInjectParams key) {
        this.key = key.toString();
    }

}
