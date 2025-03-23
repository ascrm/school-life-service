package com.school.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.school.entity.Comment;
import com.school.entity.Post;
import com.school.utils.UserHolder;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createdBy", String.class, UserHolder.getLoginId());
        this.strictInsertFill(metaObject, "updatedBy", String.class, UserHolder.getLoginId());
        this.strictInsertFill(metaObject, "version", Integer.class, 1);
        this.strictInsertFill(metaObject, "isDelete", Integer.class,0);

        //Post 默认值
        if (metaObject.getOriginalObject() instanceof Post) {
            this.strictInsertFill(metaObject, "likes", Integer.class, 0);
            this.strictInsertFill(metaObject, "favorites", Integer.class, 0);
            this.strictInsertFill(metaObject, "comments", Integer.class, 0);
        }

        if(metaObject.getOriginalObject() instanceof Comment){
            this.strictInsertFill(metaObject, "likes", Integer.class, 0);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updatedBy", String.class, UserHolder.getLoginId());
    }
}