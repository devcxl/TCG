package com.github.devcxl.tcg.configurable;

/**
 * 配置面板 UI 接口
 * @author hehaiyangwork@gmail.com
 * @date 2017/05/20
 */
public interface UIConfigurable {

    /**
     * 是否已修改
     * @return
     */
    boolean isModified();

    /**
     * 应用
     */
    void apply();

    /**
     * 重置
     */
    void reset();

}
