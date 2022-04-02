package com.casualclub.prehistoric.model.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.time.LocalDateTime;

public class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 2412370106691836124L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = BaseEntity.Update.class)
    @TableId(value = "id", type = IdType.INPUT)
    private T id;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人ID")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private T createBy;

    public interface Save extends Default {}
    public interface Update extends Default {}

    public BaseEntity() {
    }

    public BaseEntity(T id, LocalDateTime createTime, T createBy) {
        this.id = id;
        this.createTime = createTime;
        this.createBy = createBy;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public T getCreateBy() {
        return createBy;
    }

    public void setCreateBy(T createBy) {
        this.createBy = createBy;
    }
}
