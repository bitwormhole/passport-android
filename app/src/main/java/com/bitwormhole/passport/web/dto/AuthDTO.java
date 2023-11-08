package com.bitwormhole.passport.web.dto;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class AuthDTO extends BaseDTO {

    @SerializedName("mechanism")
    public String Mechanism;  // string      `json:"mechanism"`

    @SerializedName("account")
    public String Account; //  string      `json:"account"`

    @SerializedName("secret")
    public String Secret; //   lang.Base64 `json:"secret"`


    @SerializedName("parameters")
    public Map<String, String> Parameters;


    public void setParam(String name, String value) {
        if (name == null || value == null) {
            return;
        }
        Map<String, String> table = this.Parameters;
        if (table == null) {
            table = new HashMap<>();
            this.Parameters = table;
        }
        table.put(name, value);
    }

}
