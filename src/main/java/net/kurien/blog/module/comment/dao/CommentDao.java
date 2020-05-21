package net.kurien.blog.module.comment.dao;

import java.util.List;

import net.kurien.blog.module.comment.entity.Comment;

public interface CommentDao {
	public List<Comment> selectList();
	public List<Comment> selectListByPostNo(int postNo);
	public Comment selectOne(int commentNo);
	public Integer getLastOrder(int parentCommentNo, int commentDepth);
	public void insert(Comment comment);
	public void update(Comment comment);
	public void delete(int commentNo);
}
