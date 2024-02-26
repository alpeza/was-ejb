package com.miapi.monitor.ARCH.headers;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HeaderArchMeta {
    private String txid;
    private String channel;
    private int tier;
}
