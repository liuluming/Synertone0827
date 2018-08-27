package com.my51c.see51.common;

import com.yzx.tools.DensityUtil;

import static com.baidu.mapapi.common.SysOSUtil.getDensity;

public class MyDensityUtil extends DensityUtil {
    public static int dip2px(float dpValue) {
        return (int) (dpValue * getDensity() + 0.5F);
    }
}
