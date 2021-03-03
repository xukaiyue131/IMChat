package com.xky.imchat.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2021-01-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="GroupNumber对象", description="")
public class GroupNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "群聊id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "成员id")
    private String numberId;

    @ApiModelProperty(value = "群备注")
    private String content;

    private Integer level;

    @TableField(fill= FieldFill.INSERT)
    @ApiModelProperty(value = "加入时间")
    private Date createTime;


}
