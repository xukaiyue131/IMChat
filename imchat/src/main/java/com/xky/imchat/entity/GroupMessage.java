package com.xky.imchat.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value="GroupMessage对象", description="")
public class GroupMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "群聊消息id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "发送者id")
    private String userId;

    @ApiModelProperty(value = "群聊id")
    private String groupId;

    @ApiModelProperty(value = "内容")
    private String content;

    @TableField(fill= FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
