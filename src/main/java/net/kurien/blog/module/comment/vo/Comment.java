package net.kurien.blog.module.comment.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Comment {
    private Integer commentNo;
    private Integer postNo;
    private Integer parentCommentNo;
    private Integer commentOrder;
    private Integer commentDepth;
    private String author;
    private String password;
    private String comment;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date writeTime;
    private String writeIp;
    private String deleteYn;
}