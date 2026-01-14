package com.khahnm04.ecommerce.modules.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
public class TokenPayload implements Serializable {

    private String jwtId;
    private String token;
    private Date expiredTime;

}
