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
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type =  IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户性别")
    private Integer sex;

    @ApiModelProperty(value = "用户头像")
    private String avater;

    @ApiModelProperty(value = "用户籍贯")
    private String address;

    @ApiModelProperty(value = "用户级别")
    private Integer level;

    @ApiModelProperty(value = "用户号")
    private String account;

    @ApiModelProperty(value = "个性签名")
    private String content;

    @ApiModelProperty(value = "用户邮箱")
    private String email;
    @ApiModelProperty(value = "用户邮箱")
    private String password;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;


}
