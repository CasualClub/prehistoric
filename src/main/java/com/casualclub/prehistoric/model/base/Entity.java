package com.casualclub.prehistoric.model.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

public class Entity<T> extends BaseEntity<T> {

    private static final long serialVersionUID = -716437553459921966L;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "最后更新人ID")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private T updateBy;

    @ApiModelProperty(value = "状态")
    @TableField(value = "state")
    private Boolean state;

    @ApiModelProperty(value = "是否已删除")
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

    public Entity() {
    }

    public Entity(T id, LocalDateTime createTime, T createBy, LocalDateTime updateTime, T updateBy, Boolean state, Boolean isDeleted) {
        super(id, createTime, createBy);
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.state = state;
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public T getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(T updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
