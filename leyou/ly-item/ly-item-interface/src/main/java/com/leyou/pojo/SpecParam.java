package com.leyou.pojo;

import javax.persistence.*;

@Table(name = "tb_spec_param")
public class SpecParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cid;

    private Long groupId;

    private String name;
    @Column(name="`numeric`")
    private Integer numeric;

    private String unit;

    private Integer generic;

    private Integer searching;

    private String segments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumeric() {
        return numeric;
    }

    public void setNumeric(Integer numeric) {
        this.numeric = numeric;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getGeneric() {
        return generic;
    }

    public void setGeneric(Integer generic) {
        this.generic = generic;
    }

    public Integer getSearching() {
        return searching;
    }

    public void setSearching(Integer searching) {
        this.searching = searching;
    }

    public String getSegments() {
        return segments;
    }

    public void setSegments(String segments) {
        this.segments = segments;
    }
}
