package com.nineya.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * </p>
 *
 * @author Jacky_xi
 * @since 2023-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Configuration")
public class Configuration implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSitetime() {
        return sitetime;
    }

    public void setSitetime(String sitetime) {
        this.sitetime = sitetime;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getMapselect() {
        return mapselect;
    }

    public void setMapselect(String mapselect) {
        this.mapselect = mapselect;
    }

    public String getCrossover() {
        return crossover;
    }

    public void setCross(String crossover) {
        this.crossover = crossover;
    }

    public String getMutation() {
        return mutation;
    }

    public void setMutation(String mutation) {
        this.mutation = mutation;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }


    private static final long serialVersionUID = 1L;

    @TableId("Id")
    private String id;

    @TableField("weather")
    private String weather;

    @TableField("number")
    private String number;

    @TableField("sitetime")
    private String sitetime;

    @TableField("generator")
    private String generator;

    @TableField("mapselect")
    private String mapselect;

    @TableField("algorithm")
    private String algorithm;

    @TableField("crossover")
    private String crossover;

    @TableField("mutation")
    private String mutation;
}
