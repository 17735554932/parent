package com.teacity.entity.model;

import com.teacity.entity.model.base.BaseUserComment;
import io.jboot.db.annotation.Table;

/**
 * Generated by Jboot.
 */
@Table(tableName = "user_comment", primaryKey = "user_comment_id")
public class UserComment extends BaseUserComment<UserComment> {

    public static String COMMENT_TYPE_All = "1";

    public static String COMMENT_TYPE_IMPORTANT = "0";

	
}
