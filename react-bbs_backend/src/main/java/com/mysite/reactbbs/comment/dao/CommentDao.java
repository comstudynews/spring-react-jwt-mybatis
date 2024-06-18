package com.mysite.reactbbs.comment.dao;

import java.util.List;
import com.mysite.reactbbs.comment.domain.Comment;
import com.mysite.reactbbs.comment.dto.param.CommentListParam;
import com.mysite.reactbbs.comment.dto.param.CreateCommentParam;
import com.mysite.reactbbs.comment.dto.param.UpdateCommentParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentDao {

    List<Comment> getCommentPageList(CommentListParam param);
    Integer getCommentCount(Integer seq);

    void createComment(CreateCommentParam param);
    Integer deleteComment(Integer seq);

    Comment getCommentBySeq(Integer seq);
    Integer updateComment(UpdateCommentParam param);
}
