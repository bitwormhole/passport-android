package com.bitwormhole.passport.web.vo;

import com.bitwormhole.passport.web.dto.AuthDTO;
import com.google.gson.annotations.SerializedName;

public class AuthVO extends BaseVO {


    @SerializedName("auth")
    public AuthDTO[] Auth;  //  `json:"auth"`         // 需要验证的内容


//    UserInfo    *rbac.UserDTO `json:"user"`         // 用户信息
    //   NewPassword lang.Base64   `json:"new_password"` // 新的密码（用于注册，设置，重设密码）
    //   Success     bool          `json:"success"`      // 是否完全成功

}
