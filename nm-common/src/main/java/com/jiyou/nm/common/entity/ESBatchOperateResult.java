package com.jiyou.nm.common.entity;

import io.searchbox.core.BulkResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 张恒同
 * 时间： 2018/11/2   09:19
 * 描述： 批量操作结果
 */
@Data
public class ESBatchOperateResult<T> {

    boolean isSucess;
    List<T> objects = new ArrayList<>();
    List<BulkResult.BulkResultItem> failedItems = new ArrayList<>();

    public ESBatchOperateResult(boolean isSucess, List<T> objects) {
        this.isSucess = isSucess;
        this.objects = objects;
    }

    public ESBatchOperateResult(boolean isSucess, List<T> objects, List<BulkResult.BulkResultItem> failedItems) {
        this.isSucess = isSucess;
        this.objects = objects;
        this.failedItems = failedItems;
    }
}
