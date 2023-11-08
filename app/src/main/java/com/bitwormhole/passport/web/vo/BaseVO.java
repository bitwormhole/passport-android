package com.bitwormhole.passport.web.vo;

import com.bitwormhole.passport.web.dto.PaginationDTO;

public class BaseVO {

    public int status;
    public String message;
    public String error;
    public String time;
    public long timestamp;
    public PaginationDTO pagination;

}
