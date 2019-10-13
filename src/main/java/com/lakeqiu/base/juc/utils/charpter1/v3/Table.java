package com.lakeqiu.base.juc.utils.charpter1.v3;

/**
 * @author lakeqiu
 */
public class Table {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表记录条数
     */
    private long sourceRecordCount = 10;
    /**
     * 校验器校验出来的结果
     */
    private long targetCount;

    @Override
    public String toString() {
        return "Table{" +
                "tableName='" + tableName + '\'' +
                ", sourceRecordCount=" + sourceRecordCount +
                ", targetCount=" + targetCount +
                ", columnSchema='" + columnSchema + '\'' +
                ", targetColumnSchema='" + targetColumnSchema + '\'' +
                '}';
    }

    /**
     * 表内容
     */
    String columnSchema = "<table name='a'><column name='coll' type='varchar' /></table>";
    /**
     * 校验器校验出来的结果
     */
    String targetColumnSchema = "";

    public Table(String tableName, long sourceRecordCount) {
        this.tableName = tableName;
        this.sourceRecordCount = sourceRecordCount;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getSourceRecordCount() {
        return sourceRecordCount;
    }

    public void setSourceRecordCount(long sourceRecordCount) {
        this.sourceRecordCount = sourceRecordCount;
    }

    public long getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(long targetCount) {
        this.targetCount = targetCount;
    }

    public String getColumnSchema() {
        return columnSchema;
    }

    public void setColumnSchema(String columnSchema) {
        this.columnSchema = columnSchema;
    }

    public String getTargetColumnSchema() {
        return targetColumnSchema;
    }

    public void setTargetColumnSchema(String targetColumnSchema) {
        this.targetColumnSchema = targetColumnSchema;
    }
}
