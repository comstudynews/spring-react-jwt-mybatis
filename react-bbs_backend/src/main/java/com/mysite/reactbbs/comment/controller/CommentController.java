package com.mysite.reactbbs.comment.controller;

import java.util.Date;
import com.mysite.reactbbs.bbs.dto.request.CreateCommentRequest;
import com.mysite.reactbbs.bbs.dto.response.CreateCommentResponse;
import com.mysite.reactbbs.comment.dto.request.CommentRequest;
import com.mysite.reactbbs.comment.dto.request.UpdateCommentRequest;
import com.mysite.reactbbs.comment.dto.response.CommentResponse;
import com.mysite.reactbbs.comment.dto.response.DeleteCommentResponse;
import com.mysite.reactbbs.comment.dto.response.UpdateCommentResponse;
import com.mysite.reactbbs.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    /*
    public CommentController(CommentService service) {
        this.service = service;
    }
	*/ 
    
    /* [GET] /comment?bbsSeq={seq}&page={page} 댓글 조회 */
    @GetMapping
    public ResponseEntity<CommentResponse> getBbsCommentList(@ModelAttribute CommentRequest req) {
        System.out.println("CommentController getBbsCommentList " + new Date());

        return ResponseEntity.ok(service.getBbsCommentList(req));
    }

    /* [POST] /comment?bbsSeq={seq} 댓글 작성 */
    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(@RequestParam("bbsSeq") Integer bbsSeq,
        @RequestBody CreateCommentRequest req) {
        System.out.println("CommentController createComment " + new Date());

        return ResponseEntity.ok(service.createComment(bbsSeq, req));
    }

    /* [DELETE] /comment/{seq} 댓글 삭제 */
    @DeleteMapping("/{seq}")
    public ResponseEntity<DeleteCommentResponse> deleteComment(@PathVariable("seq") Integer seq) {
        System.out.println("CommentController deleteComment " + new Date());

        return ResponseEntity.ok(service.deleteComment(seq));
    }

    /* [PATCH] /comment/{seq} 댓글 수정 */
    @PatchMapping("/{seq}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@AuthenticationPrincipal UserDetails userDetails,
                                                                @PathVariable("seq") Integer seq,
                                                                @RequestBody UpdateCommentRequest req) {

        return ResponseEntity.ok(service.updateComment(userDetails.getUsername(), seq, req));
    }
}
