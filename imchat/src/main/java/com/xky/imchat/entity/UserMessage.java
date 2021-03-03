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
 * @since 2021-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserMessage对象", description="")
public class UserMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "聊天记录id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "发送者id")
    private String userId;

    @ApiModelProperty(value = "接收者id")
    private String friendId;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "消息类型")
    private  Integer type;

    @TableField(fill= FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
