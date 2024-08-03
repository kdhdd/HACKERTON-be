package com.example.demo.Post;

import com.example.demo.Comment.CommentRepository;
import com.example.demo.Member.Member;
import com.example.demo.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

//@Service
//public class PostService {
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    // 게시글 작성
//    public Post createPost(String loginId, PostDto postDto) {
//        Member member = memberRepository.findByLoginId(loginId)
//                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
//        // 하루에 한 번만 글 작성할 수 있도록 제한
//        if (member.getLastPostDate() != null && member.getLastPostDate().isEqual(LocalDate.now())) {
//            throw new IllegalStateException("You can only post once a day");
//        }
//
//        Post post = new Post(member, postDto.getContent());
//        member.setLastPostDate(LocalDate.now());
//        memberRepository.save(member);
//
//        return postRepository.save(post);
//    }
//    // 게시글 수정
//    public Post updatePost(String loginId, Long postId, PostDto postDto) {
//        Member member = memberRepository.findByLoginId(loginId)
//                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
//
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
//
//        // 작성자 본인 확인
//        if (!post.getMember().equals(member)) {
//            throw new IllegalStateException("You can only edit your own posts");
//        }
//
//        // 게시글 내용 수정
//        post.setContent(postDto.getContent());
//        return postRepository.save(post);
//    }
//    // 게시글 삭제
//    public void deletePost(String loginId, Long postId) {
//        Member member = memberRepository.findByLoginId(loginId)
//                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
//
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
//
//        // 작성자 본인 확인
//        if (!post.getMember().equals(member)) {
//            throw new IllegalStateException("You can only delete your own posts");
//        }
//
//        // 게시글 삭제
//        postRepository.delete(post);
//    }
//    // 모든 게시글 조회
//    public List<Post> getAllPosts() {
//        return postRepository.findAll();
//    }
//
//    // 특정 게시글 조회
//    public Post getPostById(Long postId) {
//        return postRepository.findById(postId).orElse(null);
//    }
//
//    // 닉네임으로 게시글 조회
//    public List<PostDto> getPostsByNickname(String nickname) {
//        List<Post> posts = postRepository.findByMemberNickname(nickname);
//        return posts.stream().map(PostDto::fromPost).collect(Collectors.toList());
//    }
//}

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentRepository commentRepository;

    // 게시글 작성
    public PostDto createPost(String loginId, PostDto postDto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (member.getLastPostDate() != null && member.getLastPostDate().isEqual(LocalDate.now())) {
            throw new IllegalStateException("You can only post once a day");
        }

        Post post = new Post(member, postDto.getContent());
        member.setLastPostDate(LocalDate.now());
        memberRepository.save(member);

        Post savedPost = postRepository.save(post);
        return PostDto.fromPost(savedPost);
    }

    // 게시글 수정
    public PostDto updatePost(String loginId, Long postId, PostDto postDto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getMember().equals(member)) {
            throw new IllegalStateException("You can only edit your own posts");
        }

        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);
        return PostDto.fromPost(updatedPost);
    }

    // 게시글 삭제
    public void deletePost(String loginId, Long postId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getMember().equals(member)) {
            throw new IllegalStateException("You can only delete your own posts");
        }

        postRepository.delete(post);
    }

    // 모든 게시글 조회 (최신순 정렬)
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAllOrderByCreatedAtDesc();
        return posts.stream().map(post -> {
            PostDto postDto = PostDto.fromPost(post);
            postDto.setCommentCount(commentRepository.countByPostId(post.getId())); // 댓글 개수 설정
            return postDto;
        }).collect(Collectors.toList());
    }

    // 특정 게시글 조회
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            PostDto postDto = PostDto.fromPost(post);
            postDto.setCommentCount(commentRepository.countByPostId(postId)); // 댓글 개수 설정
            return postDto;
        }
        return null;
    }

    // 닉네임으로 게시글 조회 (최신순 정렬)
    public List<PostDto> getPostsByNickname(String nickname) {
        List<Post> posts = postRepository.findByMemberNicknameOrderByCreatedAtDesc(nickname);
        return posts.stream().map(post -> {
            PostDto postDto = PostDto.fromPost(post);
            postDto.setCommentCount(commentRepository.countByPostId(post.getId())); // 댓글 개수 설정
            return postDto;
        }).collect(Collectors.toList());
    }
}

