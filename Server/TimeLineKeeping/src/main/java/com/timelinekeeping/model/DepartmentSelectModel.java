package com.timelinekeeping.model;

import com.timelinekeeping.entity.DepartmentEntity;

import java.io.Serializable;

/**
 * Created by TrungNN on 9/18/2016.
 */
public class DepartmentSelectModel implements Serializable {
    private Long id;
    private String name;

    public DepartmentSelectModel() {
    }

    public DepartmentSelectModel(DepartmentEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartmentSelectModel that = (DepartmentSelectModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}