package com.example.demo.Comment;

import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import com.example.demo.Post.Post;
import com.example.demo.Post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 댓글 작성
    public CommentDto createComment(String loginId, Long postId, CommentDto commentDto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = new Comment(post, member, commentDto.getComment());
        Comment savedComment = commentRepository.save(comment);
        return CommentDto.fromComment(savedComment);
    }

    // 댓글 수정
    public CommentDto updateComment(String loginId, Long commentId, CommentDto commentDto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        // 작성자 본인 확인
        if (!comment.getMember().equals(member)) {
            throw new IllegalStateException("You can only edit your own comments");
        }

        // 댓글 내용 수정
        comment.setContent(commentDto.getComment());
        Comment updatedComment = commentRepository.save(comment);
        return CommentDto.fromComment(updatedComment);
    }

    // 댓글 삭제
    public void deleteComment(String loginId, Long commentId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        // 작성자 본인 확인
        if (!comment.getMember().equals(member)) {
            throw new IllegalStateException("You can only delete your own comments");
        }

        // 댓글 삭제
        commentRepository.delete(comment);
    }

    // 특정 게시글의 모든 댓글 조회
    public List<CommentDto> getAllCommentsForPost(Long postId) {
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getPost().getId().equals(postId))
                .map(CommentDto::fromComment)
                .collect(Collectors.toList());
    }

    // 특정 댓글 조회
    public CommentDto getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .map(CommentDto::fromComment)
                .orElse(null);
    }
    // 닉네임으로 댓글 조회
    public List<CommentDto> getCommentsByNickname(String nickname) {
        return commentRepository.findByMemberNickname(nickname).stream()
                .map(CommentDto::fromComment)
                .collect(Collectors.toList());
    }
}
