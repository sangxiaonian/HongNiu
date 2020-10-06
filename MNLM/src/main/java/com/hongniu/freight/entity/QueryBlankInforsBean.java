package com.hongniu.freight.entity;

/**
 * 作者： ${PING} on 2019/3/8.
 */
public class QueryBlankInforsBean {
    /**
     * id : 146503
     * name : 超级网银行号
     * value : 308584000013
     * type : bank_net_code
     * description : 招商银行股份有限公司
     * sort : null
     * parentId : null
     * createBy : null
     * createDate : null
     * updateBy : null
     * updateDate : null
     * remarks : null
     * delFlag : null
     */

    private String id;
    private String name;
    private String value;
    private String type;
    private String description;
    private Object sort;
    private Object parentId;
    private Object createBy;
    private Object createDate;
    private Object updateBy;
    private Object updateDate;
    private Object remarks;
    private Object delFlag;

    public QueryBlankInforsBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public Object getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Object createBy) {
        this.createBy = createBy;
    }

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public Object getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Object delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return description==null?"":description;
    }
}
