package com.nineya.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

/**
 * <p>
 * </p>
 *
 * @author Jacky_xi
 * @since 2023-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Result")
public class Result implements Serializable {
    public String getCrashnum() {
        return crashnum;
    }

    public String getFusionerror() {
        return fusionerror;
    }

    private static final long serialVersionUID = 1L;

    @TableId("Id")
    private String id;

    @TableField("counter")
    private String counter;

    @TableField("crashnum")
    private String crashnum;

    @TableField("fusionerror")
    private String fusionerror;

    @TableField("datetime")
    private Timestamp DateTime;


    public String getId() {
        return id;
    }

    public String getCounter() {
        return counter;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public void setCrashnum(String crashnum) {
        this.crashnum = crashnum;
    }

    public void setFusionerror(String fusionerror) {
        this.fusionerror = fusionerror;
    }
}

